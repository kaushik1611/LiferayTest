/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.inexture.csvimporter.portlet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.inexture.csvimporter.background.task.executor.CSVImportBackgroundTaskExecutor;
import com.inexture.csvimporter.constants.CSVImporterPortletKeys;
import com.inexture.csvimporter.model.Goods;
import com.inexture.csvimporter.model.impl.GoodsImpl;
import com.inexture.csvimporter.service.GoodsLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

/**
 * @author Laptop1
 *
 */
@Component(immediate = true, property = { "com.liferay.portlet.display-category=category.development",
		"com.liferay.portlet.instanceable=true", "javax.portlet.name=" + CSVImporterPortletKeys.CSVImporter,
		"com.liferay.portlet.header-portlet-css=/css/datatables.min.css",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.footer-portlet-javascript=/js/jquery-2.1.4.min.js",
		"com.liferay.portlet.footer-portlet-javascript=/js/datatables.min.js",
		"com.liferay.portlet.footer-portlet-javascript=/js/main.js", "javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp", "javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.display-name=CSVImporter Portlet" }, service = Portlet.class)
public class CSVImporter extends MVCPortlet {

	private static final Log log = LogFactoryUtil.getLog(CSVImporter.class.getName());

	@Reference
	protected BackgroundTaskManager backgroundTaskmanager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet#render(javax.
	 * portlet.RenderRequest, javax.portlet.RenderResponse)
	 */
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		log.info("Executing Render");
		super.render(renderRequest, renderResponse);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet#serveResource(
	 * javax.portlet.ResourceRequest, javax.portlet.ResourceResponse)
	 */
	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException {
		String action = resourceRequest.getParameter("resourceAction");
		switch (action) {
		case "getGoodsList":
			// Get all goods data
			List<Goods> goodsList = GoodsLocalServiceUtil.getGoodses(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			JSONObject object = JSONFactoryUtil.createJSONObject();
			object.put("data", goodsList);
			resourceResponse.getWriter().write(object.toJSONString());
			break;
		default:
			break;
		}
		super.serveResource(resourceRequest, resourceResponse);
	}

	/**
	 * Action request to upload csv file
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws IOException
	 * @throws PortletException
	 */
	public void uploadCSV(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {
		UploadPortletRequest upreq = PortalUtil.getUploadPortletRequest(actionRequest);
		log.debug("File Name::" + upreq.getFileName("csvFileUpload"));
		File file = upreq.getFile("csvFileUpload");

		List<GoodsImpl> goodsList;
		try {
			goodsList = readFromCSV(file, ',');
			log.debug("Total CSV data::" + goodsList.size());
		} catch (IOException e1) {
			log.error("error.." + e1);
			SessionErrors.add(actionRequest, "error-csv-file-empty");
			return;
		} catch (Exception e) {
			log.error("error.." + e);
			SessionErrors.add(actionRequest, "error-parse-csv");
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		ServiceContext serviceContext = null;
		try {
			serviceContext = ServiceContextFactory.getInstance(actionRequest);
		} catch (PortalException e) {
			log.error("Eror in getting service context", e);
			SessionErrors.add(actionRequest, "something-went-wrong");
		}

		// This taskContextMap can be used as transporter to background job
		Map<String, Serializable> taskContextMap = new HashMap<>();
		taskContextMap.put("title", "Upload Success");
		taskContextMap.put("notificationText",
				"CSV data persist successfully for the file <b>" + upreq.getFileName("csvFileUpload") + "</b>");
		taskContextMap.put("userId", themeDisplay.getUserId());
		taskContextMap.put("goodsList", new ArrayList<GoodsImpl>(goodsList));
		taskContextMap.put("serviceContext", serviceContext);

		try {
			// Adding the job to liferay background manager
			BackgroundTask backgroundTask = backgroundTaskmanager.addBackgroundTask(themeDisplay.getUserId(),
					themeDisplay.getScopeGroupId(), CSVImportBackgroundTaskExecutor.class.getName(),
					CSVImportBackgroundTaskExecutor.class.getName(), taskContextMap, serviceContext);
			// With returned background object you can check status, id etc.
			actionRequest.setAttribute("backgroundTaskId", backgroundTask.getBackgroundTaskId());
			log.debug("backgroundTask Id: " + backgroundTask.getBackgroundTaskId());
			SessionMessages.add(actionRequest, "csv-upload-success");
		} catch (Exception e) {
			log.error("Exception while adding/triggering background task.", e);
			SessionErrors.add(actionRequest, "something-went-wrong");
		}
	}

	/**
	 * Method to read data from csv file
	 * 
	 * @param source
	 * @param delimiter
	 * @return
	 */
	private static List<GoodsImpl> readFromCSV(File file, char delimiter)
			throws NumberFormatException, IOException, PortletException {
		/**
		 * Reading the CSV File Delimiter is comma Default Quote character is
		 * double quote Start reading from line 1
		 */
		CSVReader csvReader = new CSVReader(new FileReader(file), delimiter, CSVParser.DEFAULT_QUOTE_CHARACTER, 1);
		// mapping of columns with their positions
		ColumnPositionMappingStrategy<GoodsImpl> mappingStrategy = new ColumnPositionMappingStrategy<GoodsImpl>();
		// Set mappingStrategy type to Goods Type
		mappingStrategy.setType(GoodsImpl.class);
		// Fields in Goods Bean
		String[] columns = new String[] { "name", "description", "length", "height", "nr" };
		// Setting the colums for mappingStrategy
		mappingStrategy.setColumnMapping(columns);
		// create instance for CsvToBean class
		CsvToBean<GoodsImpl> ctb = new CsvToBean<GoodsImpl>();
		// parsing csvReader(Employee.csv) with mappingStrategy
		return ctb.parse(mappingStrategy, csvReader);
	}

}
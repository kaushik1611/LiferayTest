package com.inexture.csvimporter.background.task.executor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import com.inexture.csvimporter.constants.CSVImporterPortletKeys;
import com.inexture.csvimporter.model.impl.GoodsImpl;
import com.inexture.csvimporter.service.GoodsLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplayFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;

/**
 * @author Laptop1
 *
 */
@Component(immediate = true, property = {
		"background.task.executor.class.name=com.inexture.csvimporter.background.task.executor.CSVImportBackgroundTaskExecutor" }, // Without
		service = BackgroundTaskExecutor.class)
public class CSVImportBackgroundTaskExecutor extends BaseBackgroundTaskExecutor {
	public static final Log log = LogFactoryUtil.getLog(CSVImportBackgroundTaskExecutor.class);

	/**
	 * @param backgroundTask
	 * @return BackgroundTaskResult
	 */
	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask) throws Exception {
		// taskContextMap which is sent by the caller
		Map<String, Serializable> taskContextMap = backgroundTask.getTaskContextMap();

		String title = taskContextMap.get("title").toString();
		String notificationText = taskContextMap.get("notificationText").toString();
		long userId = Long.valueOf(taskContextMap.get("userId").toString());
		ServiceContext serviceContext = (ServiceContext) taskContextMap.get("serviceContext");

		List<GoodsImpl> goodsList = (List<GoodsImpl>) taskContextMap.get("goodsList");
		for (GoodsImpl goods : goodsList) {
			GoodsLocalServiceUtil.add(goods);
		}
		// Telling the system if, background task is successful or not
		BackgroundTaskResult backgroundTaskResult = new BackgroundTaskResult(BackgroundTaskConstants.STATUS_SUCCESSFUL);

		// Sending the data to util for MessageBus
		fireNotification(userId, title, notificationText, serviceContext);

		// UserNotificationEventLocalServiceUtil.
		return backgroundTaskResult;
	}

	@Override
	public boolean isSerial() {
		return false;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		return BackgroundTaskDisplayFactoryUtil.getBackgroundTaskDisplay(backgroundTask);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	/**
	 * Fire the notification for completion of the import csv
	 *
	 * @param userId
	 * @param reportTypeText
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void fireNotification(long userId, String title, String notificationText, ServiceContext serviceContext)
			throws PortalException, SystemException {
		log.debug("Exeucting fireNotification");
		JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
		payloadJSON.put("userId", userId);
		payloadJSON.put("title", title);
		payloadJSON.put("notificationText", notificationText);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(userId, CSVImporterPortletKeys.CSVImporter,
				new Date().getTime(), UserNotificationDeliveryConstants.TYPE_WEBSITE, userId, payloadJSON.toString(),
				false, serviceContext);
		log.debug("End  fireNotification");
	}
}
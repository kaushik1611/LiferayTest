package com.custom.background.task.notification;

import org.osgi.service.component.annotations.Component;

import com.inexture.csvimporter.constants.CSVImporterPortletKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Laptop1
 *
 */
@Component(immediate = true, property = {
		"javax.portlet.name=" + CSVImporterPortletKeys.CSVImporter }, service = UserNotificationHandler.class)
public class CustomUserNotificationHandler extends BaseUserNotificationHandler {

	/**
	 * 
	 */
	public CustomUserNotificationHandler() {
		setPortletId(CSVImporterPortletKeys.CSVImporter);
	}

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.notifications.BaseUserNotificationHandler#getBody(com.liferay.portal.kernel.model.UserNotificationEvent, com.liferay.portal.kernel.service.ServiceContext)
	 */
	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
			throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());
		// long userId = jsonObject.getLong("userId");
		String notificationText = jsonObject.getString("notificationText");
		String title = "<strong>" + jsonObject.getString("title") + "</strong>";
		String body = StringUtil.replace(getBodyTemplate(), new String[] { "[$TITLE$]", "[$BODY_TEXT$]" },
				new String[] { title, notificationText });
		return body;
	}

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.notifications.BaseUserNotificationHandler#getBodyTemplate()
	 */
	@Override
	protected String getBodyTemplate() throws Exception {
		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\">[$TITLE$]</div><div ");
		sb.append("class=\"body\">[$BODY_TEXT$]</div>");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.notifications.BaseUserNotificationHandler#getLink(com.liferay.portal.kernel.model.UserNotificationEvent, com.liferay.portal.kernel.service.ServiceContext)
	 */
	@Override
	protected String getLink(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
			throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());
		String pageLink = jsonObject.getString("pageLink");
		return pageLink;
	}

}

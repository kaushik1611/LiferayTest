<%@ include file="/init.jsp" %>

<portlet:actionURL name="uploadCSV" var="actionURL"></portlet:actionURL>
<portlet:resourceURL var="getGoodsListURL">
	<portlet:param name="resourceAction" value="getGoodsList"/>
</portlet:resourceURL>
<div class="hide" id="getGoodsListURL">${getGoodsListURL}</div>
<div class="hide" id="namespace"><portlet:namespace/></div>
<liferay-ui:error key="error-parse-csv" message="error-parse-csv" />
<liferay-ui:error key="error-csv-file-empty" message="error-csv-file-empty" />
<liferay-ui:success key="csv-upload-success" message="csv-upload-success" />
<aui:form action="<%=actionURL%>" enctype="multipart/form-data" method="post">
    <div class="manage-main" style="padding-top: 20px;">
        <table>
            <tr>
                <td class="lbl"><liferay-ui:message key="select-file-to-upload" /></td>
                <td><input type="file" name="csvFileUpload" id="csvFileUpload" accept=".csv" size="1" /></td>
            </tr>
        </table>
    	<aui:button type="submit" value="upload" name="upload" cssClass="topSpace"/>
    </div>
</aui:form>
<br />
<hr/>
<center><h2><liferay-ui:message key="Goods Data" /></h2></center>
<hr/>
<aui:row>
	<aui:col>
		<table id="<portlet:namespace/>goods-list-table"></table>
	</aui:col>
</aui:row>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
		<logic:messagesPresent>
			<div class="error">
				<html:messages id="message" bundle="${_bundle==null?'org.apache.struts.action.MESSAGE':_bundle}">
					${message}
				</html:messages>
			</div>
		</logic:messagesPresent>
		<logic:messagesPresent message="true">
			<div class="message" id="loginError">
				<html:messages id="message" message="true" bundle="${_bundle==null?'org.apache.struts.action.MESSAGE':_bundle}">
					${message}
				</html:messages>
			</div>
		</logic:messagesPresent>
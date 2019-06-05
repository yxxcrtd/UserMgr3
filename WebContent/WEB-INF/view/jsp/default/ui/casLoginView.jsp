<jsp:directive.include file="includes/top.jsp" />

<!-- 
<c:if test="${not pageContext.request.secure}">
<div id="msg" class="errors">
    <h2>Non-secure Connection</h2>
    <p>You are currently accessing CAS over a non-secure connection.  Single Sign On WILL NOT WORK.  In order to have single sign on work, you MUST log in over HTTPS.</p>
</div>
</c:if>
 -->
<script type="text/javascript">
function resetpasswordFunc(){
	window.document.location.href="resetpassword";
}
function registernewuserFunc(){
	window.document.location.href="registeruser";
}
function reshowVerifyCode(){
	document.getElementById("verifyImage").src="authimg?now="+new Date();
}
</script>
  <div class="box fl-panel" id="login">
			<form:form method="post" id="fmm" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
                  <form:errors path="*" id="msg" cssClass="errors" element="div" />
                  <div id="loginleft">            
                    <h2><spring:message code="screen.welcome.instructions" /></h2>
                    
                    <c:if test="${not empty isSelectSchool and isSelectSchool.value=='true'}">
                        <div class="row fl-controls-left">
                            <label for="schoolGuid" class="fl-label"><spring:message code="screen.welcome.label.school" /></label>
                              <select name="schoolGuid"  id="schoolGuid" >  
                                    <c:forEach items="${schoolList}" var="school"   >
                                       <option value="<c:out value='${school.unitGuid}'/>" <c:if test="${fn:toLowerCase(loginSelectSchool)==fn:toLowerCase(school.unitGuid)}">selected</c:if>>  
                                           <c:out value="${school.schoolName}"/>  
                                       </option>   
                                    </c:forEach>  
                               </select>                                                           
                        </div>
                    </c:if>
                                        
                    <div class="row fl-controls-left">
                        <label for="username" class="fl-label"><spring:message code="screen.welcome.label.netid" /></label>
						<c:if test="${not empty sessionScope.openIdLocalId}">
						<strong>${sessionScope.openIdLocalId}</strong>
						<input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
						</c:if>

						<c:if test="${empty sessionScope.openIdLocalId}">
						<spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
						<form:input cssClass="required" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="false" htmlEscape="true" />
						</c:if>
						
                    </div>
                    <div class="row fl-controls-left">
                        <label for="password" class="fl-label"><spring:message code="screen.welcome.label.password" /></label>
						<%--
						NOTE: Certain browsers will offer the option of caching passwords for a user.  There is a non-standard attribute,
						"autocomplete" that when set to "off" will tell certain browsers not to prompt to cache credentials.  For more
						information, see the following web page:
						http://www.geocities.com/technofundo/tech/web/ie_autocomplete.html
						--%>
						<spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
						<form:password cssClass="required" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
                    </div>
                    <c:if test="${not empty isShowVerifyCode and isShowVerifyCode.value=='true'}" var="verifycondition" scope="request">
	                    <div class="row fl-controls-left">
	                        <label for="vcode" class="fl-label"><spring:message code="screen.welcome.label.vcode" /></label>
	                        <form:input cssClass="required" cssErrorClass="error" id="vcode" style="width:250px" maxlength="4" tabindex="3" path="vcode" htmlEscape="true" autocomplete="off"/>
	                         <a href="#" onclick="reshowVerifyCode();return false;"><img id="verifyImage" class="verfycode" src="authimg" border="0" title='<spring:message code="usermgr.login.verifyCode.refresh" />' /></a>
	                    </div>
                    </c:if>
                    <c:if test="${not verifycondition}">
                        <input type="hidden" name="vcode" value="1234"/>
                    </c:if>         
                    <!--         
                    <div class="row check">
                        <input id="warn" name="warn" value="true" tabindex="3" accesskey="<spring:message code="screen.welcome.label.warn.accesskey" />" type="checkbox" />
                        <label for="warn"><spring:message code="screen.welcome.label.warn" /></label>
                    </div>
                    -->
                    <div class="row btn-row">
						<input type="hidden" name="lt" value="${loginTicket}" />
						<input type="hidden" name="execution" value="${flowExecutionKey}" />
						<input type="hidden" name="_eventId" value="submit" />
                        
                        <input class="btn-submit" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" />
                        
                        <div style="text-align:right;padding-right:20px;">
                        <input class="btn-reset" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="reset" />
                        <c:if test="${not empty isRegisterEnable and isRegisterEnable.value=='true'}">
                        <input class="btn-reset" name="registernewuser" accesskey="z" value="<spring:message code="screen.welcome.button.registernewuser" />" tabindex="7" type="button" onclick="registernewuserFunc()" />
                        </c:if>
                        <input class="btn-reset" name="resetpassword" accesskey="r" value="<spring:message code="screen.welcome.button.resetpassword" />" tabindex="6" type="button" onclick="resetpasswordFunc()" />
                        </div>
                    </div>
                 </div>
                 <div id="loginright"></div>
                 <div style="clear:both;width:60px; padding-top:2px;">
					 <div id="sidebar">
					    <div class="sidebar-content">
					           <p><spring:message code="screen.welcome.security" /></p>
					    </div>
					 </div>
                 </div>   
            </form:form>
   </div>
<jsp:directive.include file="includes/bottom.jsp" />

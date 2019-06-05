<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
<script type="text/javascript">
<!--
function setconfig(chk,key){
	document.getElementById("configForm").key.value=key;
	if(chk.checked){
		document.getElementById("configForm").keyvalue.value="true";
	}else{
		document.getElementById("configForm").keyvalue.value="false";
	}
	document.getElementById("configForm").submit();
}
//-->
</script>
<form name="configForm" id="configForm" action="setconfig" method="post" target="hiddenframe">
    <input type="hidden" name="key" id="key" value=""/>
    <input type="hidden" name="keyvalue" id="keyvalue" value=""/>
</form>
             <%
             //--如果用户有权限管理--  //request.getRemoteUser()
             //根据当前登录的票证得到用户
            String name="";
             
            //下面这个只能得到一次？？
            org.jasig.cas.authentication.principal.UsernamePasswordCredentials user = (org.jasig.cas.authentication.principal.UsernamePasswordCredentials)request.getAttribute("credentials");
            name=user.getUsername();   //可能是null
            //name=user.getUsername();   //可能是null
            //String logouturl = request.getContextPath() + "/logout"; 
             %>
<iframe style="display:none" name="hiddenframe"></iframe>
<div class="box fl-panel" id="login">
         <c:if test="${not empty userInfo and userInfo.username=='admin'}">
         <table border="0" style="width:960px;">
            <tr>
            <c:if test="${isSelectSchool.value=='true'}">
                <td id="tdschoollist" style="height:30px;width:100px;background:#0077BB;vertical-align:middle;">
                    <a href="listschool" style="color:#ffffff">单位管理</a>
                </td>
              </c:if> 
                <td style="height:30px;width:100px;background:#0077BB;vertical-align:middle;">
                    <a href="listuser" style="color:#ffffff">用户管理</a>
                </td>
                <td style="text-align:left;padding-left:10px;vertical-align:middle;">
                                                        设置：<input type="checkbox" name="isShowVerifyCode" onclick="setconfig(this,'isShowVerifyCode');" <c:if test="${isShowVerifyCode.value=='true'}">checked</c:if> value="1"/>使用验证码&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox" name="isRegisterEnable" onclick="setconfig(this,'isRegisterEnable');" <c:if test="${isRegisterEnable.value=='true'}">checked</c:if>  value="2"/>注册用户账号
                    <!-- 
                    <input type="checkbox" name="isSelectSchool" onclick="setconfig(this,'isSelectSchool');" <c:if test="${isSelectSchool.value=='true'}">checked</c:if>  value="3"/>选择单位进行登录
                    -->
                </td> 
                <td style="width:100px;">
                    
                </td>
            </tr>
         </table>
         </c:if> 

        <div class="fl-controls-left">
			<div id="msg" class="success">
				<h2><spring:message code="screen.success.header" /></h2>
				<p><spring:message code="screen.success.success" /></p>
				<p><spring:message code="screen.success.security" /></p>
			</div>
		</div>
		
    </div>         
<jsp:directive.include file="includes/bottom.jsp" />


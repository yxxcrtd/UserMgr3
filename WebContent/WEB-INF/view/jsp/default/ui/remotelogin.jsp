<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<style type="text/css">
html{margin: 0;border: 0; padding:0;}
body{font-size:12px;
margin: 0; 
padding-top:10px; 
padding-left:4px;
padding-right:0px;
padding-bottom:2px;
border: 0; 
outline: 0;
overflow-x:hidden;
overflow-y:hidden;
background-color:transparent;
}

input[type=text], input[type=password] { height:17px;}
div{
    margin:0px auto 0px;
    height:27px;
}
.errors{color:red;}
</style>

<script type="text/javascript">
function reshowVerifyCode(){
	<c:if test="${not empty isShowVerifyCode and isShowVerifyCode.value=='true'}">
    document.getElementById("verifyImage").src="authimg?now="+new Date();
    </c:if>
}
function checkInputData(){
	if(document.getElementById("username").value==""){
		alert("请输入用户登录名");
		return false;	
	}
    if(document.getElementById("password").value==""){
        alert("请输入用户密码");
        return false;   
    }
    if(document.getElementById("vcode").value==""){
        alert("请输入验证码");
        return false;   
    }
	return true;
}
function linkx(){
	var shref="resetpassword?username=" + document.getElementById("username").value;
	window.open(shref,"_top");
	return false;
}
</script>
</head>
<body>
  <script type="text/javascript">
      <c:forEach items="${flowRequestContext.messageContext.allMessages}" var="message">
              alert("${message.text}");
      </c:forEach>
  </script>
 <form:form method="post" id="fmm" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true"  onSubmit="return checkInputData();">
             <div>用户名称:
             <form:input id="username" style="width:104px;"  tabindex="1" path="username" autocomplete="false" htmlEscape="true" onClick="reshowVerifyCode();" />
             </div>
             <div>用户密码:
             <form:password id="password"  style="width:104px;"   tabindex="2" path="password"  htmlEscape="true" autocomplete="off" />
            </div>             
         <c:if test="${not empty isShowVerifyCode and isShowVerifyCode.value=='true'}" var="verifycondition" scope="request">
         <div>验&nbsp;证&nbsp;码: <form:input style="width:37px;" id="vcode" maxlength="4" tabindex="3" path="vcode" htmlEscape="true" autocomplete="off"/>
          <a href="#" onclick="reshowVerifyCode();return false;"><img id="verifyImage" style="width:60px;vertical-align:top" class="verfycode" src="authimg" border="0" title='点击刷新验证码' /></a>
         </div>
         </c:if>
         
         <div style="padding-top:5px;padding-left:20px;">
         <c:if test="${not verifycondition}">
             <input type="hidden" name="vcode" id="vcode" value="1234"/>
         </c:if>               
             <input type="hidden" name="schoolGuid" value="" />
             <input type="hidden" name="lt" value="${loginTicket}" />
             <input type="hidden" name="execution" value="${flowExecutionKey}" />
             <input type="hidden" name="_eventId" value="submit" />
             <input name="submit" accesskey="l" value="登录" tabindex="4" type="submit" />
             <input name="reset" accesskey="r" value="重置" tabindex="5" type="reset" />
             <!-- 
             <a style="float:right;padding-right:14px" href="#" onclick="return linkx();">重置密码</a>
             -->
        </div>             
 </form:form>
</body>
</html>
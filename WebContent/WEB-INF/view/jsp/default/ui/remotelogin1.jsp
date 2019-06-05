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
padding-left:10px;
padding-right:0px;
padding-bottom:0px;
border: 0; 
outline: 0;
overflow-x:hidden;
overflow-y:hidden;
background-color:#e4e4e4;
color:#5e5e5e;
}

input[type=text],input[type=password]{ padding:1px; width:202px; height:22px; border: 1px solid #d0d0d0; background: #FFFFFF; }
div{
    margin:2px 0px 0px 0px;
    padding:2px 0px 0px 2px;
}
.errors{color:red;}
a{outline:none; text-decoration:none; blr:expression(this.onFocus =   this.blur () )}
a:focus{-moz-outline-style:none}
a:hover{color:#F00}

.errorMessage{color:#F00; font-weight:bold}

.registerbtn { 
    border-width:1px;
    border-color:#BDE3F8;
	padding:0px;
	width: 140px; height:30px; padding: 0px;
	background: #BDE3F8;
	-webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px;
	font-size:13px;
	cursor: pointer;
}

.btnn { 
    border-width:1px; 
    padding:0px;
    width: 100px; 
    height:30px; 
    background: #BEE4F9; 
    -webkit-border-radius: 5px; -moz-border-radius: 5px; border-radius: 5px;
    font-size:12px;
	-moz-border-radius: 2px; -webkit-border-radius: 2px; border-radius: 2px; -moz-box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.5);
	-webkit-box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.5); box-shadow: 1px 2px 2px rgba(0, 0, 0, 0.5);
	text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.20);
}
.btnn:hover { cursor: pointer; }

.btn-submit { border-width:1px; padding:0px;border: 1px solid #eeae0f;color:#bd5704;
width: 203px; height:30px; padding: 0px; background: #ffbc1a; 
margin-left:0px;margin-right:0px;
font-size:14px;font-family:黑体; font-weight:bold;
}

.btn-reset { border: 0; background: none; text-transform: lowercase; height:50px; cursor: pointer; }
.btn-submit:hover, .btn-reset:hover { cursor: pointer; }

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
function resetpasswordFunc(){
    //window.document.location.href="resetpassword";
    var shref="resetpassword?username=" + document.getElementById("username").value;
    window.open(shref,"_top");
    return false;    
}
function registernewuserFunc(){
    //window.document.location.href="registeruser";
    var shref="registeruser";
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
 <form:form method="post" id="fmm" commandName="${commandName}" htmlEscape="true"  onSubmit="return checkInputData();">
             <div>用户名称:</div>
             <div>
             <form:input id="username" tabindex="1" path="username" autocomplete="false" htmlEscape="true" onClick="reshowVerifyCode();" />
             </div>
             <div>用户密码:</div>
             <div>
             <form:password id="password"  tabindex="2" path="password"  htmlEscape="true" autocomplete="off" />
            </div>             
         <c:if test="${not empty isShowVerifyCode and isShowVerifyCode.value=='true'}" var="verifycondition" scope="request">
         <div>验&nbsp;&nbsp;证&nbsp;&nbsp;码: </div>
         <div><form:input style="width:120px;" id="vcode" maxlength="4" tabindex="3" path="vcode" htmlEscape="true" autocomplete="off"/>
          <a href="#" onclick="reshowVerifyCode();return false;"><img id="verifyImage" style="width:80px;vertical-align:top" class="verfycode" src="authimg" border="0" title='点击刷新验证码' /></a>
         </div>
         </c:if>
         
         <div style="text-align:right;width:203px;height:26px;padding-top:10px">
            <a style="color:#bd5704" href="#" onclick="registernewuserFunc();return false;">注册新用户</a>
            &nbsp;&nbsp;
            <a href="#" onclick="resetpasswordFunc();return false;">忘记密码?</a>
            &nbsp;&nbsp;
         </div>
         
         <div>
         <c:if test="${not verifycondition}">
             <input type="hidden" name="vcode" id="vcode" value="1234"/>
         </c:if>               
             <input type="hidden" name="schoolGuid" value="" />
             <input type="hidden" name="lt" value="${loginTicket}" />
             <input type="hidden" name="execution" value="${flowExecutionKey}" />
             <input type="hidden" name="_eventId" value="submit" />
             
             <input name="submit" class="btn-submit" accesskey="l" value="登录" tabindex="4" type="submit" />
             
             <!-- 
             <input name="reset" class="btnn" accesskey="r" value="重置" tabindex="5" type="reset" />
             <a style="float:right;padding-right:14px" href="#" onclick="return linkx();">重置密码</a>
             -->
        </div>             
 </form:form>
</body>
</html>
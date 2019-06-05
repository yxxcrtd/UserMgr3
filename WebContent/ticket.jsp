<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %><%
String contextPath = request.getContextPath();
String cookiePath="/";
if(contextPath!=""){
    cookiePath = contextPath + "/" ;
}
javax.servlet.http.Cookie cookie = org.springframework.web.util.WebUtils.getCookie(request, "CASTGC");
String cookievalue=(cookie == null ? " " : cookie.getValue());
if(cookievalue.length()>5){
    response.getWriter().print("true");
}else{
	response.getWriter().print("false");
}
%>
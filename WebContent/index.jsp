<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>

<%
	final String queryString = request.getQueryString();
	
	final String url = request.getContextPath() + "/login" + (queryString != null ? "?" + queryString : "");
	final String logouturl = request.getContextPath() + "/logout";
	
	response.sendRedirect(response.encodeURL(url));
	//request.getServletContext().getFilterRegistration("").getInitParameter(arg0)
%>
首页Index.jsp
<br/><br/>
<br/>
<li><a href="<%=url%>">CAS登录页面</a>
<li><a href="login.jsp">自己登录页面</a>


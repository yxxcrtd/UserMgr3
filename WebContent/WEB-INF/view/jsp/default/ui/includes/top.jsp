<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:theme code="mobile.custom.css.file" var="mobileCss" text="" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	    <title>中教启星单点登录验证系统</title>
		<spring:theme code="standard.custom.css.file" var="customCssFile" />
		<spring:theme code="edustar.custom.css.file" var="edustarCssFile" />
        <link type="text/css" rel="stylesheet" href="<c:url value="${customCssFile}" />" />
        <link type="text/css" rel="stylesheet" href="<c:url value="${edustarCssFile}" />" />
	</head>
	<body id="cas">
	<%String logouturl = request.getContextPath() + "/logout"; %>
    <div class="flc-screenNavigator-view-container">
        <div class="fl-screenNavigator-view">
            <div id="header" class="flc-screenNavigator-navbar fl-navbar fl-table">
                <a href="login">
                <table border="0" style="width:100%;height:100%;">
                    <tr>
                        <td style="vertical-align:bottom;width:35%;text-align:right">
			                <div id="uinfo">
			                <c:if test="${not empty userInfo and userInfo.trueName!=''}">
			                <li>当前登录用户是：${userInfo.trueName}</li>
			                </c:if>
			                </div>
                        </td>
                        <td style="vertical-align:bottom;text-align:center">
                            <div id="uinfo">
                            <c:if test="${not empty userInfo and userInfo.trueName!=''}">
                             <a href="<%=logouturl%>">安全退出</a></li>
                             </c:if>
                            </div> 
                        </td>
                    </tr>
                </table>
                </a>
            </div>		
            <div id="content" class="fl-screenNavigator-scroll-container">

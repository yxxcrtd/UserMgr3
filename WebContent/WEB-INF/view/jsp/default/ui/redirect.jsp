<%@ page contentType="text/html; charset=UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>  
<html>  
    <title></title>  
    <head>  
        <META http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <script>  
        var redirectURL = "${returnUrl}";  
        <c:if test="${empty target}">
                window.location.href = redirectURL;
         </c:if> 
         <c:if test="${target!=null && target!='' && target!='null' }">
         window.open(redirectURL,"${target}"); 
         </c:if>
       </script>  
    </head>  
    <body>

    </body>  
</html>  

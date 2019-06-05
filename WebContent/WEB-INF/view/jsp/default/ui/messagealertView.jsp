<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <c:if test="${alertmessage != ''}">
        <script type="text/javascript">
        alert("${alertmessage}");
        </script>
    </c:if>    
    <c:if test="${script == ''}">
        <script type="text/javascript">
        ${script}
        </script>
    </c:if>    

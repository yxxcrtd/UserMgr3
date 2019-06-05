<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
<div class="box fl-panel"
    <c:if test="${messageType == 'success'}">
        <div id="msg" Class="success">
            <h2>${message}</h2>
            <p>
            <c:if test="${not empty gopage and gopage!=''}">
                <a style="font-size:13px" href="${gopage}" target="_top">返回</a>
            </c:if>
            </p>            
            <p></p>
            <p></p>
        </div>
    </c:if>    
    <c:if test="${messageType == 'error'}">
        <div id="msg" Class="errors">
            <h2>${message}</h2>
            <p>
            <c:if test="${not empty failpage and failpage!=''}">
                <a style="font-size:13px" href="${failpage}" target="_top">返回</a>
            </c:if>
            </p>
            <p></p>
        </div>
    </c:if>  
</div>
<jsp:directive.include file="includes/bottom.jsp" />
<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
<div class="box fl-panel" id="login">
      <div style="height:40px;background:#0077BB;color:#ffffff;">
      <table border="0" style="height:100%;width:100%">
          <tr>
              <td style="vertical-align:middle;text-align:right;padding-right:20px">
                    <%final String loginurl = request.getContextPath() + "/login"; %>
                    <a href="<%=loginurl%>" style="color:#ffffff;">返回登录页面</a>
              </td>
          </tr>
      </table>
      </div>         
 <div style="width:100%;">
     <div id="msg" class="success">
     <h2><spring:message code="screen.logout.header" /></h2>
     <p><spring:message code="screen.logout.success" /></p>
     <p><spring:message code="screen.logout.security" /></p>
     </div>
 </div>
</div>  
<jsp:directive.include file="includes/bottom.jsp" />  
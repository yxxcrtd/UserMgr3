<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
  <form:form method="post" id="fmm" name="fm1" action="editschool" cssClass="fm-v clearfix" htmlEscape="true">
        <input type="hidden" name="cmd" value="save"/>
        <spring:bind path="command.unitGuid">
        <input type="hidden" name="guid" value="${status.value}"/>
        </spring:bind>
        <table width="100%" height="100%">
            <tr>
                <td>
                    &nbsp;
                </td>
                <td width="70%">
                    <spring:bind path="command.*">
                    <c:if test="${not empty status.errorMessages}">
                    <div id="msg" Class="errors">
                    <font color="#FF0000">
                    <c:forEach
                    items="${status.errorMessages}"
                    var="error">
                    错误: <c:out value="${error}"/><br>
                    </c:forEach>
                    </font>
                    </div>
                    </c:if>
                    </spring:bind>
                    <h2 style="background:#BFE7FF;height:40px;vertical-align: middle;">单位信息修改</h2>
                    <div id="css3">
                        <div class="1">
                            <div id="wizardcontent">
                                <center>
                                    <table border="0" cellspacing="8" cellpadding="2">
                                        <tr>
                                            <td align="right" style="width:100px;height:25px">
                                                学校名称：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.schoolName">
                                                <input type="text" style="width:300px" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" maxlength="16" />
                                                <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
                                                必须给出学校名称
                                                </spring:bind>                                                
                                            </td>
                                        </tr>
                                         <tr>
                                            <td align="right"  style="height:25px">
                                                GUID编码：
                                            </td>
                                            <td align="left">
                                            <spring:bind path="command.unitGuid">
                                                <input type="text" style="width:300px" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"/>
                                                <input type="button" name="newguidBtn" value="生成默认Guid" onclick="getnewGuid();"/>
                                                <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
                                               必须输入GUID.
                                            </spring:bind>                                               
                                            </td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" align="center"  style="height:60px">
                                                <input type="button" class="wizardBtn" value="保存" onclick="checkSchoolData();"/>
                                            </td>
                                        </tr>
                                    </table>
                                </center>
                            </div>
                            <div style="clear: both;"></div>
                        </div>
                    </div>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table>
 </form:form>      
 <script type="text/javascript">
 function newGuid()
 {
     var guid = "";
     for (var i = 1; i <= 32; i++){
       var n = Math.floor(Math.random()*16.0).toString(16);
       guid +=   n;
       if((i==8)||(i==12)||(i==16)||(i==20))
         guid += "-";
     }
     return guid;    
 } 
 function getnewGuid(){
	 document.fm1.unitGuid.value=newGuid();
 }
 function regIsGuid(fData)
 {
     var reg = new RegExp("^[a-fA-F0-9]{8}-([a-fA-F0-9]{4}-){3}[a-fA-Z0-9]{12}$");
     return (reg.test(fData));
 }
 
 function checkSchoolData()
 {
     //使用MVC中的Validator进行验证
     if(document.fm1.schoolName.value==""){alert("必须输入学校");   return false;   }
     if(document.fm1.unitGuid.value==""){alert("必须输入GUID");   return false;   }
     if(regIsGuid(document.fm1.unitGuid.value)==false){alert("输入的GUID不合规则");   return false;}
     document.fm1.submit();
 }
 </script>    
<jsp:directive.include file="includes/bottom.jsp" />


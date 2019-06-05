<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
<form:form method="post" id="fmm" cssClass="fm-v clearfix" commandName="${commandName}" htmlEscape="true">
        <table width="100%" height="100%">
            <tr>
                <td>
                    &nbsp;
                </td>
                <td style="width:1140px">
                <c:if test="${not empty requestScope['message']}">
                    <div id="msg" Class="errors">
                        ${message}
                    </div>
                </c:if>                
                    <div id="css3">
                        <div id="mainNavCaption">
                        <h2>设置密码：</h2>
                        </div>
                        <div style="float:left">                    
                            <ul id="mainNav" class="threeStep">
                                <li class="lastDone corner"><span><a href="${commandName}">第一步</a>：输入您的用户名或邮件地址</span></li>
                                <li class="current"><span>第二步：输入您的问题答案</span></li>
                                <li class="finalStep"><span>第三步：重新设置您的新密码</span></li>
                            </ul>
                        </div>    
                        <div style="clear: both;"></div>
                            <div id="wizardcontent">
                                    <table border="0" style="width:100%;">
                                        <tr>
                                           <td align="right" style="width:400px" height="50">
                                           </td>
                                           <td></td>
                                           <td style="width:100px"></td>
                                        </tr>
                                        <tr>
                                            <td align="right" height="50">
												<label id="">问题：</label> 
											</td>
                                            <td>
                                                <div id="question">${question}</div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td align="right" height="50">
							                <label id="" for="answer">请输入答案：</label>                                          
							                </td>
                                            <td>
												<input type="text" name="answer" id="answer" onfocus="this.select();"/>                                               
												<span class="loadImg"></span>
                                                <span id="answerTip"></span>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" align="right" height="70" style="padding-right:50px;padding-bottom:10px;vertical-align:bottom;">
                                                <input type="submit" class="wizardBtn" value="下一步" />
                                            </td>
                                            <td></td>
                                        </tr>
                                    </table>
                                </div>
                        </div>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table>
  
<input type="hidden" name="execution" value="${flowExecutionKey}" />
<input type="hidden" name="_eventId" value="submit" />                        
 </form:form>          
<jsp:directive.include file="includes/bottom.jsp" />
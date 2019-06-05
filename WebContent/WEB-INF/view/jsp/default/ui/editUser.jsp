<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />
  <form:form method="post" id="fmm" name="fm1" action="edituser" cssClass="fm-v clearfix" htmlEscape="true">
        <input type="hidden" name="cmd" value="save"/>
         <spring:bind path="command.role">
             <input type="hidden" name="role" value="${status.value}"/>
         </spring:bind>            
        <spring:bind path="command.id">
        <input type="hidden" name="id" value="${status.value}"/>
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
                    <h2 style="background:#BFE7FF;height:40px;vertical-align: middle;">用户信息修改</h2>
                    <div id="css3">
                        <div class="1">
                            <div id="wizardcontent">
                                <center>
                                    <table border="0" cellspacing="8" cellpadding="2">
                                        <tr>
                                            <td align="right" style="width:100px;height:25px">
                                                登录名：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.username">
                                                <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" maxlength="16" />
                                                <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
                                                必须给出登录名, 长度在 4-16 个字符之间, 只能用英文字母或数字.
                                                </spring:bind>                                                
                                            </td>
                                        </tr>
                                         <tr>
                                            <td align="right"  style="height:25px">
                                                登录密码：
                                            </td>
                                            <td align="left">
                                            <spring:bind path="command.password">
                                                <input type="password" name="<c:out value="${status.expression}"/>" value=""/>
                                               如果不输入密码,则原来的密码仍然有效
                                            </spring:bind>                                               
                                            </td>
                                        </tr>
                                         <tr>
                                            <td align="right"  style="height:25px">
                                                再次输入密码：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.repassword">
                                                <input type="password" name="<c:out value="${status.expression}"/>" value="" />
                                             如果输入了密码，请确定您记住您的密码, 两次输入必须一致.
                                            </spring:bind>                                                    
                                            </td>
                                        </tr>  
                                    <tr>
                                            <td align="right"  style="height:25px">
                                                Email地址：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.email">
                                                <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"  maxlength="100" />
                                                <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
                                             必须输入邮件地址, 且不能和别人重复.
                                            </spring:bind>                                              
                                            </td>
                                        </tr> 
                                        
                                    <tr>
                                            <td align="right"  style="height:25px">
                                                真实姓名：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.trueName">
                                                <input type="text" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"  maxlength="100" />
                                                <font style="color: #FF0000; font-size: 14px; font-weight: bold;">*</font>
                                            </spring:bind>                                                  
                                            </td>
                                        </tr>  
<c:if test="${not empty isSelectSchool and isSelectSchool.value=='true'}">                                        
                                  <tr>
                                            <td align="right"  style="height:25px">
                                                所属单位：
                                            </td>
                                            <td align="left">
                                            <spring:bind path="command.unitGuid">
										         <select name="unitGuid"  >  
										               <option value="" >--无--</option> 
										               <c:forEach items="${schoolList}" var="school"   >
										                  <option value="<c:out value='${school.unitGuid}'/>" <c:if test="${fn:toLowerCase(status.value)==fn:toLowerCase(school.unitGuid)}">selected</c:if>>  
										                      <c:out value="${school.schoolName}"/>  
										                  </option>   
										               </c:forEach>  
										          </select>                                             
                                            </spring:bind>                                                  
                                            </td>
                                        </tr>  
</c:if>                                                                                
                                         <tr>  
                                            <td align="right"  style="height:25px">
                                                密保问题：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.question">
                                                <input type="text" id="question" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>"  style="width:300px"/>
                                                </spring:bind> 
                                                <select onchange="selectQuestion(this)">
                                                <option value="">选择密保问题....</option>
                                                <option value="您的父亲的名字">您的父亲的名字</option>
                                                <option value="您的母亲的名字">您的母亲的名字</option>
                                                <option value="您的爱人的名字">您的爱人的名字</option>
                                                <option value="您的孩子的名字">您的孩子的名字</option>
                                                <option value="您的爷爷的名字">您的爷爷的名字</option>
                                                <option value="您的奶奶的名字">您的奶奶的名字</option>
                                                <option value="您的姥爷的名字">您的姥爷的名字</option>
                                                <option value="您的姥姥的名字">您的姥姥的名字</option>
                                                <option value="您的父亲的生日">您的父亲的生日</option>
                                                <option value="您的母亲的生日">您的母亲的生日</option>
                                                <option value="您的爱人的生日">您的爱人的生日</option>
                                                <option value="您的孩子的生日">您的孩子的生日</option>
                                                <option value="您的生日是？">您的生日是？</option>
                                                <option value="您最喜欢的鲜花是什么?">您最喜欢的鲜花是什么?</option>
                                                <option value="您最喜欢的颜色是什么?">您最喜欢的颜色是什么?</option>
                                                <option value="您最喜欢的电影明星是?">您最喜欢的电影明星是?</option>
                                                <option value="您最喜欢的歌曲是?">您最喜欢的歌曲是?</option>
                                                <option value="您的座右铭是什么?">您的座右铭是什么?</option>
                                                <option value="您上的大学校名是什么?">您上的大学校名是什么?</option>
                                                </select>
                                                当重新设置密码时，系统将会询问此问题。
                                            </td>
                                        </tr>                                                                                                                     
                                         <tr>  
                                            <td align="right"  style="height:25px">
                                                问题的答案：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.answer">
                                                <input type="text" name="<c:out value="${status.expression}"/>" value="" style="width:300px"/>
                                                </spring:bind> 
                                                如果不输入答案，原来的答案保持不变
                                            </td>
                                        </tr> 
                                          <tr>                                                                                                                                                        <tr>
                                            <td align="right"  style="height:25px">
                                                                                                                        状态：
                                            </td>
                                            <td align="left">
                                                <spring:bind path="command.status">
                                                <select type="text" name="<c:out value="${status.expression}"/>">
                                                    <option value=0 <c:if test="${status.value==0}">selected</c:if>>正常</option>
                                                    <option value=1 <c:if test="${status.value==1}">selected</c:if>>待审核</option>
                                                    <option value=2 <c:if test="${status.value==2}">selected</c:if>>已锁定</option>
                                                    <option value=3 <c:if test="${status.value==3}">selected</c:if>>已删除</option>
                                                </select>
                                            </spring:bind>                                              
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td colspan="2" align="center"  style="height:60px">
                                                <input type="submit" class="wizardBtn" value="保存"/>
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
 function selectQuestion(ck)
 {
     if(ck.selectedIndex==-1){return;}
     if(ck.selectedIndex==0){return;}
     var s=ck.options[ck.selectedIndex].value;
     document.getElementById("question").value=s;
 }
 function checkUserData()
 {
     return ;
     //使用MVC中的Validator进行验证
     if(document.fm1.username.value==""){alert("必须输入登录名");   return false;   }
     if(document.fm1.password.value==""){alert("必须输入登录密码");   return false;   }
     if(document.fm1.repassword.value==""){alert("必须输入登录密码");   return false;   }
     if(document.fm1.repassword.value!=document.fm1.password.value){   alert("两次输入的密码必须一致");   return false;   }
     if(document.fm1.email.value==""){   alert("必须输入email");   return false;   }
     if(document.fm1.trueName.value==""){   alert("必须输入姓名");   return false;   }
     document.fm1.submit();
 }
 </script>    
<jsp:directive.include file="includes/bottom.jsp" />


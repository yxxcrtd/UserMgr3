<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />

        <table width="100%" height="100%" cellspacing="0" cellpadding="2" style="margin-top:0px;padding-top:0px">
            <tr>
                <td>
                    &nbsp;
                </td>
                <td width="70%">
                <form name="searchForm" method="post" action="listuser">
                <div style="height:40px;background:#0077BB;color:#ffffff;">
                <table border="0" style="height:100%;width:100%">
                    <tr>
                        <td style="vertical-align:middle;">
                                                          请输入登录名或姓名：<input type="text" id="username" style="height:18px" name="username" value="${username}"/>&nbsp;&nbsp;
                        <input type="submit" name="btnSearch" value="查找">                
                        </td>
                    </tr>
                </table>
                </div>
                </form>
                    <div id="css3">
		                  <table style="width:100%;background-color:#A9A9A9" border='0' cellspacing='1' cellpadding='2'>
						    <tr style="height:28px;font-size:13px;text-align:center;color:#0077bb;">
						        <th style="width:100px;background-color:#dbdbdb;vertical-align:middle;">登录名</th>
						        <th style="width:100px;background-color:#dbdbdb;vertical-align:middle;">姓名</th>
						        <th style="width:200px;background-color:#dbdbdb;vertical-align:middle;">Email</th>
						        <th style="width:100px;background-color:#dbdbdb;vertical-align:middle;">注册时间</th>
						        <th style="width:100px;background-color:#dbdbdb;vertical-align:middle;">角色</th>
						        <th style="width:100px;background-color:#dbdbdb;vertical-align:middle;">状态</th>
						        <th style="width:100px;background-color:#dbdbdb;vertical-align:middle;">操作</th>
						    </tr>
						   <c:forEach items="${userList}" var="u">
						    <tr style="height:26px;">
						        <td style="text-align:center;background-color:#ffffff">${u.username}</td>
						        <td style="text-align:center;background-color:#ffffff">${u.trueName}</td>
						        <td style="text-align:center;background-color:#ffffff">${u.email}</td>
						        <td style="text-align:center;background-color:#ffffff"><fmt:formatDate value="${u.createDate}" pattern="yyyy-MM-dd"/></td>
						        <td style="text-align:center;background-color:#ffffff">
						        <c:if test="${u.role==1}">系统管理员</c:if>
						        <c:if test="${u.role!=1}">用户</c:if>
						        </td>
						        <td style="text-align:center;background-color:#ffffff">
                                <c:if test="${u.status==0}">正常</c:if>
                                <c:if test="${u.status==1}">待审核</c:if>
                                <c:if test="${u.status==2}">已锁定</c:if>
                                <c:if test="${u.status==3}">已删除</c:if>
						        </td>
						        <td style="text-align:center;background-color:#ffffff">
						          <a href="edituser?id=${u.id}" style="color:blue">修改</a>&nbsp;&nbsp;
						          <!-- |&nbsp;&nbsp;<a href="deleteuser?id=${u.id}" style="color:blue">删除</a> -->
						        </td>
						    </tr>
						   </c:forEach>
						   </table> 
                    </div>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>
        </table> 
<br/>  
<div class='pager'>
	<c:if test="${not empty pager}">
		<div style='text-align:center;padding:6px 0'>
		共 <font color='red'>${pager.totalRows}</font>&nbsp;${pager.itemUnit}${pager.itemName}
		 
		 <c:if test="${pager.currentPage == 1}"><a href='#' class="btnpage" onclick="return false;">&nbsp;首&nbsp;&nbsp;页&nbsp;</a></c:if>
		 <c:if test="${pager.currentPage != 1}"><a href='${pager.firstPageUrl}' class="btnpage">&nbsp;首&nbsp;&nbsp;页&nbsp;</a></c:if>
		 
		 <c:if test="${pager.currentPage > 1}"><a href='${pager.prevPageUrl}' class="btnpage">&nbsp;上一页&nbsp;</a></c:if>
		 <c:if test="${pager.currentPage <= 1}"><a href='#' class="btnpage" onclick="return false;">&nbsp;上一页&nbsp;</a></c:if>
		 
		 <c:if test="${pager.currentPage < pager.totalPages}"><a href='${pager.nextPageUrl}' class="btnpage">&nbsp;下一页&nbsp;</a></c:if>
		 <c:if test="${pager.currentPage >= pager.totalPages}"><a href='#' class="btnpage" onclick="return false;">&nbsp;下一页&nbsp;</a></c:if>
		 
		 <c:if test="${(pager.currentPage != pager.totalPages) && (pager.totalPages != 0)}"><a href='${pager.lastPageUrl}' class="btnpage">&nbsp;尾&nbsp;&nbsp;页&nbsp;</a></c:if>
		 <c:if test="${(pager.currentPage == pager.totalPages) || (pager.totalPages == 0)}"><a href='#' class="btnpage" onclick="return false;">&nbsp;尾&nbsp;&nbsp;页&nbsp;</a></c:if>
		 页次：${pager.currentPage}/${pager.totalPages}页   ${pager.pageSize}${pager.itemUnit}${pager.itemName}/页 
		<c:if test="${pager.totalPages > 0}">转到页面:<input id='__pg' style='width:60px' value='${pager.currentPage}' /> <input type='button' value='确定' onclick="window.location='${pager.urlPattern}'.replace('{page}', document.getElementById('__pg').value);" /></c:if>
		</div>  
	</c:if>  
</div>        
<jsp:directive.include file="includes/bottom.jsp" />
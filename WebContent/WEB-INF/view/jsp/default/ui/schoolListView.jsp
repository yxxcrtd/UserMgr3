<%@ page pageEncoding="UTF-8" %>
<jsp:directive.include file="includes/top.jsp" />

        <table width="100%" height="100%" cellspacing="0" cellpadding="2" style="margin-top:0px;padding-top:0px">
            <tr>
                <td>
                    &nbsp;
                </td>
                <td width="70%">
                <form name="searchForm" method="post" action="listschool">
                <div style="height:40px;background:#0077BB;color:#ffffff;">
                <table border="0" style="height:100%;width:100%">
                    <tr>
                        <td style="vertical-align:middle;">
                                                          请输入单位名称：<input type="text" id="username" style="height:18px" name="schoolname" value="${schoolname}"/>&nbsp;&nbsp;
                        <input type="submit" name="btnSearch" value="查找"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input type="button" name="btnAdd" value="增加单位" onclick="AddSchool();">               
                        </td>
                    </tr>
                </table>
                </div>
                </form>
                    <div id="css3">
		                  <table style="width:100%;background-color:#A9A9A9" border='0' cellspacing='1' cellpadding='2'>
						    <tr style="height:28px;font-size:13px;text-align:center;color:#0077bb;">
						        <th style="width:400px;background-color:#dbdbdb;vertical-align:middle;">单位名称</th>
						        <th style="width:150px;background-color:#dbdbdb;vertical-align:middle;">GUID编号</th>
						        <th style="width:100px;background-color:#dbdbdb;vertical-align:middle;">操作</th>
						    </tr>
						   <c:forEach items="${schoolList}" var="s">
						    <tr style="height:26px;">
						        <td style="text-align:center;background-color:#ffffff">${s.schoolName}</td>
						        <td style="text-align:center;background-color:#ffffff">${s.unitGuid}</td>
						        <td style="text-align:center;background-color:#ffffff">
						          <a href="editschool?guid=${s.unitGuid}" style="color:blue">修改</a>&nbsp;&nbsp;
						          <a href="#" onclick="delSchool('${s.unitGuid}')" style="color:blue">删除</a>
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
<form name="delform" id="delform" action="delschool" method="post">
    <input type="hidden" name="guid" value=""/>
</form>
<form name="addform" id="addform" action="addschool" method="post">
    <input type="hidden" name="guid" value=""/>
    <input type="hidden" name="Id" value="0"/>
    <input type="hidden" name="unitGuid" value=""/>
    <input type="hidden" name="schoolName" value=""/>
</form>

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
function AddSchool(){
	window.document.location.href="addschool";
}
function delSchool(guid){
	if(confirm("确认删除单位吗？")==false){return;}
    document.getElementById("delform").guid.value=guid;
    document.getElementById("delform").submit();
}
</script>
<jsp:directive.include file="includes/bottom.jsp" />
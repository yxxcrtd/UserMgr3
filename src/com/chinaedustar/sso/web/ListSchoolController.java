package com.chinaedustar.sso.web;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.chinaedustar.sso.web.support.CurrentUser;

import cn.edustar.usermgr.param.query.SchoolQueryParam;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.SchoolService;
import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.util.QueryHelper;

public class ListSchoolController extends AbstractController {
    @NotNull
    private CurrentUser currentUser;
    @NotNull
    private SchoolService schoolService;
    
    private String messageView;
    
    public void setSchoolService(SchoolService schoolService){
    	this.schoolService=schoolService;
    }
    private Pager getNewPager(){
    	Pager pager=new Pager();
        pager.setItemName("学校");
        pager.setItemUnit("个");
        pager.setPageSize(20);
        return pager;
    }
    protected ModelAndView handleRequestInternal(
            final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
    	
    	final Map<String, Object> model = new HashMap<String, Object>();
    	User u=currentUser.getUser(request, response);
    	if(u==null){
	        model.put("message", "请确定您已经登录");
	        model.put("messageType", "error");
	        return new ModelAndView("casMessageView", model);    		
    	}
    	if(u!=null){
    		if(u.getRole()!=1 && !u.getUsername().toLowerCase().equals("admin")){
		        model.put("message", "请确定您拥有管理权限");
		        model.put("messageType", "error");
		        return new ModelAndView("casMessageView", model);    		
    		}
    	}
    	
    	Pager pager=getNewPager();
    	
    	String schoolname=request.getParameter("schoolname");
    	
    	String pageIndex=request.getParameter("page");
    	if(pageIndex==null){
    		pager.setCurrentPage(1);
    	}else{
    		pager.setCurrentPage(Integer.parseInt(pageIndex));
    	}
    	
    	SchoolQueryParam param = new SchoolQueryParam();
    	
    	QueryHelper qry= param.createQuery();
    	
    	if(schoolname!=null && schoolname.length()>0){
    		String queryName=schoolname.replace("\'", "");
    		queryName=queryName.replace("\"", "");
    		String sWhere=" s.schoolName LIKE '%"+ queryName +"%' or s.unitGuid ='"+ queryName +"'";
    		qry=qry.addAndWhere(sWhere);
    	}
    	
    	List<School> schoollist=this.schoolService.getSchoolList(param, pager);
    	
    	model.put("schoolList", schoollist);
    	model.put("pager", pager);
    	model.put("userInfo", u);
    	model.put("schoolname", schoolname);
        return new ModelAndView("casSchoolListView", model);
    }

	public String getMessageView() {
		return messageView;
	}

	public void setMessageView(String messageView) {
		this.messageView = messageView;
	}
	public CurrentUser getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}
}

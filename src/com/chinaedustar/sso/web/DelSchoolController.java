package com.chinaedustar.sso.web;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.chinaedustar.sso.web.support.CurrentUser;

import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.SchoolService;
import cn.edustar.usermgr.service.UserService;

public class DelSchoolController  extends AbstractController{
    /** Logger instance */
    private Logger logger = LoggerFactory.getLogger(getClass());

    @NotNull
    private SchoolService schoolService;

    @NotNull
    private UserService userService;

    @NotNull
    private CurrentUser currentUser;	
    
	public CurrentUser getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public SchoolService getSchoolService() {
		return schoolService;
	}
	
	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
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
    	
    	String schoolGuid=request.getParameter("guid");
    	if(schoolGuid==null || schoolGuid.length()==0){
	        model.put("message", "缺少参数guid");
	        model.put("messageType", "error");
	        model.put("failpage", "listschool");
	        return new ModelAndView("casMessageView", model);      		
    	}
    	
    	List<User> userlist=this.userService.getUserList(schoolGuid);
    	if(userlist==null || userlist.size()==0){
    		this.schoolService.delschool(schoolGuid);
	        model.put("message", "删除完成");
	        model.put("messageType", "success");
	        model.put("gopage", "listschool");
	        return new ModelAndView("casMessageView", model);      		
    	}else{
	        model.put("message", "该单位下还存在用户，不能删除");
	        model.put("messageType", "error");
	        model.put("failpage", "listschool");
	        return new ModelAndView("casMessageView", model);      		
    	}
    }
}

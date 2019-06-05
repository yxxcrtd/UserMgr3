package com.chinaedustar.sso.web;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.chinaedustar.sso.web.support.CurrentUser;
//import org.springframework.web.servlet.mvc.BaseCommandController;
//import org.springframework.web.servlet.mvc.SimpleFormController;
import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.param.query.UserQueryParam;
import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.UserService;
import cn.edustar.usermgr.util.MD5;
import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.util.QueryHelper;

public class ListuserController extends AbstractController {
    /** Logger instance */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @NotNull
    private UserService userService;

    @NotNull
    private CurrentUser currentUser;
    
    
    private String messageView;
    
    public void setUserService(UserService userService){
    	this.userService=userService;
    }
    private Pager getNewPager(){
    	Pager pager=new Pager();
        pager.setItemName("用户");
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
    	
    	String username=request.getParameter("username");
    	
    	String pageIndex=request.getParameter("page");
    	if(pageIndex==null){
    		pager.setCurrentPage(1);
    	}else{
    		pager.setCurrentPage(Integer.parseInt(pageIndex));
    	}
    	
    	UserQueryParam param = new UserQueryParam();
    	
    	QueryHelper qry= param.createQuery();
    	
    	logger.debug("username="+username);
    	
    	if(username!=null && username.length()>0){
    		String queryName=username.replace("\'", "");
    		queryName=queryName.replace("\"", "");
    		String sWhere=" u.username LIKE '%"+ queryName +"%' or u.trueName LIKE '%"+ queryName +"%' or u.email='"+ queryName +"'";
    		qry=qry.addAndWhere(sWhere);
    	}
    	
    	logger.debug("查询用户....");
    	List<User> userlist=this.userService.getUserList(param, pager);
    	logger.debug("查询用户结束");
    	
    	model.put("userList", userlist);
    	model.put("pager", pager);
    	model.put("userInfo", u);
    	model.put("username", username);
        return new ModelAndView("casUserListView", model);
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

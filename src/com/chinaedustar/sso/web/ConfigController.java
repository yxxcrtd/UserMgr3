package com.chinaedustar.sso.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.chinaedustar.sso.web.support.CurrentUser;
import com.chinaedustar.sso.web.support.UsernamePasswordVCodeCredentials;

import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.UserService;

public class ConfigController extends AbstractController{
    /** Logger instance */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@NotNull
    private ConfigService configService;
	
	@NotNull
	private CurrentUser currentUser;
	
	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}   
	public CurrentUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}
	
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	final Map<String, Object> model = new HashMap<String, Object>();
    	
    	if(!checkUserPower(request,response)){
	        model.put("alertmessage", "请确定您已经登录，或者拥有管理权限");
	        model.put("script", "");
	        return new ModelAndView("casAlertMessageView", model);
    	}
    	
    	String keyname = request.getParameter("key");
    	String keyvalue = request.getParameter("keyvalue");
    	
    	if(keyname!=null && keyname.length()>0){
  			this.configService.updateValueByKey(keyname, keyvalue);
	        model.put("alertmessage", "设置成功");
	        model.put("script", "");
	        return new ModelAndView("casAlertMessageView", model);
    	}else{
    		model.put("alertmessage", "设置失败,缺少关键字");
    		model.put("script", "");
	        return new ModelAndView("casAlertMessageView", model);
    	}
    }
    private boolean checkUserPower(HttpServletRequest request,HttpServletResponse response){
    	User user=currentUser.getUser(request, response);
    	if(user==null){
    		return false;
    	}
    	else if(user.getRole()!=1 && !user.getUsername().toLowerCase().equals("admin")){
    		return false;
    	}else{
    		return true;
    	}
    }
}

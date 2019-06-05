package com.chinaedustar.sso.web;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import cn.edustar.usermgr.pojos.User;

public class VerUserController extends BaseController  {
    /** Logger instance */
	private Logger logger = LoggerFactory.getLogger(getClass());	
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		final Map<String, Object> model = new HashMap<String, Object>();
		/*
		if(this.currentUser.getUser(request, response)==null){
			//服务器上没有登录
			logger.debug("服务器上没有登录 currentUser is null");
			model.put("message", "error:服务器上没有登录 ");
			return new ModelAndView("casSingleMessage",model);
		}*/
		
		String loginName=request.getParameter("loginName");
		if(loginName==null || loginName.length()==0){
			loginName=request.getParameter("loginname");
		}	
		if(loginName==null || loginName.length()==0){
			loginName=request.getParameter("username");
		}
		if(loginName==null || loginName.length()==0){
			loginName=request.getParameter("userName");
		}
		if(loginName==null || loginName.length()==0){
			logger.debug("loginName is null");
			model.put("message", "error:缺少参数loginName");
			return new ModelAndView("casSingleMessage",model);
		}
		User user=this.userService.getUserByLoginname(loginName);
		if(user==null){
			model.put("message", "error:没有找到用户");
			return new ModelAndView("casSingleMessage",model);
		}else{
			String msg=user.getId() + "," + user.getUsername() + ","
					+ user.getPassword() + "," + user.getGuid() + ","
					+ user.getTrueName() + ","
					+ user.getRole() + "," 
					+ user.getUsn()+","
					+ user.getUnitGuid();
			model.put("message", msg);
			return new ModelAndView("casSingleMessage",model);
		}
	}
}

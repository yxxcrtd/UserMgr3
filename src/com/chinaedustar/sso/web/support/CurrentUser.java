package com.chinaedustar.sso.web.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.UserService;

// 获取当前用户
public class CurrentUser {
    /** Logger instance */
	private Logger logger = LoggerFactory.getLogger(getClass());	
	private UserService userService;
	private CookieRetrievingCookieGenerator edustarUserNameCookieGenerator;
	
	/**
	 * @return
	 */
	public final User getUser(HttpServletRequest request,HttpServletResponse response){
		HttpSession session=request.getSession(false);
		if(session==null){
			logger.debug("CurrentUser session 是 空");
			return getUserFromCookies(request,response);
		}
		logger.debug("CurrentUser session id = "+session.getId());
		Object o=session.getAttribute("credentials");
		if(o==null)
		{
			logger.debug("CurrentUser session里credentials是空");
			return getUserFromCookies(request,response);
		}
		UsernamePasswordVCodeCredentials credentials=(UsernamePasswordVCodeCredentials)o;
		String loginName=credentials.getUsername();
		if(loginName==null){
			logger.debug("CurrentUser session里credentials的loginName是空");
			return getUserFromCookies(request,response);
		}
		User u=this.userService.getUserByLoginname(loginName);
		logger.debug("CurrentUser 根据session里credentials的"+loginName+"，得到User:");
		return u;		
	}
	
	private User getUserFromCookies(HttpServletRequest request,HttpServletResponse response){
		if(this.edustarUserNameCookieGenerator==null){
			logger.debug("CurrentUser edustarUserNameCookieGenerator 没注入？？？");
			return null;
		}else{
			String username=this.edustarUserNameCookieGenerator.retrieveCookieValue(request);
			if(username==null){
				logger.debug("CurrentUser edustarUserNameCookieGenerator 没得到user信息");
				return null;
			}
			User u=this.userService.getUserByLoginname(username);
			logger.debug("CurrentUser 根据cookies里credentials的loginName，得到User:",u);
			return u;
		}
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userservice) {
		this.userService = userservice;
	}
	
	public CookieRetrievingCookieGenerator getEdustarUserNameCookieGenerator() {
		return edustarUserNameCookieGenerator;
	}
	public void setEdustarUserNameCookieGenerator(
			CookieRetrievingCookieGenerator edustarUserNameCookieGenerator) {
		this.edustarUserNameCookieGenerator = edustarUserNameCookieGenerator;
	}
}

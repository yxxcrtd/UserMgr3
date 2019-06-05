package com.chinaedustar.sso.cas.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.scope.FlowScope;

import cn.edustar.usermgr.pojos.User;

import com.chinaedustar.sso.web.support.CurrentUser;

public class LoginUserInfoAction {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@NotNull
	private CurrentUser currentUser;

	
	public CurrentUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}
	
	public User getUser(final RequestContext context){
		HttpServletRequest request=WebUtils.getHttpServletRequest(context); 
		HttpServletResponse response=WebUtils.getHttpServletResponse(context);
		//logger.debug("LoginUserInfoAction 中得到CurrentUser......");
		return this.currentUser.getUser(request, response);
	}
	
	public void testInfo(final RequestContext context,String ticketId){
		HttpServletRequest request=WebUtils.getHttpServletRequest(context); 
		HttpServletResponse response=WebUtils.getHttpServletResponse(context);
		Object returnUrl=context.getFlowScope().get("returnUrl");
		if(returnUrl!=null){
			String reUrl=returnUrl.toString();
			if(reUrl.indexOf("?")>0){
				reUrl=reUrl+"&ticket="+ticketId;
			}else{
				reUrl=reUrl+"?ticket="+ticketId;
			}
			context.getFlowScope().put("returnUrl", reUrl);
			logger.debug("登录到最后：returnUrl=="+reUrl);
		}
		Object responsex=context.getFlowScope().get("response");
		if(responsex!=null){
			org.jasig.cas.authentication.principal.Response r=(org.jasig.cas.authentication.principal.Response)responsex;
			logger.debug("登录到最后：Response.Url=="+r.getUrl());
		}
	}
}

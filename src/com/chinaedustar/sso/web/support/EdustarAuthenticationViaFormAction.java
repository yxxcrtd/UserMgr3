package com.chinaedustar.sso.web.support;
import org.jasig.cas.web.flow.AuthenticationViaFormAction;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.webflow.execution.RequestContext;
import org.jasig.cas.web.support.WebUtils;
import org.jasig.cas.authentication.principal.Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;

import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.UserService;

public class EdustarAuthenticationViaFormAction extends AuthenticationViaFormAction {   
    private final String ERROR="error";   
    private final String SUCCESS="success";   
    private ConfigService configService;
    private UserService userService;
    private Logger logger = LoggerFactory.getLogger(getClass());
     public final String validatorCode(final RequestContext context, final Credentials credentials, final MessageContext messageContext) throws Exception {
         final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
         final HttpServletResponse response = WebUtils.getHttpServletResponse(context);
    	 Config config= configService.getConfigByKey("isShowVerifyCode");
    	 if(config!=null){
    		 if(config.getValue().equals("true")){
		         String vcode=(String)WebUtils.getHttpServletRequest(context).getSession().getAttribute("random");  
		         logger.debug("验证码：EdustarAuthenticationViaFormAction中Session里是："+vcode);
		         UsernamePasswordVCodeCredentials upv=(UsernamePasswordVCodeCredentials)credentials;   
		         logger.debug("验证码：UsernamePasswordVCodeCredentials中是："+upv.getVcode());
		         if(!StringUtils.hasText(upv.getVcode()) || !StringUtils.hasText(vcode)) return ERROR;   
		         if(upv.getVcode().toLowerCase().equals(vcode.toLowerCase())){   
		             //return SUCCESS;
		        	 //接着验证选择单位
		         }else{
			         MessageBuilder msgBuilder=new MessageBuilder();   
			         msgBuilder.defaultText("验证码有误！");   
			         messageContext.addMessage(msgBuilder.error().build());   
			         return ERROR;
		         }
    		 }
    	 }
    	 config= configService.getConfigByKey("isSelectSchool");
    	 if(config!=null){
    		 if(config.getValue().equals("true")){
    			 String schoolGuid=WebUtils.getHttpServletRequest(context).getParameter("schoolGuid");
    			 if(schoolGuid==null){
    		         MessageBuilder msgBuilder=new MessageBuilder();
    		         msgBuilder.defaultText("请选择登录单位！");   
    		         messageContext.addMessage(msgBuilder.error().build());   
    		         return ERROR;  
    			 }
    			//缓存选择的学校 
				Cookie cookie = new Cookie("loginSelectSchool", schoolGuid);
				cookie.setMaxAge(Integer.MAX_VALUE);
				String domain = getDomain(request.getServerName());
				if (!"".equals(domain) && !Character.isDigit(domain.charAt(0))) {
					cookie.setDomain("." + domain);
				}
				cookie.setPath("/");
				response.addCookie(cookie);    			 
    		 }
    	 }
    	 return SUCCESS;
     }

 	private String getDomain(String url) {
		String returnDomain = "";
		Boolean isIP = true;
		if (url.contains(".")) {
			String[] tempArray = url.split("\\.");
			for (int i = 0; i < tempArray.length; i++) {
				try {
					Integer.parseInt(tempArray[i]);
				} catch (NumberFormatException e) {
					isIP = false;
					break;
				}
			}
			if (isIP) {
				returnDomain = "";
			} else {
				switch (tempArray.length) {
				case 2:
					returnDomain = url;
					break;
				default:
					returnDomain = url.substring(url.indexOf(".") + 1);
					break;
				}
			}
		} else {
			returnDomain = "";
		}
		return returnDomain;
	}     
	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}   
 
}   

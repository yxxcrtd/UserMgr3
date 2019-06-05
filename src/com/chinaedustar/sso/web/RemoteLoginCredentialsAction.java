package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.web.bind.CredentialsBinder;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.execution.RequestContext;
import com.chinaedustar.sso.web.support.UsernamePasswordVCodeCredentials;
import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.service.ConfigService;

public class RemoteLoginCredentialsAction {
    /** Logger instance */
	private Logger logger = LoggerFactory.getLogger(getClass());
    
	@NotNull
    private ConfigService configService;

    /**
     * Binder that allows additional binding of form object beyond Spring
     * defaults.
     */
	private CredentialsBinder credentialsBinder;
	
    public final String generate(final RequestContext context,UsernamePasswordVCodeCredentials credentials,String loginTicket) {
        String username = context.getRequestParameters().get("username");
        String password = context.getRequestParameters().get("password");
        String vcode = context.getRequestParameters().get("vcode");
    	Config config=this.configService.getConfigByKey("isShowVerifyCode");
		if(username==null || username.length()==0){
			//需要登录名
			context.getFlowScope().put("messagetype", "error");
			context.getFlowScope().put("message", "缺少登录名");
			return "error";
		}
		if(password==null || password.length()==0){
			//需要密码？？？也许不需要
		}
		if(config!=null){
			if(config.getValue().equals("true")){
				if(vcode==null || vcode.length()==0){
					//需要验证码
					context.getFlowScope().put("messagetype", "error");
					context.getFlowScope().put("message", "单点登录系统需要验证码");
					return "error";					
				}
			}
		}
		
		credentials.setUsername(username);
		credentials.setPassword(password);
		credentials.setVcode(vcode);
		context.getFlowScope().put("credentials", credentials);
		
        return "generated";
    }
    
    public final String doBind(final RequestContext context, final Credentials credentials) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        
        logger.debug("this.credentialsBinder is NULL = "+(this.credentialsBinder==null));
        
        if (this.credentialsBinder != null && this.credentialsBinder.supports(credentials.getClass())) {
            this.credentialsBinder.bind(request, credentials);
        }else{
        	UsernamePasswordVCodeCredentials credentialsEx =(UsernamePasswordVCodeCredentials) credentials;
            String username = context.getRequestParameters().get("username");
            String password = context.getRequestParameters().get("password");
            String vcode = context.getRequestParameters().get("vcode");
        	Config config=this.configService.getConfigByKey("isShowVerifyCode");
    		if(username==null || username.length()==0){
    			//需要登录名
    			context.getFlowScope().put("messagetype", "error");
    			context.getFlowScope().put("message", "缺少登录名");
    			return "error";
    		}
    		if(password==null || password.length()==0){
    			//需要密码？？？也许不需要
    		}
    		if(config!=null){
    			if(config.getValue().equals("true")){
    				if(vcode==null || vcode.length()==0){
    					//需要验证码
    					context.getFlowScope().put("messagetype", "error");
    					context.getFlowScope().put("message", "单点登录系统需要验证码");
    					return "error";					
    				}
    			}
    		}
    		
    		credentialsEx.setUsername(username);
    		credentialsEx.setPassword(password);
    		credentialsEx.setVcode(vcode);        	
        }
        return "success";
    }  
    
    public final void setCredentialsBinder(final CredentialsBinder credentialsBinder) {
        this.credentialsBinder = credentialsBinder;
    }
    
	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}    
}

package com.chinaedustar.sso.cas.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.flow.InitialFlowSetupAction;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.UserService;

import com.chinaedustar.sso.web.support.UsernamePasswordVCodeCredentials;

public class noFlowLoginAction extends AbstractController {
    /** Logger instance */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	   @NotNull
	   private CentralAuthenticationService centralAuthenticationService;
	     
	  	@NotNull
	    private CookieRetrievingCookieGenerator warnCookieGenerator;
	 	@NotNull
	    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;
	 	
	 	private InitialFlowSetupAction initialFlowSetupAction;
	 
		@NotNull
	    private ConfigService configService;
		
		@NotNull
		private UserService userService;
		
	    private CookieRetrievingCookieGenerator edustarUserNameCookieGenerator ;

	    
	    /** Extractors for finding the service. */
	    @NotEmpty
	    private List<ArgumentExtractor> argumentExtractors;
	    protected ModelAndView handleRequestInternal(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	    	String uName = request.getParameter("username");
	    	String password = request.getParameter("password");
	    	String vcode = request.getParameter("vcode");
	    	String failpage = request.getParameter("failpage");
	    	final Map<String, Object> model = new HashMap<String, Object>();
			if(uName==null || uName.length()==0){
				//需要登录名
		        model.put("messageType", "error");
		        model.put("message", "缺少登录名");	
		        model.put("failpage", failpage);
		        return new ModelAndView("casMessageView", model);
			}
			if(password==null || password.length()==0){
				//需要密码？？？也许不需要
			}
	    	
	    	Config config=this.configService.getConfigByKey("isShowVerifyCode");
			if(config!=null){
				if(config.getValue().equals("true")){
					if(vcode==null || vcode.length()==0){
				        model.put("messageType", "error");
				        model.put("message", "单点登录系统需要验证码");
				        model.put("failpage", failpage);
				        return new ModelAndView("casMessageView", model);
					}
			         String vSessionCode=(String)request.getSession().getAttribute("random");  
			         if(!vSessionCode.toLowerCase().equals(vcode.toLowerCase())){   
					        model.put("messageType", "error");
					        model.put("message", "验证码不正确");
					        model.put("failpage", failpage);
					        return new ModelAndView("casMessageView", model);
			         }   
				}
			}
	    	
	    	//Credentials credentials =new UsernamePasswordCredentials(uName,password);
	    	Credentials credentials =new UsernamePasswordVCodeCredentials(uName,password,vcode);
	    	
	    	if (!this.initialFlowSetupAction.getPathPopulated()) {
	            final String contextPath = request.getContextPath();
	            final String cookiePath = StringUtils.hasText(contextPath) ? contextPath + "/" : "/";
	            logger.info("Setting path for cookies to: "
	                + cookiePath);
	            this.warnCookieGenerator.setCookiePath(cookiePath);
	            this.ticketGrantingTicketCookieGenerator.setCookiePath(cookiePath);
	            this.initialFlowSetupAction.SetPathPopulated(true);
	        }
	    	 final Service service = WebUtils.getService( this.argumentExtractors, request);
	    	 String ticketGrantingTicketId="";
	    	 String serviceTicket = "";
	 		try {
	 			ticketGrantingTicketId = this.centralAuthenticationService.createTicketGrantingTicket(credentials);
	 			
	 			/***
	        	 * 产生新的票据，并将票据及服务记录在缓存中
	        	 */
	 			System.out.println("service is null -------- "+(service==null));
	 			System.out.println("ticketGrantingTicketId is null -------- "+(ticketGrantingTicketId==null));
	 			System.out.println("ticketGrantingTicketId -------- "+ticketGrantingTicketId);
	 			
	 			serviceTicket= this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId,service);
	 			
	 			this.ticketGrantingTicketCookieGenerator.removeCookie(response);
	 			
	 			this.ticketGrantingTicketCookieGenerator.addCookie(request, response, ticketGrantingTicketId);
	 		 
	 			this.warnCookieGenerator.addCookie(request, response, "true");
	 			
	            //HttpSession sesson= request.getSession(false);  //参数false,如果Session没有，则返回null
	            HttpSession sesson= request.getSession();
	            sesson.setAttribute("credentials", credentials);
	            
	            
	            if(this.edustarUserNameCookieGenerator!=null){
	            	this.edustarUserNameCookieGenerator.addCookie(request, response, ((UsernamePasswordVCodeCredentials)credentials).getUsername());
	            }
	            
	            
	 		} catch (TicketException e) {
	 			//e.printStackTrace();
		        model.put("messageType", "error");
		        model.put("message", "您提供的凭证有误！请检查输入的用户或密码是否正确");
		        model.put("failpage", failpage);
		        return new ModelAndView("casMessageView", model);
	 		}
	 		//String redUrl=request.getParameter("service")+"?ticket="+serviceTicket;
	 		String redUrl="";
	 		logger.debug("redUrl="+request.getParameter("redUrl"));
	 		logger.debug("ru="+request.getParameter("ru"));
	 		logger.debug("service="+request.getParameter("service"));
	 		
	 		if(request.getParameter("redUrl")!=null && request.getParameter("redUrl").length()>0){
	 			redUrl=request.getParameter("redUrl");
	 		}else{
		 		if(request.getParameter("ru")!=null && request.getParameter("ru").length()>0){
		 			redUrl=request.getParameter("ru");
		 		}else{
		 			redUrl=request.getParameter("service");
		 		}
	 		}
	 		redUrl=redUrl+"?ticket="+serviceTicket;
	 		
	 		logger.debug("CAS登录成功,转向到"+redUrl);
	 		
	 		return new  ModelAndView(new RedirectView(redUrl));
		}
	    
  
	    
	    public void setWarnCookieGenerator(final CookieRetrievingCookieGenerator warnCookieGenerator) {
	        this.warnCookieGenerator = warnCookieGenerator;
	    }
	    public void setArgumentExtractors(
	        final List<ArgumentExtractor> argumentExtractors) {
	        this.argumentExtractors = argumentExtractors;
	    }
	    public final void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
	        this.centralAuthenticationService = centralAuthenticationService;
	    }
	    public void setTicketGrantingTicketCookieGenerator(
	            final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
	            this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
	        }



		public void setInitialFlowSetupAction(
				InitialFlowSetupAction initialFlowSetupAction) {
			this.initialFlowSetupAction = initialFlowSetupAction;
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
		
		public CookieRetrievingCookieGenerator getEdustarUserNameCookieGenerator() {
			return edustarUserNameCookieGenerator;
		}

		public void setEdustarUserNameCookieGenerator(
				CookieRetrievingCookieGenerator edustarUserNameCookieGenerator) {
			this.edustarUserNameCookieGenerator = edustarUserNameCookieGenerator;
		}	  
}
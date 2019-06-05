package com.chinaedustar.sso.cas.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import org.jasig.cas.web.flow.InitialFlowSetupAction;
import org.hibernate.validator.constraints.NotEmpty;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.authentication.principal.WebApplicationService;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.chinaedustar.sso.web.BaseController;
/**
 * 如果客户端没有票证ticket,可以通过GetTicketController来从服务器得到一个
 * @author dell
 *
 */
public class GetTicketController extends BaseController {
    /** Logger instance */
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    /** Extractors for finding the service. */
    @NotEmpty
    private List<ArgumentExtractor> argumentExtractors;
    
    /** Extracts parameters from Request object. */
    @NotNull
    private ArgumentExtractor argumentExtractor;
    
    /** CookieGenerator for the TicketGrantingTickets. */
    @NotNull
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;
    
   @NotNull
   private CentralAuthenticationService centralAuthenticationService;
	   
	private InitialFlowSetupAction initialFlowSetupAction;
   
   @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
	   
	   final Map<String, Object> model = new HashMap<String, Object>();
	   
	   logger.debug("request="+request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		response.setContentType("text/html;charset=UTF-8");
		if(this.currentUser==null){
			//服务器上没有登录
			logger.debug("服务器上没有登录 currentUser is null");
			model.put("message", "");
			return new ModelAndView("casSingleMessage",model);
		}
		if(this.ticketGrantingTicketCookieGenerator==null){
			//没有注入 ticketGrantingTicketCookieGenerator
			logger.debug("没有注入 ticketGrantingTicketCookieGenerator");
			model.put("message", "");
			return new ModelAndView("casSingleMessage",model);
		}
		//logger.debug("initialFlowSetupAction.getPathPopulated="+this.initialFlowSetupAction.getPathPopulated());
    	//if (!this.initialFlowSetupAction.getPathPopulated()) {
            final String contextPath = request.getContextPath();
            final String cookiePath = StringUtils.hasText(contextPath) ? contextPath + "/" : "/";
            logger.debug("cookiePath="+cookiePath);
            logger.info("Setting path for cookies to: " + cookiePath);
            this.ticketGrantingTicketCookieGenerator.setCookiePath(cookiePath);
        //}
		/*
		javax.servlet.http.Cookie[] Cookies= request.getCookies();
		logger.debug("Cookies 数量="+Cookies.length);
		for(int i=0;i<Cookies.length;i++){
			javax.servlet.http.Cookie c=Cookies[i];
			logger.debug("Cookie["+ i +"] Domain:"+c.getDomain()+" name="+c.getName()+" path="+c.getPath()+" value="+c.getValue()+" Secure="+c.getSecure());
		}
    		*/
            
		String ticketGrantingTicketId=this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
		if(ticketGrantingTicketId==null){
			//服务器上没有ticketGrantingTicket
			logger.debug("服务器上没有ticketGrantingTicket");
			model.put("message", "");
			return new ModelAndView("casSingleMessage",model);
		}
		else{
			logger.debug("服务器上ticketGrantingTicket="+ticketGrantingTicketId);
		}
		

		if(this.centralAuthenticationService==null){
			//没有注入centralAuthenticationService
			logger.debug("没有注入centralAuthenticationService");
			model.put("message", "");
			return new ModelAndView("casSingleMessage",model);
		}
		//final WebApplicationService service = this.argumentExtractor.extractService(request);
    	final Service service = WebUtils.getService( this.argumentExtractors, request);
    	String serviceTicket=null;
    	if(service!=null){
    		serviceTicket= this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId,service);
    	}else{
    		logger.debug("service ===null");
    	}
		if(serviceTicket==null){
			//没有找到ticket
			logger.debug("没有找到ticket");
			model.put("message", "");
			return new ModelAndView("casSingleMessage",model);
		}
		logger.debug("ticket="+serviceTicket);
		model.put("message", serviceTicket);
	    return new ModelAndView("casSingleMessage",model);
   }
   
   public final void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
       this.centralAuthenticationService = centralAuthenticationService;
   }
   
   public void setArgumentExtractors(
	        final List<ArgumentExtractor> argumentExtractors) {
	        this.argumentExtractors = argumentExtractors;
	    }
   
   public void setTicketGrantingTicketCookieGenerator(
	        final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
	        this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
   }
   
	public void setInitialFlowSetupAction(
			InitialFlowSetupAction initialFlowSetupAction) {
		this.initialFlowSetupAction = initialFlowSetupAction;
	}
	
    public final void setArgumentExtractor(final ArgumentExtractor argumentExtractor) {
        this.argumentExtractor = argumentExtractor;
    }	
}

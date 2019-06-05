package org.jasig.cas.web.flow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.bind.CredentialsBinder;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.web.util.CookieGenerator;
import org.springframework.webflow.execution.RequestContext;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import com.chinaedustar.sso.web.support.UsernamePasswordVCodeCredentials;

/**
 * Action to authenticate credentials and retrieve a TicketGrantingTicket for those credentials. If there is a request for renew, then it also generates the Service Ticket required.
 */
@SuppressWarnings("deprecation")
public class AuthenticationViaFormAction {

    /**
     * Binder that allows additional binding of form object beyond Spring
     * defaults.
     */
	private CredentialsBinder credentialsBinder;

    /** Core we delegate to for handling all ticket related tasks. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    @NotNull
    private CookieGenerator warnCookieGenerator;

    private CookieRetrievingCookieGenerator edustarUserNameCookieGenerator ;
    
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public final void doBind(final RequestContext context, final Credentials credentials) throws Exception {
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

        if (this.credentialsBinder != null && this.credentialsBinder.supports(credentials.getClass())) {
            this.credentialsBinder.bind(request, credentials);
        }
    }
    
    public final String submit(final RequestContext context, final Credentials credentials, final MessageContext messageContext) throws Exception {
    	// 验证登录票证
    	
        final String authoritativeLoginTicket = WebUtils.getLoginTicketFromFlowScope(context);
        final String providedLoginTicket = WebUtils.getLoginTicketFromRequest(context);
        if (!authoritativeLoginTicket.equals(providedLoginTicket)) {
            this.logger.warn("Invalid login ticket " + providedLoginTicket);
            final String code = "INVALID_TICKET";
            messageContext.addMessage(new MessageBuilder().error().code(code).arg(providedLoginTicket).defaultText(code).build());
            return "error";
        }

        final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        final Service service = WebUtils.getService(context);
        if (StringUtils.hasText(context.getRequestParameters().get("renew")) && ticketGrantingTicketId != null && service != null) {

            try {
                final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId, service, credentials);
                WebUtils.putServiceTicketInRequestScope(context, serviceTicketId);
                putWarnCookieIfRequestParameterPresent(context);
                return "warn";
            } catch (final TicketException e) {
                if (isCauseAuthenticationException(e)) {
                    populateErrorsInstance(e, messageContext);
                    return getAuthenticationExceptionEventId(e);
                }
                
                this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
                if (logger.isDebugEnabled()) {
                    logger.debug("Attempted to generate a ServiceTicket using renew=true with different credentials", e);
                }
            }
        }

        try {
        	String myTGT=this.centralAuthenticationService.createTicketGrantingTicket(credentials);
            WebUtils.putTicketGrantingTicketInRequestScope(context,myTGT);
            putWarnCookieIfRequestParameterPresent(context);
            
            //HttpSession sesson= WebUtils.getHttpServletRequest(context).getSession(false);  //参数false,如果Session没有，则返回null
            
            HttpSession sesson= WebUtils.getHttpServletRequest(context).getSession();
            sesson.setAttribute("credentials", credentials);
            
            //logger.debug("用户提交登录信息，生成了 credentials,保存到Session中");
            //logger.debug("Session is NULL ? "+(sesson==null));
            //if(sesson!=null){
            //	logger.debug("Session id is "+sesson.getId());
            //	logger.debug("Session里的 credentials is : "+((UsernamePasswordVCodeCredentials)sesson.getAttribute("credentials")).getUsername());
            //}
            
            
            if(this.edustarUserNameCookieGenerator!=null){
            	//logger.debug("credentials的userName保存到cookies.....");
            	this.edustarUserNameCookieGenerator.addCookie(WebUtils.getHttpServletRequest(context), WebUtils
                        .getHttpServletResponse(context), ((UsernamePasswordVCodeCredentials)credentials).getUsername());
            	//logger.debug("马上从cookies中得到userName....."+this.edustarUserNameCookieGenerator.retrieveCookieValue(WebUtils.getHttpServletRequest(context)));
            }
            
            return "success";
        } catch (final TicketException e) {
            populateErrorsInstance(e, messageContext);
            if (isCauseAuthenticationException(e))
                return getAuthenticationExceptionEventId(e);
            return "error";
        }
    }

    /**
     * 我增加了可以不需要登录票证的功能
     * @param context
     * @param credentials
     * @param messageContext
     * @param needLT
     * @return
     * @throws Exception
     */
    public final String submitEx(final RequestContext context, final Credentials credentials, final MessageContext messageContext, boolean needLT) throws Exception {
        final String authoritativeLoginTicket = WebUtils.getLoginTicketFromFlowScope(context);
        final String providedLoginTicket = WebUtils.getLoginTicketFromRequest(context);
        //logger.debug("authoritativeLoginTicket="+authoritativeLoginTicket);
        //logger.debug("providedLoginTicket="+providedLoginTicket);
        //logger.debug("needLT="+needLT);
        
    	if(needLT){
	        if (!authoritativeLoginTicket.equals(providedLoginTicket)) {
	            //this.logger.warn("Invalid login ticket " + providedLoginTicket);
	            final String code = "INVALID_TICKET";
	            messageContext.addMessage(new MessageBuilder().error().code(code).arg(providedLoginTicket).defaultText(code).build());
	            return "error";
	        }
    	}else{
    		
    	}
        
    	final String ticketGrantingTicketId = WebUtils.getTicketGrantingTicketId(context);
        final Service service = WebUtils.getService(context);
        
        //logger.debug("ticketGrantingTicketId="+ticketGrantingTicketId);
        //logger.debug("service="+service);
        //logger.debug("context.getExternalContext().getContextPath()="+context.getExternalContext().getContextPath());
        
        if (StringUtils.hasText(context.getRequestParameters().get("renew")) && ticketGrantingTicketId != null && service != null) {
        	//如果是renew参数，则销毁TGT，然后会重新生成TGT
            try {
                final String serviceTicketId = this.centralAuthenticationService.grantServiceTicket(ticketGrantingTicketId, service, credentials);
                WebUtils.putServiceTicketInRequestScope(context, serviceTicketId);
                putWarnCookieIfRequestParameterPresent(context);
                return "warn";
            } catch (final TicketException e) {
                if (isCauseAuthenticationException(e)) {
                    populateErrorsInstance(e, messageContext);
                    return getAuthenticationExceptionEventId(e);
                }
                
                this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
                if (logger.isDebugEnabled()) {
                    logger.debug("Attempted to generate a ServiceTicket using renew=true with different credentials", e);
                }
            }
        }

        try {
        	//logger.debug("准备生成TGT.....");
        	String myTGT=this.centralAuthenticationService.createTicketGrantingTicket(credentials);
        	//logger.debug("TGT 是 ",myTGT);
        	
            //HttpSession sesson= WebUtils.getHttpServletRequest(context).getSession(false);  //参数false,如果Session没有，则返回null
            HttpSession sesson= WebUtils.getHttpServletRequest(context).getSession();
            sesson.setAttribute("credentials", credentials);
            
        	//logger.debug("将 TGT 保存在RequestScope....");
            WebUtils.putTicketGrantingTicketInRequestScope(context,myTGT );
            //logger.debug("将 TGT 保存在RequestScope....成功");
            
            //logger.debug("写cookies....");
            putWarnCookieIfRequestParameterPresent(context);
            //logger.debug("写cookies....成功");
            
            //logger.debug("credentials的userName保存到cookies.....");
            if(this.edustarUserNameCookieGenerator!=null){
            	this.edustarUserNameCookieGenerator.addCookie(WebUtils.getHttpServletRequest(context), WebUtils
                        .getHttpServletResponse(context), ((UsernamePasswordVCodeCredentials)credentials).getUsername());
            }
            
            return "success";
        } catch (final TicketException e) {
        	//logger.debug("这是啥错误？");
        	//logger.debug("这是啥错误？？？", e);
            populateErrorsInstance(e, messageContext);
            //if (isCauseAuthenticationException(e))
            //    return getAuthenticationExceptionEventId(e);
			context.getFlowScope().put("messagetype", "error");
			context.getFlowScope().put("message", "登录错误，用户名或密码不正确");            
            return "error";
        }
    }


    private void populateErrorsInstance(final TicketException e, final MessageContext messageContext) {

        try {
            messageContext.addMessage(new MessageBuilder().error().code(e.getCode()).defaultText(e.getCode()).build());
        } catch (final Exception fe) {
            logger.error(fe.getMessage(), fe);
        }
    }

    private void putWarnCookieIfRequestParameterPresent(final RequestContext context) {
        final HttpServletResponse response = WebUtils.getHttpServletResponse(context);
        
        
        if (StringUtils.hasText(context.getExternalContext().getRequestParameterMap().get("warn"))) {
            this.warnCookieGenerator.addCookie(response, "true");
        } else {
            this.warnCookieGenerator.removeCookie(response);
        }
    }
    
    private AuthenticationException getAuthenticationExceptionAsCause(final TicketException e) {
        return (AuthenticationException) e.getCause();
    }

    private String getAuthenticationExceptionEventId(final TicketException e) {
        final AuthenticationException authEx = getAuthenticationExceptionAsCause(e);

        if (this.logger.isDebugEnabled())
            this.logger.debug("An authentication error has occurred. Returning the event id " + authEx.getType());

        return authEx.getType();
    }

    private boolean isCauseAuthenticationException(final TicketException e) {
        return e.getCause() != null && AuthenticationException.class.isAssignableFrom(e.getCause().getClass());
    }

    public final void setCentralAuthenticationService(final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    /**
     * Set a CredentialsBinder for additional binding of the HttpServletRequest
     * to the Credentials instance, beyond our default binding of the
     * Credentials as a Form Object in Spring WebMVC parlance. By the time we
     * invoke this CredentialsBinder, we have already engaged in default binding
     * such that for each HttpServletRequest parameter, if there was a JavaBean
     * property of the Credentials implementation of the same name, we have set
     * that property to be the value of the corresponding request parameter.
     * This CredentialsBinder plugin point exists to allow consideration of
     * things other than HttpServletRequest parameters in populating the
     * Credentials (or more sophisticated consideration of the
     * HttpServletRequest parameters).
     *
     * @param credentialsBinder the credentials binder to set.
     */
    public final void setCredentialsBinder(final CredentialsBinder credentialsBinder) {
        this.credentialsBinder = credentialsBinder;
    }
    
    public final void setWarnCookieGenerator(final CookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

	public CookieRetrievingCookieGenerator getEdustarUserNameCookieGenerator() {
		return edustarUserNameCookieGenerator;
	}

	public void setEdustarUserNameCookieGenerator(
			CookieRetrievingCookieGenerator edustarUserNameCookieGenerator) {
		this.edustarUserNameCookieGenerator = edustarUserNameCookieGenerator;
	}
    
}

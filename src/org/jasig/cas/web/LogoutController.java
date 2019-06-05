package org.jasig.cas.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
import org.jasig.cas.services.RegisteredService;
import org.jasig.cas.services.ServicesManager;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller to delete ticket granting ticket cookie in order to log out of single sign on. 
 * This controller implements the idea of the ESUP Portail's Logout patch to allow for redirecting to a url on logout.
 * It also exposes a log out link to the view via the WebConstants.LOGOUT constant.
 */
public final class LogoutController extends AbstractController {

    /** Logger instance */
    private final Log log = LogFactory.getLog(getClass());
    
    /** The CORE to which we delegate for all CAS functionality. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    /** CookieGenerator for TGT Cookie */
    @NotNull
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

    private CookieRetrievingCookieGenerator edustarUserNameCookieGenerator;
    /** CookieGenerator for Warn Cookie */
    @NotNull
    private CookieRetrievingCookieGenerator warnCookieGenerator;

    /** Logout view name. */
    @NotNull
    private String logoutView;

    @NotNull
    private ServicesManager servicesManager;

    /**
     * Boolean to determine if we will redirect to any url provided in the
     * service request parameter.
     */
    private boolean followServiceRedirects;
    
    public LogoutController() {
        setCacheSeconds(0);
    }

    protected ModelAndView handleRequestInternal(
        final HttpServletRequest request, final HttpServletResponse response)
        throws Exception {
        final String ticketGrantingTicketId = this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
        final String service = request.getParameter("service");
        
        final String returnUrl = request.getParameter("ru");
        
        log.debug("退出注销操作 ticketGrantingTicketId="+ticketGrantingTicketId);
        
        if (ticketGrantingTicketId != null) {
            this.centralAuthenticationService.destroyTicketGrantingTicket(ticketGrantingTicketId);
            this.ticketGrantingTicketCookieGenerator.removeCookie(response);
            this.warnCookieGenerator.removeCookie(response);
            if(this.edustarUserNameCookieGenerator!=null){
            	this.edustarUserNameCookieGenerator.removeCookie(response);
            }
        }
        
        /*清除Session*/
        Enumeration e=request.getSession().getAttributeNames(); 
        while(e.hasMoreElements()){ 
        	String sessionName=(String)e.nextElement(); 
        	request.getSession().removeAttribute(sessionName); 
        } 
        request.getSession().invalidate();
        
        if(returnUrl==null || returnUrl.length()==0){
	        if (this.followServiceRedirects && service != null) {
	            final RegisteredService rService = this.servicesManager.findServiceBy(new SimpleWebApplicationServiceImpl(service));
	            if (rService != null && rService.isEnabled()) {
	                return new ModelAndView(new RedirectView(service));
	            }
	        }
        }
        else{
        	return new ModelAndView(new RedirectView(returnUrl));
        }
        
        return new ModelAndView(this.logoutView);
    }

    public void setTicketGrantingTicketCookieGenerator(
        final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
        this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
    }

    public void setEdustarUserNameCookieGenerator(final CookieRetrievingCookieGenerator edustarUserNameCookieGenerator){
    	this.edustarUserNameCookieGenerator=edustarUserNameCookieGenerator;
    }
    public void setWarnCookieGenerator(final CookieRetrievingCookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

    /**
     * @param centralAuthenticationService The centralAuthenticationService to set.
     */
    public void setCentralAuthenticationService(
        final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    public void setFollowServiceRedirects(final boolean followServiceRedirects) {
        this.followServiceRedirects = followServiceRedirects;
    }

    public void setLogoutView(final String logoutView) {
        this.logoutView = logoutView;
    }

    public void setServicesManager(final ServicesManager servicesManager) {
        this.servicesManager = servicesManager;
    }
    
}

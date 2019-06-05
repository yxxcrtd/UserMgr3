package org.jasig.cas.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Maps requests for usernames to a page that displays the Login URL for an OpenId Identity Provider.
 */
public final class OpenIdProviderController extends AbstractController {

    @NotNull
    private String loginUrl;
    
    protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        return new ModelAndView("openIdProviderView", "openid_server", this.loginUrl);
    }

    public void setLoginUrl(final String loginUrl) {
        this.loginUrl = loginUrl;
    }
    
}

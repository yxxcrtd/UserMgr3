package org.jasig.cas.web;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Delegating controller.
 * 
 * Tries to find a controller among its delegates, that can handle the current request. If none is found, an error is generated.
 */
public class DelegatingController extends AbstractController {
	
    List<DelegateController> delegates;
    
    /** View if Service Ticket Validation Fails. */
    private static final String DEFAULT_ERROR_VIEW_NAME = "casServiceFailureView";

    /** The view to redirect if no delegate can handle the request. */
    @NotNull
    private String failureView = DEFAULT_ERROR_VIEW_NAME;

    /**
     * Handles the request.
     * Ask all delegates if they can handle the current request.
     * The first to answer true is elected as the delegate that will process the request.
     * If no controller answers true, we redirect to the error page.
     * @param request the request to handle
     * @param response the response to write to
     * @return the model and view object
     * @throws Exception if an error occurs during request handling
     */
    protected final ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        for (DelegateController delegate : delegates) {
            if (delegate.canHandle(request, response)) {
                return delegate.handleRequest(request, response);
            }
        }
        return generateErrorView("INVALID_REQUEST", "INVALID_REQUEST", null);
    }

    private ModelAndView generateErrorView(final String code, final String description, final Object[] args) {
        final ModelAndView modelAndView = new ModelAndView(this.failureView);
        final String convertedDescription = getMessageSourceAccessor().getMessage(description, args, description);
        modelAndView.addObject("code", code);
        modelAndView.addObject("description", convertedDescription);
        return modelAndView;
    }

    /**
     * @param delegates the delegate controllers to set
     */
    @NotNull
    public void setDelegates(List<DelegateController> delegates) {
        this.delegates = delegates;
    }

    /**
     * @param failureView The failureView to set.
     */
    public final void setFailureView(final String failureView) {
        this.failureView = failureView;
    }
    
}

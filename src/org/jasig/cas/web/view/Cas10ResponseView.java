package org.jasig.cas.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.validation.Assertion;

/**
 * Custom View to Return the CAS 1.0 Protocol Response. Implemented as a view class rather than a JSP (like CAS 2.0 spec) because of the requirement of the line feeds to be "\n".
 */
public final class Cas10ResponseView extends AbstractCasView {

    /**
     * Indicate whether this view will be generating the success response or not.
     */
    private boolean successResponse;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	protected void renderMergedOutputModel(final Map model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Assertion assertion = getAssertionFrom(model);

        if (this.successResponse) {
            response.getWriter().print("yes\n" + assertion.getChainedAuthentications().get(0).getPrincipal().getId() + "\n");
        } else {
            response.getWriter().print("no\n\n");
        }
    }

    public void setSuccessResponse(final boolean successResponse) {
        this.successResponse = successResponse;
    }
    
}

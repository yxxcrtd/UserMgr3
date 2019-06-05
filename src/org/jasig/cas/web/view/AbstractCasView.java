package org.jasig.cas.web.view;

import java.util.Map;

import org.jasig.cas.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Abstract class to handle retrieving the Assertion from the model.
 */
public abstract class AbstractCasView extends AbstractView {
    
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final Assertion getAssertionFrom(final Map<String, Object> model) {
        return (Assertion) model.get("assertion");
    }
    
}

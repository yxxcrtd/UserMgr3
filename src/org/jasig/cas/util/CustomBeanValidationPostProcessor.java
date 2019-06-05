package org.jasig.cas.util;

import java.lang.annotation.ElementType;

import javax.validation.Configuration;
import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;

/**
 * Provides a custom {@link javax.validation.TraversableResolver} that should work in JPA2 environments without the JPA2 restrictions (i.e. getters for all properties).
 */
public final class CustomBeanValidationPostProcessor extends BeanValidationPostProcessor {

    @SuppressWarnings("rawtypes")
	public CustomBeanValidationPostProcessor() {
        final Configuration configuration = Validation.byDefaultProvider().configure();
        configuration.traversableResolver(new TraversableResolver() {
            public boolean isReachable(final Object o, final Path.Node node, final Class<?> aClass, final Path path, final ElementType elementType) {
                return true;
            }

            public boolean isCascadable(final Object o, final Path.Node node, final Class<?> aClass, final Path path, final ElementType elementType) {
                return true;
            }
        });

        final Validator validator = configuration.buildValidatorFactory().getValidator();
        setValidator(validator);
    }
    
}

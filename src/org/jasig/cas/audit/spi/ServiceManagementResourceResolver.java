package org.jasig.cas.audit.spi;

import org.aspectj.lang.JoinPoint;
import org.jasig.cas.util.AopUtils;

import com.github.inspektr.audit.spi.AuditResourceResolver;

/**
 * Resolves a service id to the service. The expectation is that args[0] is a Long.
 */
public final class ServiceManagementResourceResolver implements AuditResourceResolver {

    public String[] resolveFrom(final JoinPoint target, final Object returnValue) {
        return findService(target);
    }

    public String[] resolveFrom(final JoinPoint target, final Exception exception) {
        return findService(target);
    }

    private String[] findService(final JoinPoint joinPoint) {
        final JoinPoint j = AopUtils.unWrapJoinPoint(joinPoint);

        final Long id = (Long) j.getArgs()[0];

        if (id == null) {
            return new String[] {""};
        }

        return new String[] {"id=" + id};
    }
}

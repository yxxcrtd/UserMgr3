package org.jasig.cas.authentication;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.github.inspektr.audit.annotation.Audit;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.handler.AuthenticationHandler;
import org.jasig.cas.authentication.handler.NamedAuthenticationHandler;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Principal;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.3.5
 */
public abstract class AbstractAuthenticationManager implements AuthenticationManager {

    /** Log instance for logging events, errors, warnings, etc. */
    protected final Logger log = LoggerFactory.getLogger(AuthenticationManagerImpl.class);

    /** An array of AuthenticationAttributesPopulators. */
    @NotNull
    private List<AuthenticationMetaDataPopulator> authenticationMetaDataPopulators = new ArrayList<AuthenticationMetaDataPopulator>();

    @Audit(
        action="AUTHENTICATION",
        actionResolverName="AUTHENTICATION_RESOLVER",
        resourceResolverName="AUTHENTICATION_RESOURCE_RESOLVER")
    @Profiled(tag = "AUTHENTICATE", logFailuresSeparately = false)
    public final Authentication authenticate(final Credentials credentials) throws AuthenticationException {

        final Pair<AuthenticationHandler, Principal> pair = authenticateAndObtainPrincipal(credentials);

        // we can only get here if the above method doesn't throw an exception. And if it doesn't, then the pair must not be null.
        final Principal p = pair.getSecond();
        log.info(String.format("Principal found: %s", p.getId()));

        if (log.isDebugEnabled()) {
            log.debug(String.format("Attribute map for %s: %s", p.getId(), p.getAttributes()));
        }

        Authentication authentication = new MutableAuthentication(p);

        if (pair.getFirst()instanceof NamedAuthenticationHandler) {
            final NamedAuthenticationHandler a = (NamedAuthenticationHandler) pair.getFirst();
            authentication.getAttributes().put(AuthenticationManager.AUTHENTICATION_METHOD_ATTRIBUTE, a.getName());
        }

        for (final AuthenticationMetaDataPopulator authenticationMetaDataPopulator : this.authenticationMetaDataPopulators) {
            authentication = authenticationMetaDataPopulator.populateAttributes(authentication, credentials);
        }

        return new ImmutableAuthentication(authentication.getPrincipal(),
            authentication.getAttributes());
    }

    /**
     * @param authenticationMetaDataPopulators the authenticationMetaDataPopulators to set.
     */
    public final void setAuthenticationMetaDataPopulators(final List<AuthenticationMetaDataPopulator> authenticationMetaDataPopulators) {
        this.authenticationMetaDataPopulators = authenticationMetaDataPopulators;
    }

    /**
     * Follows the same rules as the "authenticate" method (i.e. should only return a fully populated object, or throw an exception)
     *
     * @param credentials the credentials to check
     * @return the pair of authentication handler and principal.  CANNOT be NULL.
     * @throws AuthenticationException if there is an error authenticating.
     */
    protected abstract Pair<AuthenticationHandler,Principal> authenticateAndObtainPrincipal(Credentials credentials) throws AuthenticationException;


    /**
     * Logs the exception occurred as an error.
     * 
     * @param handlerName The class name of the authentication handler.
     * @param credentials Client credentials subject to authentication. 
     * @param e The exception that has occurred during authentication attempt.
     */
    protected void logAuthenticationHandlerError(final String handlerName, final Credentials credentials, final Exception e) {
        if (this.log.isErrorEnabled())
            this.log.error("{} threw error authenticating {}", new Object[] {handlerName, credentials, e});
    }


    protected static class Pair<A,B> {

        private final A first;

        private final B second;

        public Pair(final A first, final B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return this.first;
        }

        public B getSecond() {
            return this.second;
        }
    }

}

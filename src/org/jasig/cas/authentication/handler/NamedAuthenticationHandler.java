package org.jasig.cas.authentication.handler;

/**
 * Offers AuthenticationHandlers a way to identify themselves by a user-configured name.
 */
public interface NamedAuthenticationHandler extends AuthenticationHandler {
    
    String getName();
    
}

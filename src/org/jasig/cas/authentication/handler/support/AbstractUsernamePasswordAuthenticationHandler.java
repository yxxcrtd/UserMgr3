package org.jasig.cas.authentication.handler.support;

import org.jasig.cas.authentication.handler.*;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

import javax.validation.constraints.NotNull;

/**
 * Abstract class to override supports so that we don't need to duplicate the check for UsernamePasswordCredentials.
 */
public abstract class AbstractUsernamePasswordAuthenticationHandler extends AbstractPreAndPostProcessingAuthenticationHandler {

    /** Default class to support if one is not supplied. */
    private static final Class<UsernamePasswordCredentials> DEFAULT_CLASS = UsernamePasswordCredentials.class;

    /** Class that this instance will support. */
    @NotNull
    private Class< ? > classToSupport = DEFAULT_CLASS;

    /**
     * Boolean to determine whether to support subclasses of the class to
     * support.
     */
    private boolean supportSubClasses = true;

    /**
     * PasswordEncoder to be used by subclasses to encode passwords for
     * comparing against a resource.
     */
    @NotNull
    private PasswordEncoder passwordEncoder = new PlainTextPasswordEncoder();

    @NotNull
    private PrincipalNameTransformer principalNameTransformer = new NoOpPrincipalNameTransformer();

    /**
     * Method automatically handles conversion to UsernamePasswordCredentials
     * and delegates to abstract authenticateUsernamePasswordInternal so
     * subclasses do not need to cast.
     */
    protected final boolean doAuthentication(final Credentials credentials) throws AuthenticationException {
        return authenticateUsernamePasswordInternal((UsernamePasswordCredentials) credentials);
    }

    /**
     * Abstract convenience method that assumes the credentials passed in are a subclass of UsernamePasswordCredentials.
     * 
     * @param credentials the credentials representing the Username and Password
     * presented to CAS
     * @return true if the credentials are authentic, false otherwise.
     * @throws AuthenticationException if authenticity cannot be determined.
     * 
     * 验证用户名和密码的具体操作通过实现该方法来实现
     */
    protected abstract boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException;

    /**
     * Method to return the PasswordEncoder to be used to encode passwords.
     * 
     * @return the PasswordEncoder associated with this class.
     */
    protected final PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    protected final PrincipalNameTransformer getPrincipalNameTransformer() {
        return this.principalNameTransformer;
    }

    /**
     * Method to set the class to support.
     * 
     * @param classToSupport the class we want this handler to support
     * explicitly.
     */
    public final void setClassToSupport(final Class< ? > classToSupport) {
        this.classToSupport = classToSupport;
    }

    /**
     * Method to set whether this handler will support subclasses of the
     * supported class.
     * 
     * @param supportSubClasses boolean of whether to support subclasses or not.
     */
    public final void setSupportSubClasses(final boolean supportSubClasses) {
        this.supportSubClasses = supportSubClasses;
    }

    /**
     * Sets the PasswordEncoder to be used with this class.
     * 
     * @param passwordEncoder the PasswordEncoder to use when encoding passwords.
     * 
     * 指定适当的密码加密
     */
    public final void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public final void setPrincipalNameTransformer(final PrincipalNameTransformer principalNameTransformer) {
        this.principalNameTransformer = principalNameTransformer;
    }

    /**
     * @return true if the credentials are not null and the credentials class is
     * equal to the class defined in classToSupport.
     */
    public final boolean supports(final Credentials credentials) {
        return credentials != null
            && (this.classToSupport.equals(credentials.getClass()) || (this.classToSupport
                .isAssignableFrom(credentials.getClass()))
                && this.supportSubClasses);
    }

}

package org.jasig.cas.authentication.handler;

/**
 * Generic abstract exception to extend when you don't know what the heck is going on.
 */
public abstract class UncategorizedAuthenticationException extends AuthenticationException {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 872764495107139229L;

	/**
     * Constructor that allows for providing a custom error code for this class.
     * Error codes are often used to resolve exceptions into messages. Providing
     * a custom error code allows the use of a different message.
     * 
     * @param code the custom code to use with this exception.
     */
    public UncategorizedAuthenticationException(final String code) {
        super(code);
    }

    /**
     * Constructor that allows for chaining of exceptions and a custom error code.
     * 
     * @param code the custom error code to use in message resolving.
     * @param throwable the chained exception.
     */
    public UncategorizedAuthenticationException(final String code,
        final Throwable throwable) {
        super(code, throwable);
    }
}

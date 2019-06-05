package org.jasig.cas.monitor;

/**
 * Monitor status code inspired by HTTP status codes.
 */
public enum StatusCode {
    ERROR(500),
    WARN(400),
    INFO(300),
    OK(200),
    UNKNOWN(100);

    /** Status code numerical value */
    private final int value;

    /**
     * Creates a new instance with the given numeric value.
     *
     * @param numericValue Numeric status code value.
     */
    StatusCode(final int numericValue) {
        this.value = numericValue;
    }

    /**
     * Gets the numeric value of the status code.  Higher values describe more severe conditions.
     *
     * @return Numeric status code value.
     */
    public int value() {
        return this.value;
    }
    
}

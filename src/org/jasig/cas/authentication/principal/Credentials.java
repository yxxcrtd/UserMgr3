package org.jasig.cas.authentication.principal;

import java.io.Serializable;

/**
 * Marker interface for credentials required to authenticate a principal.
 * <p>
 * The Credentials is an opaque object that represents the information a user
 * asserts proves that the user is who it says it is. In CAS, any information
 * that is to be presented for authentication must be wrapped (or implement) the
 * Credentials interface. Credentials can contain a userid and password, or a
 * Certificate, or an IP address, or a cookie value. Some credentials require
 * validation, while others (such as container based or Filter based validation)
 * are inherently trustworthy.
 * <p>
 * People who choose to implement their own Credentials object should take care that
 * any toString() they implement does not accidentally expose confidential information.
 * toString() can be called from various portions of the CAS code base, including logging
 * statements, and thus toString should never contain anything confidential or anything
 * that should not be logged.
 * <p>
 * Credentials objects that are included in CAS do NOT expose any confidential information.
 */
public interface Credentials extends Serializable {
	
    // marker interface contains no methods
	
}

package org.jasig.cas.authentication.handler;

import org.jasig.cas.authentication.principal.Credentials;

/**
 * Validate Credentials support for AuthenticationManagerImpl.
 * <p>
 * Determines that Credentials are valid. Password-based credentials may be
 * tested against an external LDAP, Kerberos, JDBC source. Certificates may be
 * checked against a list of CA's and do the usual chain validation.
 * Implementations must be parameterized with their sources of information.
 * <p>
 * Callers to this class should first call supports to determine if the AuthenticationHandler can authenticate the credentials provided.
 */
public interface AuthenticationHandler {

    /**
     * Method to determine if the credentials supplied are valid.
     * 
     * 该方法担当验证认证信息的任务，这也是需要扩展的主要方法，根据情况与存储合法认证信息的介质进行交互，返回boolean类型的值，true表示验证通过，false表示验证失败。
     * 
     * @param credentials The credentials to validate.
     * @return true if valid, return false otherwise.
     * @throws AuthenticationException An AuthenticationException can contain
     * details about why a particular authentication request failed.
     */
    boolean authenticate(Credentials credentials) throws AuthenticationException;

    /**
     * Method to check if the handler knows how to handle the credentials provided. 
     * It may be a simple check of the Credentials class or something more complicated such as scanning the information contained in the Credentials object.
     * 
     * 检查所给的包含认证信息的Credentials是否受当前AuthenticationHandler支持
     * 
     * @param credentials The credentials to check.
     * @return true if the handler supports the Credentials, false othewrise.
     */
    boolean supports(Credentials credentials);
    
}

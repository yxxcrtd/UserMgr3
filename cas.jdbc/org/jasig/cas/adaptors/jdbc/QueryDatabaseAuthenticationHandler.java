package org.jasig.cas.adaptors.jdbc;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.validation.constraints.NotNull;

/**
 * Class that if provided a query that returns a password (parameter of query must be username) will compare that password to a translated version of the password provided by the user.
 * If they match, then authentication succeeds. Default password translator is plaintext translator.
 */
public class QueryDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    @NotNull
    private String sql;

    @SuppressWarnings("deprecation")
	protected final boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
        final String username = getPrincipalNameTransformer().transform(credentials.getUsername());
        final String password = credentials.getPassword();
        final String encryptedPassword = this.getPasswordEncoder().encode(
            password);
        
        try {
            final String dbPassword = getJdbcTemplate().queryForObject(this.sql, String.class, username);
            return dbPassword.equals(encryptedPassword);
        } catch (final IncorrectResultSizeDataAccessException e) {
            // this means the username was not found.
            return false;
        }
    }

    /**
     * @param sql The sql to set.
     */
    public void setSql(final String sql) {
        this.sql = sql;
    }
    
}

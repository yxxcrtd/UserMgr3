package org.jasig.cas.adaptors.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.jdbc.datasource.DataSourceUtils;

/**
 * This class attempts to authenticate the user by opening a connection to the database with the provided username and password. Servers are provided as a Properties class with the key being the URL and the property being the type of database driver needed.
 */
public class BindModeSearchDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    protected final boolean authenticateUsernamePasswordInternal(
        final UsernamePasswordCredentials credentials)
        throws AuthenticationException {
        final String username = credentials.getUsername();
        final String password = credentials.getPassword();
        try {
            final Connection c = this.getDataSource().getConnection(username, password);
            DataSourceUtils.releaseConnection(c, this.getDataSource());
            return true;
        } catch (final SQLException e) {
            return false;
        }
    }
    
}

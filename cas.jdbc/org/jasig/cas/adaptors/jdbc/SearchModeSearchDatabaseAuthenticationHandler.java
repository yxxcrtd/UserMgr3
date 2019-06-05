package org.jasig.cas.adaptors.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.beans.factory.InitializingBean;

import cn.edustar.usermgr.util.MD5;

import javax.validation.constraints.NotNull;

/**
 * Class that given a table, username field and password field will query a database table with the provided encryption technique to see if the user exists.
 * This class defaults to a PasswordTranslator of PlainTextPasswordTranslator.
 */
public class SearchModeSearchDatabaseAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler implements InitializingBean {

	private final Log log = LogFactory.getLog(getClass());
	
    private static final String SQL_PREFIX = "Select count('x') from ";

    @NotNull
    private String fieldUser;

    @NotNull
    private String fieldPassword;

    @NotNull
    private String tableUsers;

    private String sql;

    @SuppressWarnings("deprecation")
	protected final boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
        final String transformedUsername = getPrincipalNameTransformer().transform(credentials.getUsername());
        final String encyptedPassword = getPasswordEncoder().encode(credentials.getPassword());
        final String schoolGuid=credentials.getSchoolGuid();

        //办公的用户的密码曾经把密码+chinaEdustar.com 了进行MD5处理了。
        final String encyptedPassword2 = getPasswordEncoder().encode(credentials.getPassword()+"chinaEdustar.com");
        final String md5Password1=MD5.toMD5(credentials.getPassword()+"chinaEdustar.com", 16);
        final String md5Password2=MD5.toMD5(credentials.getPassword(), 16);
        
        log.debug("验证用户和密码:transformedUsername="+transformedUsername+",encyptedPassword="+encyptedPassword);
        
        String SQL;
        if(schoolGuid==null || schoolGuid.length()==0){
        	SQL=this.sql;
        }else{
        	//SQL=this.sql+" And unitGuid='"+schoolGuid+"'";
        	SQL=SQL_PREFIX + this.tableUsers + " Where " + this.fieldUser
        	        + " = ? And (" + this.fieldPassword + " = ? or " + this.fieldPassword + " = ?)  And status=0 And unitGuid=?"; 
        }
        log.debug(SQL);
        int count =0;
        if(schoolGuid==null || schoolGuid.length()==0){
        	count = getJdbcTemplate().queryForInt(SQL,transformedUsername, encyptedPassword);
        	if(count==0){
        		count = getJdbcTemplate().queryForInt(SQL,transformedUsername, encyptedPassword2);
        	}
        	if(count==0){
        		count = getJdbcTemplate().queryForInt(SQL,transformedUsername, md5Password1);
        	}
        	if(count==0){
        		count = getJdbcTemplate().queryForInt(SQL,transformedUsername, md5Password2);
        	}
        	
        }else{
        	count = getJdbcTemplate().queryForInt(SQL,transformedUsername, encyptedPassword,encyptedPassword2,schoolGuid);
        	if(count==0){
        		count = getJdbcTemplate().queryForInt(SQL,transformedUsername, md5Password1,md5Password2,schoolGuid);
        	}
        }
        
        log.debug("int count="+count);
        
        return count > 0;
        
    }

    public void afterPropertiesSet() throws Exception {
        this.sql = SQL_PREFIX + this.tableUsers + " Where " + this.fieldUser
        + " = ? And " + this.fieldPassword + " = ?  And status=0"; 
    }

    /**
     * @param fieldPassword The fieldPassword to set.
     */
    public final void setFieldPassword(final String fieldPassword) {
        this.fieldPassword = fieldPassword;
    }

    /**
     * @param fieldUser The fieldUser to set.
     */
    public final void setFieldUser(final String fieldUser) {
        this.fieldUser = fieldUser;
    }

    /**
     * @param tableUsers The tableUsers to set.
     */
    public final void setTableUsers(final String tableUsers) {
        this.tableUsers = tableUsers;
    }
    
}

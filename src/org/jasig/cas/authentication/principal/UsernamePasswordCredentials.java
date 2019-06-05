package org.jasig.cas.authentication.principal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * UsernamePasswordCredentials respresents the username and password that a user may provide in order to prove the authenticity of who they say they are.
 */
public class UsernamePasswordCredentials implements Credentials {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4408913902552789095L;

    /** The username. */
    @NotNull
    @Size(min=1, message = "required.username")
    private String username;

    /** The password. */
    @NotNull
    @Size(min=1, message = "required.password")
    private String password;

    /*选择登录单位，可以为空*/
	private String schoolGuid;
	
    public String getSchoolGuid() {   
        return schoolGuid;   
    }   
    public void setSchoolGuid(String schoolGuid) {   
        this.schoolGuid = schoolGuid;   
    }  
    
    /**
     * @return Returns the password.
     */
    public final String getPassword() {
        return this.password;
    }

    /**
     * @param password The password to set.
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return Returns the userName.
     */
    public final String getUsername() {
        return this.username;
    }

    /**
     * @param userName The userName to set.
     */
    public final void setUsername(final String userName) {
        this.username = userName;
    }

    public String toString() {
        return "[username: " + this.username + "]";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsernamePasswordCredentials that = (UsernamePasswordCredentials) o;

        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
    
    public UsernamePasswordCredentials(){
    	
    }
    public UsernamePasswordCredentials(String username,String password){
    	this.username=username;
    	this.password=password;
    }
    
}

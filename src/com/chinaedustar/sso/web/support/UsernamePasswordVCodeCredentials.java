package com.chinaedustar.sso.web.support;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/**
 * 继承原来的登录验证的UsernamePasswordCredentials，增加了验证码属性
 * @author dell
 *
 */
public class UsernamePasswordVCodeCredentials extends UsernamePasswordCredentials {
	@NotNull   
    @Size(min=1,message = "required.vcode")
	private String vcode;

	
	private static final long serialVersionUID = 8225580510676152041L;

 	
   
    public String getVcode() {   
        return vcode;   
    }   
   
    public void setVcode(String vcode) {   
        this.vcode = vcode;   
    }    
    
    public UsernamePasswordVCodeCredentials(){
    	
    }
    public UsernamePasswordVCodeCredentials(String username,String password,String vcode){
    	this.setUsername(username);
    	this.setPassword(password);
    	this.setVcode(vcode);
    }
    public UsernamePasswordVCodeCredentials(String username,String password,String vcode,String schoolGuid){
    	this.setUsername(username);
    	this.setPassword(password);
    	this.setVcode(vcode);
    	this.setSchoolGuid(schoolGuid);
    }
}

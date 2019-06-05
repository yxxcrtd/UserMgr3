package com.chinaedustar.sso.web.support;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.UserService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *注册用户时的验证器，如果用户输入的数据不符合要求，则提示用户重新输入 
 *added by baimindong 
 */
public final class RegisterValidator implements Validator {

	private UserService userService;
	
    public boolean supports(final Class<?> clazz) {
        return (BaseUser.class.isAssignableFrom(clazz) || User.class.isAssignableFrom(clazz));
        //判定参数类别，当传入Class对象与当前类类别相同，或是当前类的父类（或当前类实现的接口）时返回真。这
        //里我们将其用于对校验对象的数据类型进行判定（这里的判定条件为：校验对象必须是BaseUser类的实例）
    }
    
    public void validate(final Object o, final Errors errors) {
        //将传入的对象转换为我们需要的
    	final BaseUser u = (BaseUser) o;
    	if(u.getUsername().length()==0){
    		errors.rejectValue("username", 
    				"usernameisneed",
    				null,
    				"登录名必须输入！");    		
    	}
    	if(u.getId()==0){
	    	if(u.getPassword().length()==0){
	    		errors.rejectValue("password", 
	    				"passwordisneed",
	    				null,
	    				"密码必须输入！");    		
	    	}  
    	}
    	if (!u.getPassword().equals(u.getRepassword()))
    	{
	    	errors.rejectValue("repassword",
	    	"notsame",
	    	null,
	    	"两次输入的密码不一致！");
    	}    
    	if(u.getEmail().length()==0){
    		errors.rejectValue("email", 
    				"emailisneed",
    				null,
    				"Email必须输入！");    		
    	}   
    	if(u.getTrueName().length()==0){
    		errors.rejectValue("trueName", 
    				"truenameisneed",
    				null,
    				"姓名必须输入！");    		
    	}    
    	if(u.getId()==0){
	    	if(u.getQuestion().length()>0){
	        	if(u.getAnswer().length()==0){
	        		errors.rejectValue("answer", 
	        				"answerisneed",
	        				null,
	        				"输入了问题，就必须输入答案！");    		
	        	}     	
	    	}
    	}
    	/*检查用户名是否已经存在*/
    	if(u.getUsername().length()>0){
    		User uu=userService.getUserByLoginname(u.getUsername());
	    	if ( uu!= null && !uu.getId().equals(u.getId())) {
		    	errors.rejectValue("username",
		    	"existed",
		    	null,
		    	"该登录名称已存在！");
	    	}
    	}
    	/*检查Email是否已经存在*/
    	if(u.getEmail().length()>0){
    		User _uu=userService.getUserByEmail(u.getEmail());
	    	if (_uu != null && !_uu.getId().equals(u.getId())) {
		    	errors.rejectValue("email",
		    	"existed",
		    	null,
		    	"该Email已存在！");
	    	}
    	}
    }

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}

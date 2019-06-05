package com.chinaedustar.sso.web;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;
//import org.springframework.web.servlet.mvc.BaseCommandController;
//import org.springframework.web.servlet.mvc.SimpleFormController;
import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.SchoolService;
import cn.edustar.usermgr.service.UserService;
import cn.edustar.usermgr.util.MD5;

public class RegisterController extends SimpleFormController {
    /** Logger instance */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @NotNull
    private UserService userService;
    @NotNull
    private ConfigService configService;
    @NotNull
    private SchoolService schoolService;  
    
    private String messageView;
    
	public RegisterController(){
		
	}
	
    public void setUserService(UserService userService){
    	this.userService=userService;
    }
    public void setSchoolService(SchoolService schoolService){
    	this.schoolService=schoolService;
    }    
    /**
     * 准备初始化数据 
     */
    protected Map referenceData(HttpServletRequest req) {
    	Map<String, Object> refData = new HashMap<String, Object>();
    	
    	List<School> schoolList= this.schoolService.getSchools();
    	refData.put("schoolList", schoolList);
    	
    	Config isSelectSchool= this.configService.getConfigByKey("isSelectSchool");
    	refData.put("isSelectSchool", isSelectSchool);
    	
        return(refData);
    } 
    
    protected ModelAndView onSubmit(Object cmd, BindException ex)throws Exception {
    	HashMap result_map;
    	Config config=this.configService.getConfigByKey("isRegisterEnable");
    	logger.debug("config is null ? " +(config==null));
		if(config!=null){
			logger.debug("config.getValue()= " +config.getValue());
			if(config.getValue().equals("false")){
				result_map = new HashMap();
				result_map.put("messageType", "error");
				result_map.put("message", "系统不允许用户注册");
				return new ModelAndView(this.messageView, result_map);
				
			}
		}
		User user = (User) cmd;
		user.setLastLoginIp("");
		user.setPassword(MD5.toMD5(user.getPassword(),32));
		user.setLoginTimes(0);
		user.setUsn(1);
		user.setStatus(1);
		user.setCurrentLoginIp("");
		user.setRole(3);
		user.setLastLoginTime(new Date());
		if(user.getAnswer().length()>0){
			user.setAnswer(MD5.toMD5(user.getAnswer(),32));
		}
		this.userService.saveOrUpdate(user);
		result_map = new HashMap();
		result_map.put("userinfo", user);
		return new ModelAndView(this.getSuccessView(), result_map);
    }

	public String getMessageView() {
		return messageView;
	}

	public void setMessageView(String messageView) {
		this.messageView = messageView;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
    
}

package com.chinaedustar.sso.web;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.chinaedustar.sso.web.support.CurrentUser;
//import org.springframework.web.servlet.mvc.BaseCommandController;
//import org.springframework.web.servlet.mvc.SimpleFormController;
import cn.edustar.usermgr.param.query.SchoolQueryParam;
import cn.edustar.usermgr.pojos.Config;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.service.ConfigService;
import cn.edustar.usermgr.service.SchoolService;
import cn.edustar.usermgr.util.MD5;
import cn.edustar.usermgr.util.Pager;
import cn.edustar.usermgr.util.QueryHelper;

public class AddSchoolController extends SimpleFormController {
    /** Logger instance */
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @NotNull
    private SchoolService schoolService;

    @NotNull
    private CurrentUser currentUser;
    
    @NotNull
    private ConfigService configService;    
    
    private String messageView="casMessageView";
    
    public void setSchoolService(SchoolService schoolService){
    	this.schoolService=schoolService;
    }
    protected ModelAndView onSubmit(Object cmd, BindException ex)throws Exception {
    	logger.debug("提交输入页面");
    	
    	HashMap result_map= new HashMap();
		School school = (School) cmd;
		this.schoolService.saveOrUpdate(school);
		result_map.put("schoolinfo", school);
		return new ModelAndView(this.getSuccessView(), result_map);
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	final Map<String, Object> model = new HashMap<String, Object>();
    	School school = new School();
        return school;
    }
    
	public String getMessageView() {
		return messageView;
	}

	public void setMessageView(String messageView) {
		this.messageView = messageView;
	}
	public CurrentUser getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
    
}

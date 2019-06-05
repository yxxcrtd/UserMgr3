package com.chinaedustar.sso.web.support;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.service.SchoolService;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *注册用户时的验证器，如果用户输入的数据不符合要求，则提示用户重新输入 
 *added by baimindong 
 */
public final class SchoolValidator implements Validator {

	private SchoolService schoolService;
	
    public boolean supports(final Class<?> clazz) {
        return School.class.isAssignableFrom(clazz);
        //判定参数类别，当传入Class对象与当前类类别相同，或是当前类的父类（或当前类实现的接口）时返回真。这
        //里我们将其用于对校验对象的数据类型进行判定（这里的判定条件为：校验对象必须是School类的实例）
    }
    
    public void validate(final Object o, final Errors errors) {
        //将传入的对象转换为我们需要的
    	final School school = (School) o;
    	if(school.getSchoolName().length()==0){
    		errors.rejectValue("schoolName", 
    				"schoolNameisneed",
    				null,
    				"学校名称必须输入！");    		
    	}
    	/*
    	if(school.getUnitGuid().length()==0){
    		errors.rejectValue("unitGuid", 
    				"unitGuidisneed",
    				null,
    				"GUID必须输入！");    		
    	}   
		*/
    	
    	/*检查用户名是否已经存在*/
		School _school=schoolService.getSchoolByName(school.getSchoolName());
    	if ( _school!= null && _school.getId()!=school.getId()) {
	    	errors.rejectValue("schoolName",
	    	"existed",
	    	null,
	    	"学校名称已存在！");
    	}
		_school=schoolService.getSchool(school.getUnitGuid());
    	if ( _school!= null && _school.getId()!=school.getId()) {
	    	errors.rejectValue("unitGuid",
	    	"existed",
	    	null,
	    	"GUID已存在！");
    	}
	    	
    }

	public SchoolService getSchoolService() {
		return schoolService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
}

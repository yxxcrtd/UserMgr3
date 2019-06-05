package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.execution.RequestContext;

import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.service.UserService;
import cn.edustar.usermgr.util.MD5;

public class findPasswordAction{
    /** Logger instance */
    private Logger logger = LoggerFactory.getLogger(getClass());
    private UserService userService;
    
    public String CheckVode(final RequestContext context,String vcode){
    	final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
    	HttpSession session=request.getSession(false);
    	if(session==null){
    		logger.debug("session=null");	
    		return "error";
    	}
    	else{
    		if(session.getAttribute("random")==null)
    		{
    			logger.debug("session random =null");
    			return "error";
    		}else{
    			logger.debug("vcode="+vcode);
    			logger.debug("session vcode="+session.getAttribute("random").toString());
    			if(vcode.toLowerCase().equals(session.getAttribute("random").toString().toLowerCase())){
    				return "ok";
    			}else{
    				return "error";
    			}
    		}
    	}
    }
    public String CheckQuestion(String username){
    	User user=userService.getUserByLoginnameOrEmail(username);
    	if(user==null){
    		logger.debug("查找用户"+username+",没找到");
    		return "nouser";
    	}
    	if(user.getQuestion()==null || user.getQuestion().length()==0){
    		logger.debug("查找用户"+username+",没找到用户的问题");
    		return "noquestion";
    	}
    	logger.debug("用户的问题是："+user.getQuestion());
    	return user.getQuestion();
    }
    public String CheckAnswer(String loginName,String answer){
    	String md5Password=MD5.toMD5(answer, 16);
    	logger.debug(answer+" MD5="+md5Password);
    	boolean bOK=userService.checkUserAnswerByLoginnameOrEmail(loginName,md5Password);
    	if(!bOK){
    		md5Password=MD5.toMD5(answer, 32);
    		bOK=userService.checkUserAnswerByLoginnameOrEmail(loginName,md5Password);
    	}
    	if(!bOK){
    		bOK=userService.checkUserAnswerByLoginnameOrEmail(loginName,answer);
    	}    	
    	if(!bOK){
    		logger.debug("查找用户"+loginName+",回答问题错误");
    		return "wrong";
    	}
    	logger.debug("查找用户"+loginName+",回答问题正确");
    	return "success";
    }
    public String SavePassword(String loginName,String password){
    	String md5Password=MD5.toMD5(password, 32);
    	boolean bOK=userService.SavePasswordByLoginnameOrEmail(loginName,md5Password);
    	if(bOK){
    		return "success";
    	}
    	else{
    		return "更新用户"+loginName+"密码失败！";
    	}
    }
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}

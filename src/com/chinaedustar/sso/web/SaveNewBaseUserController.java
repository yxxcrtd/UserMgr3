package com.chinaedustar.sso.web;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edustar.usermgr.pojos.User;

import com.alibaba.fastjson.JSON;

/**
 * 保存新用户
 */
public class SaveNewBaseUserController extends BaseController {
	Logger log = LoggerFactory.getLogger(SaveNewBaseUserController.class);
	@ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	String json = URLDecoder.decode(request.getParameter("json"), "UTF-8");
    	log.info("接收的json：" + json);
    	if (null != json && !"".equals(json)) {
	    	User user = new User();
	    	user.setGuid((String) JSON.parseObject(json).get("guid"));
	    	user.setUsername((String) JSON.parseObject(json).get("username"));
	    	user.setTrueName((String) JSON.parseObject(json).get("trueName"));
	    	user.setPassword((String) JSON.parseObject(json).get("password"));
	    	user.setEmail((String) JSON.parseObject(json).get("email"));
	    	user.setCreateDate(new Date((Long)JSON.parseObject(json).get("createDate")));
	    	user.setStatus((Integer) JSON.parseObject(json).get("status"));
	    	user.setLastLoginIp((String) JSON.parseObject(json).get("lastLoginIp"));
			user.setLastLoginTime(new Date((Long) JSON.parseObject(json).get("lastLoginTime")));
			user.setCurrentLoginIp((String) JSON.parseObject(json).get("currentLoginIp"));
			user.setCurrentLoginTime(new Date((Long) JSON.parseObject(json).get("currentLoginTime")));
			user.setLoginTimes((Integer) JSON.parseObject(json).get("loginTimes"));
	    	user.setQuestion((String) JSON.parseObject(json).get("question"));
	    	user.setAnswer((String) JSON.parseObject(json).get("answer"));
			user.setUsn((Integer) JSON.parseObject(json).get("usn"));
			user.setRole((Integer) JSON.parseObject(json).get("role"));			
	    	String roleName = (String) JSON.parseObject(json).get("roleName");
	    	if (null != roleName && !"".equals(roleName)) {
	    		user.setRoleName(roleName);
	    	}
			userService.saveOrUpdate(user);
			response.getWriter().print("ok");
    	} else {
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}


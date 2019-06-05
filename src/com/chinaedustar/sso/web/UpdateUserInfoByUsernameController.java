package com.chinaedustar.sso.web;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根据用户名修改用户真实姓名、邮件地址和用户角色
 */
public class UpdateUserInfoByUsernameController extends BaseController {

    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	String username = URLDecoder.decode(request.getParameter("username"), "UTF-8");
    	String trueName = URLDecoder.decode(request.getParameter("trueName"), "UTF-8");
    	String email =  URLDecoder.decode(request.getParameter("email"), "UTF-8");
    	String role = request.getParameter("role");
    	
    	if (null != username && !"".equals(username) && null != trueName && !"".equals(trueName) && null != email && !"".equals(email) && null != role && !"".equals(role)) {
    		userService.updateUserInfoByUsername(username, trueName, email, Integer.valueOf(role));
    		response.getWriter().print("ok");
    	} else {
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}

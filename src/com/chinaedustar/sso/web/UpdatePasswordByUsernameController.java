package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edustar.usermgr.BaseUser;
import cn.edustar.usermgr.util.MD5;

/**
 * 根据用户名修改用户密码
 */
public class UpdatePasswordByUsernameController extends BaseController {
	Logger log = LoggerFactory.getLogger(UpdatePasswordByUsernameController.class);
    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	String newPassword = request.getParameter("newPassword");
    	if (null != username && !"".equals(username) && null != newPassword && !"".equals(newPassword) && null != password && !"".equals(password)) {
    		log.info("服务器端验证当前的登录用户1：" + currentUser.getUser(request, response));
//    		System.out.println("服务器端验证当前的登录用户2：" + currentUser.getUserFromCookies(request, response));
//    		if (null != currentUser.getUser(request, response)) {
        		// final int count = this.getJdbcTemplate().update("UPDATE T_User SET Password = ? WHERE UserName = ?", new String[] { MD5.toMD5(password), username });
        		BaseUser user = userService.getUserByLoginname(username);
        		if (null != user) {
        			if (user.getPassword().equals(MD5.toMD5(password))) {
        	    		userService.updatePasswordByUsername(username, newPassword);
        	    		// 返回成功标识
        	    		response.getWriter().print("ok");
        			} else {
        				// 当前密码错误
        				response.getWriter().print("wrong");
        			}
        		}
//    		}
    	} else {
    		// 返回失败标识
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}

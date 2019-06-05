package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edustar.usermgr.BaseUser;

/**
 * 根据用户名重置用户密码
 */
public class ResetPasswordByUsernameController extends BaseController {

    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");
    	if (null != username && !"".equals(username) && null != password && !"".equals(password)) {
    		// final int count = this.getJdbcTemplate().update("UPDATE T_User SET Password = ? WHERE UserName = ?", new String[] { MD5.toMD5(password), username });
    		BaseUser user = userService.getUserByLoginname(username);
    		if (null != user) {
    	    	userService.updatePasswordByUsername(username, password);
    	    	// 返回成功标识
    	    	response.getWriter().print("ok");
    		} else {
				response.getWriter().print("wrong");
    		}
    	} else {
    		// 返回失败标识
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}

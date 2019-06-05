package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根据用户名删除用户
 */
public class DeleteUserController extends BaseController {

    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	String username = request.getParameter("username");
    	logger.debug("（根据用户名删除用户）传递过来的用户名参数：" + username);
    	
    	if (null != username && !"".equals(username)) {
    		//String sql = "DELETE FROM T_User WHERE UserName = ?";
    		//Object[] params = new Object[] { username };
    		//this.getJdbcTemplate().update(sql, params);
    		userService.deleteUser(username);
    		response.getWriter().print("ok");
    	} else {
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}

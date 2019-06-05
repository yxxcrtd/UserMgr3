package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根据用户名修改用户状态
 */
public class UpdateStatusByUsernameController extends BaseController {

    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	
    	String username = request.getParameter("username");
    	String status = request.getParameter("status");
    	if (null != username && !"".equals(username) && null != status && !"".equals(status)) {
    		userService.updateStatusByUsername(username, Integer.valueOf(status));
    		response.getWriter().print("操作成功！");
    	} else {
    		response.getWriter().print("参数无效！");
    	}
        return null;
    }
    
}

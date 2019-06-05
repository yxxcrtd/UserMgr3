package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import cn.edustar.usermgr.BaseUser;

/**
 * 根据用户名得到用户对象
 */
public class GetUserByUsernameController extends BaseController {

    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	// 判断是否是登录用户
//    	if (null == currentUser.getUser(request, response)) {
//    		System.out.println("没有登录信息！");
//    		return null;
//    	}
//    	System.out.println("继续......！");
    	
    	response.setContentType("text/html; charset=UTF-8");
    	String username = request.getParameter("username");
    	if (null != username && !"".equals(username)) {
    		// List<BaseUser> userList = getJdbcTemplate().query("SELECT * FROM T_User WHERE UserName = '" + username + "'", new BeanPropertyRowMapper(BaseUser.class));
    		BaseUser user = userService.getUserByQueryString(username);
    		if (null != user) {
//    			Map map = new HashMap();
//    			map.put("user", user);
//    			return new ModelAndView(new MappingJacksonJsonView(), map);
    			ModelMap model = new ModelMap();
    			model.put("user", user);
    			return new ModelAndView(new MappingJacksonJsonView(), model);
    		}
    	} else {
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}

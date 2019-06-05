package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 获取用户总数
 */
public class GetUserCountsController extends BaseController {

    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	// final int count = getJdbcTemplate().queryForInt("SELECT COUNT(*) FROM T_User");
    	final int count = userService.getUserCounts();
    	logger.debug("用户数量：" + count);
    	response.setContentType("text/html;charset=UTF-8");
    	response.getWriter().print(count);
        return null;
    }
    
}

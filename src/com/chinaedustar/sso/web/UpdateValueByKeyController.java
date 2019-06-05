package com.chinaedustar.sso.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 配置表中根据key修改value
 */
public class UpdateValueByKeyController extends BaseController {

    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	String key = request.getParameter("key");
    	String value = request.getParameter("value");
    	if (null != key && !"".equals(key) && null != value && !"".equals(value)) {
    		//final int count = this.getJdbcTemplate().update("UPDATE Config SET value = ? WHERE key = ?", new String[] { key, value });
    		configService.updateValueByKey(key, value);
    		response.getWriter().print("ok");
    	} else {
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}

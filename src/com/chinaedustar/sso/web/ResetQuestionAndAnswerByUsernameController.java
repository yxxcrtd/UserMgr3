package com.chinaedustar.sso.web;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根据用户名重置用户问题和答案
 */
public class ResetQuestionAndAnswerByUsernameController extends BaseController {
	Logger log = LoggerFactory.getLogger(ResetQuestionAndAnswerByUsernameController.class);
    @ResponseBody
	protected ModelAndView handleRequestInternal(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	response.setContentType("text/html; charset=UTF-8");
    	String username = request.getParameter("username");
    	String question = URLDecoder.decode(request.getParameter("question"), "UTF-8");
    	logger.info("（根据用户名重置用户问题和答案）接收到用户设置的问题(Method 1)：" + question);
    	logger.info("（根据用户名重置用户问题和答案）接收到用户设置的问题(Method 2)：" + URLDecoder.decode(URLDecoder.decode(question, "UTF-8"), "UTF-8"));
    	String answer = URLDecoder.decode(request.getParameter("answer"), "UTF-8");
    	logger.info("（根据用户名重置用户问题和答案）接收到用户设置的答案：" + answer);
    	if (null != username && !"".equals(username) && null != question && !"".equals(question) && null != answer && !"".equals(answer)) {
    		userService.resetQuestionAndAnswerByUsername(username, question, answer);
    		response.getWriter().print("ok");
    	} else {
    		response.getWriter().print("error");
    	}
        return null;
    }
    
}

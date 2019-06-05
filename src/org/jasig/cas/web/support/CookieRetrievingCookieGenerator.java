/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.cas.web.support;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.authentication.principal.RememberMeCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.util.CookieGenerator;

/**
 * Extends CookieGenerator to allow you to retrieve a value from a request.
 *  
 * 将TGT添加至Cookie及对Cookie进行管理，也支持“记住我”
 */
public class CookieRetrievingCookieGenerator extends CookieGenerator {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    /** The maximum age the cookie should be remembered for.
     * The default is three months (7889231 in seconds, according to Google) */
    private int rememberMeMaxAge = 7889231;
    
    public void addCookie(final HttpServletRequest request, final HttpServletResponse response, final String cookieValue) {
        
        if (!StringUtils.hasText(request.getParameter(RememberMeCredentials.REQUEST_PARAMETER_REMEMBER_ME))) {
        	
        	//logger.debug("真的写入cookie["+ getCookieName() +"]="+cookieValue+".....");
        	super.addCookie(response, cookieValue);
        	//logger.debug("内部马上得到cookie["+ getCookieName() +"]="+retrieveCookieValue(request)+".....");
        	
        } else {
        	
            final Cookie cookie = createCookie(cookieValue);
            cookie.setMaxAge(this.rememberMeMaxAge);
            if (isCookieSecure()) {
                cookie.setSecure(true);
            }
            response.addCookie(cookie);
            
        	//logger.debug("真的写入cookie["+ getCookieName() +"]="+cookieValue+".....");
        	//logger.debug("this.rememberMeMaxAge="+this.rememberMeMaxAge+".....");
        	//logger.debug("isCookieSecure="+isCookieSecure()+".....");
            
        }
    }

    public String retrieveCookieValue(final HttpServletRequest request) {
    	
    	//logger.debug("request="+request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
    	
        final Cookie cookie = org.springframework.web.util.WebUtils.getCookie(
            request, getCookieName());

        String cookievalue=( cookie == null ? null : cookie.getValue());
        
        //logger.debug("尝试取回"+ this.getCookiePath() +"下的cookie["+ getCookieName() +"]="+cookievalue);
        
        return cookievalue;
    }
    
    public void setRememberMeMaxAge(final int maxAge) {
        this.rememberMeMaxAge = maxAge;
    }
}

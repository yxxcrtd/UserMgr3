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
package org.jasig.cas.web.flow;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.web.support.ArgumentExtractor;
import org.jasig.cas.web.support.CookieRetrievingCookieGenerator;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import cn.edustar.usermgr.pojos.User;
import cn.edustar.usermgr.pojos.School;
import cn.edustar.usermgr.service.SchoolService;
import cn.edustar.usermgr.dao.hibernate.SchoolDaoHibernate;
import com.chinaedustar.sso.web.support.CurrentUser;
import com.chinaedustar.sso.web.support.UsernamePasswordVCodeCredentials;

/**
 * Class to automatically set the paths for the CookieGenerators.
 * <p>
 * Note: This is technically not threadsafe, but because its overriding with a
 * constant value it doesn't matter.
 * <p>
 * Note: As of CAS 3.1, this is a required class that retrieves and exposes the
 * values in the two cookies for subclasses to use.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.1
 */
public final class InitialFlowSetupAction extends AbstractAction {

    /** CookieGenerator for the Warnings. */
    @NotNull
    private CookieRetrievingCookieGenerator warnCookieGenerator;

    /** CookieGenerator for the TicketGrantingTickets. */
    @NotNull
    private CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator;

    private CookieRetrievingCookieGenerator edustarUserNameCookieGenerator;
    
    /** Extractors for finding the service. */
    @NotNull
    @Size(min=1)
    private List<ArgumentExtractor> argumentExtractors;

    /** Boolean to note whether we've set the values on the generators or not. */
    private boolean pathPopulated = false;
    
    private CurrentUser currentUser;
    
    private SchoolService schoolService;
    /** Logger instance */
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public boolean getPathPopulated(){
    	return this.pathPopulated;
    }
    
    public void SetPathPopulated(boolean pathPopulated){
    	this.pathPopulated=pathPopulated;
    }
    
    protected Event doExecute(final RequestContext context) throws Exception {
        logger.debug("------Login初始化------");
        final HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        final HttpServletResponse response = WebUtils.getHttpServletResponse(context);
        if(this.currentUser!=null){
        	//logger.debug("Login初始化:尝试检查得到用户信息----------------------------------------");
        	User u=this.currentUser.getUser(request, response);
        }
        if(this.edustarUserNameCookieGenerator!=null){
        	//logger.debug("Login初始化:从cookies中得到username:"+this.edustarUserNameCookieGenerator.retrieveCookieValue(request));
        }       
        if (!this.pathPopulated) {
            final String contextPath = context.getExternalContext().getContextPath();
            final String cookiePath = StringUtils.hasText(contextPath) ? contextPath + "/" : "/";
            //logger.info("Setting path for cookies to: " + cookiePath);
            this.warnCookieGenerator.setCookiePath(cookiePath);
            this.ticketGrantingTicketCookieGenerator.setCookiePath(cookiePath);
            this.pathPopulated = true;
        }
        

        
        //logger.debug("Login初始化:从cookies中得到TGT:"+this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request));
        //ticketGrantingTicketId的有效范围是 FlowScope!!!
        
        String TGT=this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
        logger.debug("------Login初始化:将ticketGrantingTicketId="+TGT+"保存在FlowScope");
        context.getFlowScope().put(
            "ticketGrantingTicketId", TGT);
        
        //warnCookieValue的有效范围是 FlowScope!!!
        context.getFlowScope().put(
            "warnCookieValue",
            Boolean.valueOf(this.warnCookieGenerator.retrieveCookieValue(request)));
        
        
        //得到学校列表
        List<School> schools= this.schoolService.getSchools();
        schools.add(0, new School("","--不限--"));
        context.getFlowScope().put("schoolList",schools);
        //得到缓存
        String loginSelectSchool="";
		Cookie[] cookies = request.getCookies();		
		if(cookies != null)
		{
			for (int i = 0; i < cookies.length; i++) {
		        Cookie cookie = cookies[i];
		        if(cookie.getName().equals("loginSelectSchool"))
		        {
		        	loginSelectSchool=cookie.getValue();
		        }  
		      }
		}
		context.getFlowScope().put("loginSelectSchool",loginSelectSchool);
		
        final Service service = WebUtils.getService(this.argumentExtractors,context);
        
        //logger.debug("service is null :"+(service==null));
        //if (service != null)
        //{
        //	logger.debug("service Id=:"+service.getId());
        //}
        
        if (service != null && logger.isDebugEnabled()) {
            logger.debug("------Login初始化: Placing service in FlowScope: " + service.getId());
        }
        
        //service的有效范围是 FlowScope!!!
        context.getFlowScope().put("service", service);

        return result("success");
    }

    public void setTicketGrantingTicketCookieGenerator(
        final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator) {
        this.ticketGrantingTicketCookieGenerator = ticketGrantingTicketCookieGenerator;
    }

    public void setEdustarUserNameCookieGenerator(final CookieRetrievingCookieGenerator ticketGrantingTicketCookieGenerator){
    	this.edustarUserNameCookieGenerator=ticketGrantingTicketCookieGenerator;
    }
    
    public void setWarnCookieGenerator(final CookieRetrievingCookieGenerator warnCookieGenerator) {
        this.warnCookieGenerator = warnCookieGenerator;
    }

    public void setArgumentExtractors(
        final List<ArgumentExtractor> argumentExtractors) {
        this.argumentExtractors = argumentExtractors;
    }

	public CurrentUser getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(CurrentUser currentUser) {
		this.currentUser = currentUser;
	}

	public SchoolService getSchoolService() {
		return schoolService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}
}

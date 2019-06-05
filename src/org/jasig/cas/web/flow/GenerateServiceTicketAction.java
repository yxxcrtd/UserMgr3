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

import org.jasig.cas.CentralAuthenticationService;
import org.jasig.cas.authentication.principal.Credentials;
import org.jasig.cas.authentication.principal.Service;
import org.jasig.cas.ticket.TicketException;
import org.jasig.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.validation.constraints.NotNull;

/**
 * Action to generate a service ticket for a given Ticket Granting Ticket and
 * Service.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.0.4
 */
public final class GenerateServiceTicketAction extends AbstractAction {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /** Instance of CentralAuthenticationService. */
    @NotNull
    private CentralAuthenticationService centralAuthenticationService;

    protected Event doExecute(final RequestContext context) {
        final Service service = WebUtils.getService(context);
        final String ticketGrantingTicket = WebUtils.getTicketGrantingTicketId(context);
        //logger.debug("执行到:GenerateServiceTicketAction.......");
        try {
            final String serviceTicketId = this.centralAuthenticationService
                .grantServiceTicket(ticketGrantingTicket,
                    service);
            //logger.debug("执行到:GenerateServiceTicketAction.......得到serviceTicketId:"+serviceTicketId);
            WebUtils.putServiceTicketInRequestScope(context,
                serviceTicketId);
            //logger.debug("执行到:GenerateServiceTicketAction........将serviceTicketId="+serviceTicketId+"保存到RequestScope");
            //logger.debug("执行到:GenerateServiceTicketAction........返回"+success().getId());
            return success();
        } 
        catch (final TicketException e) {
        	//logger.debug("执行到:GenerateServiceTicketAction........错误[TicketException]："+e.getMessage());
            if (isGatewayPresent(context)) {
            	//logger.debug("执行到:GenerateServiceTicketAction........返回：gateway");
                return result("gateway");
            }
        }
        catch (final Exception e) {
        	//logger.debug("执行到:GenerateServiceTicketAction........错误[Exception]："+e.getMessage());
            if (isGatewayPresent(context)) {
            	//logger.debug("执行到:GenerateServiceTicketAction........返回：gateway");
                return result("gateway");
            }
        }
        //logger.debug("执行到:GenerateServiceTicketAction........返回："+error().getId());
        return error();
    }

    public void setCentralAuthenticationService(
        final CentralAuthenticationService centralAuthenticationService) {
        this.centralAuthenticationService = centralAuthenticationService;
    }

    protected boolean isGatewayPresent(final RequestContext context) {
        return StringUtils.hasText(context.getExternalContext()
            .getRequestParameterMap().get("gateway"));
    }
}

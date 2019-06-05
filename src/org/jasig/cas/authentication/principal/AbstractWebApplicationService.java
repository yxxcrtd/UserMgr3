package org.jasig.cas.authentication.principal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jasig.cas.util.DefaultUniqueTicketIdGenerator;
import org.jasig.cas.util.HttpClient;
import org.jasig.cas.util.SamlUtils;
import org.jasig.cas.util.UniqueTicketIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract implementation of a WebApplicationService.
 */
public abstract class AbstractWebApplicationService implements WebApplicationService {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1594603053969363623L;

	protected static final Logger LOG = LoggerFactory.getLogger(SamlService.class);
    
    private static final Map<String, Object> EMPTY_MAP = Collections.unmodifiableMap(new HashMap<String, Object>());
    
    private static final UniqueTicketIdGenerator GENERATOR = new DefaultUniqueTicketIdGenerator();
    
    /** The id of the service. */
    private final String id;
    
    /** The original url provided, used to reconstruct the redirect url. */
    private final String originalUrl;

    private final String artifactId;
    
    private Principal principal;
    
    private boolean loggedOutAlready = false;
    
    private final HttpClient httpClient;
    
    private String callbackLogOutUrl;
    
    protected AbstractWebApplicationService(final String id, final String originalUrl, final String artifactId, final HttpClient httpClient,final String callbackLogOutUrl) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.artifactId = artifactId;
        this.httpClient = httpClient;
        this.callbackLogOutUrl=callbackLogOutUrl;
    }
    
    public final String toString() {
        return this.id;
    }
    
    public final String getId() {
        return this.id;
    }
    
    public final String getArtifactId() {
        return this.artifactId;
    }

    public final Map<String, Object> getAttributes() {
        return EMPTY_MAP;
    }

    protected static String cleanupUrl(final String url) {
        if (url == null) {
            return null;
        }

        final int jsessionPosition = url.indexOf(";jsession");

        if (jsessionPosition == -1) {
            return url;
        }

        final int questionMarkPosition = url.indexOf("?");

        if (questionMarkPosition < jsessionPosition) {
            return url.substring(0, url.indexOf(";jsession"));
        }

        return url.substring(0, jsessionPosition)
            + url.substring(questionMarkPosition);
    }
    
    protected final String getOriginalUrl() {
        return this.originalUrl;
    }

    protected final HttpClient getHttpClient() {
        return this.httpClient;
    }

    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }

        if (object instanceof Service) {
            final Service service = (Service) object;

            return getId().equals(service.getId());
        }

        return false;
    }
    
    public int hashCode() {
        final int prime = 41;
        int result = 1;
        result = prime * result
            + ((this.id == null) ? 0 : this.id.hashCode());
        return result;
    }
    
    protected Principal getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(final Principal principal) {
        this.principal = principal;
    }
    
    public boolean matches(final Service service) {
        return this.id.equals(service.getId());
    }
    
    // sessionIdentifier 就是 ST的值。
    public synchronized boolean logOutOfService(final String sessionIdentifier) {
        if (this.loggedOutAlready) {
            return true;
        }
        
        LOG.debug("Sending logout request for: " + getId());

        final String logoutRequest = "<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\""
            + GENERATOR.getNewTicketId("LR")
            + "\" Version=\"2.0\" IssueInstant=\"" + SamlUtils.getCurrentDateAndTime()
            + "\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">@NOT_USED@</saml:NameID><samlp:SessionIndex>"
            + sessionIdentifier + "</samlp:SessionIndex></samlp:LogoutRequest>";
        
        this.loggedOutAlready = true;
        
        if (this.httpClient != null) {
        	// 将 logoutRequest 中的 sessionIdentifier 值 kill 掉
        	LOG.debug("提交："+logoutRequest);
        	
        	if(this.callbackLogOutUrl!=null && this.callbackLogOutUrl.length()>0){
        		LOG.debug("提交到：callbackLogOutUrl="+this.callbackLogOutUrl);	
        		return this.httpClient.sendMessageToEndPoint(this.callbackLogOutUrl, logoutRequest, true);
        	}
        	else{
        		LOG.debug("提交到：getOriginalUrl="+getOriginalUrl());
        		return this.httpClient.sendMessageToEndPoint(getOriginalUrl(), logoutRequest, true);
        	}
        }
        
        return false;
    }
    
}

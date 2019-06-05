package org.jasig.cas.util;

import org.opensaml.Configuration;
import org.opensaml.common.SAMLObject;
import org.opensaml.saml1.binding.encoding.HTTPSOAP11Encoder;
import org.opensaml.ws.soap.common.SOAPObjectBuilder;
import org.opensaml.ws.soap.soap11.Body;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.ws.soap.util.SOAPConstants;
import org.opensaml.xml.XMLObjectBuilderFactory;

/**
 * Override OpenSAML {@link HTTPSOAP11Encoder} such that SOAP-ENV XML namespace prefix is used for SOAP envelope elements.  This is needed for backward compatibility with certain CAS clients (e.g. Java CAS client).
 */
public final class CasHTTPSOAP11Encoder extends HTTPSOAP11Encoder {
    private static final String OPENSAML_11_SOAP_NS_PREFIX = "SOAP-ENV";

    @SuppressWarnings("unchecked")
	@Override
    protected Envelope buildSOAPMessage(final SAMLObject samlMessage) {
        final XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

        final SOAPObjectBuilder<Envelope> envBuilder = (SOAPObjectBuilder<Envelope>) builderFactory.getBuilder(Envelope.DEFAULT_ELEMENT_NAME);
        final Envelope envelope = envBuilder.buildObject(SOAPConstants.SOAP11_NS, Envelope.DEFAULT_ELEMENT_LOCAL_NAME, OPENSAML_11_SOAP_NS_PREFIX);

        final SOAPObjectBuilder<Body> bodyBuilder = (SOAPObjectBuilder<Body>) builderFactory.getBuilder(Body.DEFAULT_ELEMENT_NAME);
        final Body body = bodyBuilder.buildObject(SOAPConstants.SOAP11_NS, Body.DEFAULT_ELEMENT_LOCAL_NAME, OPENSAML_11_SOAP_NS_PREFIX);

        body.getUnknownXMLObjects().add(samlMessage);
        envelope.setBody(body);

        return envelope;
    }
    
}

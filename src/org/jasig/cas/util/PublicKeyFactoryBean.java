package org.jasig.cas.util;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;

import javax.validation.constraints.NotNull;

/**
 * FactoryBean for creating a public key from a file.
 */
@SuppressWarnings("rawtypes")
public class PublicKeyFactoryBean extends AbstractFactoryBean {

    @NotNull
    private Resource resource;
    
    @NotNull
    private String algorithm;

    protected final Object createInstance() throws Exception {
        final InputStream pubKey = this.resource.getInputStream();
        try {
            final byte[] bytes = new byte[pubKey.available()];
            pubKey.read(bytes);
            final X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(bytes);
            final KeyFactory factory = KeyFactory.getInstance(this.algorithm);
            return factory.generatePublic(pubSpec);
        } finally {
            pubKey.close();
        }
    }

    public Class getObjectType() {
        return PublicKey.class;
    }
    

    public void setLocation(final Resource resource) {
        this.resource = resource;
    }
    
    public void setAlgorithm(final String algorithm) {
        this.algorithm = algorithm;
    }
    
}

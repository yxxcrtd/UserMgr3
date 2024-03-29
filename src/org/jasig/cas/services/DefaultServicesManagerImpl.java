package org.jasig.cas.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

import org.jasig.cas.authentication.principal.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.github.inspektr.audit.annotation.Audit;

/**
 * Default implementation of the {@link ServicesManager} interface. If there are no services registered with the server, it considers the ServicecsManager disabled and will not prevent any service from using CAS.
 */
public final class DefaultServicesManagerImpl implements ReloadableServicesManager {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /** Instance of ServiceRegistryDao. */
    @NotNull
    private ServiceRegistryDao serviceRegistryDao;

    /** Map to store all services. */
    private ConcurrentHashMap<Long, RegisteredService> services = new ConcurrentHashMap<Long, RegisteredService>();

    /** Default service to return if none have been registered. */
    private RegisteredService disabledRegisteredService;
    
    public DefaultServicesManagerImpl(
        final ServiceRegistryDao serviceRegistryDao) {
        this(serviceRegistryDao, new ArrayList<String>());
    }
    
    /**
     * Constructs an instance of the {@link DefaultServicesManagerImpl} where the default RegisteredService
     * can include a set of default attributes to use if no services are defined in the registry.
     * 
     * @param serviceRegistryDao the Service Registry Dao.
     * @param defaultAttributes the list of default attributes to use.
     */
    public DefaultServicesManagerImpl(final ServiceRegistryDao serviceRegistryDao, final List<String> defaultAttributes) {
        this.serviceRegistryDao = serviceRegistryDao;
        this.disabledRegisteredService = constructDefaultRegisteredService(defaultAttributes);
        load();
    }

    @Transactional(readOnly = false)
    @Audit(action = "DELETE_SERVICE", actionResolverName = "DELETE_SERVICE_ACTION_RESOLVER", resourceResolverName = "DELETE_SERVICE_RESOURCE_RESOLVER")
    public synchronized RegisteredService delete(final long id) {
        final RegisteredService r = findServiceBy(id);
        if (null == r) {
            return null;
        }
        
        this.serviceRegistryDao.delete(r);
        this.services.remove(id);
        
        return r;
    }

    /**
     * Note, if the repository is empty, this implementation will return a default service to grant all access.
     * 
     * This preserves default CAS behavior.
     */
    public RegisteredService findServiceBy(final Service service) {
        final Collection<RegisteredService> c = convertToTreeSet();
        
        if (c.isEmpty()) {
            return this.disabledRegisteredService;
        }

        for (final RegisteredService r : c) {
            if (r.matches(service)) {
                return r;
            }
        }

        return null;
    }

    public RegisteredService findServiceBy(final long id) {
        final RegisteredService r = this.services.get(id);
        
        try {
            return r == null ? null : (RegisteredService) r.clone();
        } catch (final CloneNotSupportedException e) {
            return r;
        }
    }
    
    protected TreeSet<RegisteredService> convertToTreeSet() {
        return new TreeSet<RegisteredService>(this.services.values());
    }

    public Collection<RegisteredService> getAllServices() {
        return Collections.unmodifiableCollection(convertToTreeSet());
    }

    public boolean matchesExistingService(final Service service) {
        return findServiceBy(service) != null;
    }

    @Transactional(readOnly = false)
    @Audit(action = "SAVE_SERVICE", actionResolverName = "SAVE_SERVICE_ACTION_RESOLVER", resourceResolverName = "SAVE_SERVICE_RESOURCE_RESOLVER")
    public synchronized RegisteredService save(final RegisteredService registeredService) {
        final RegisteredService r = this.serviceRegistryDao.save(registeredService);
        this.services.put(r.getId(), r);
        return r;
    }
    
    public void reload() {
        log.info(new Date() + " - Reloading registered services.");
        load();
    }
    
    private void load() {
        final ConcurrentHashMap<Long, RegisteredService> localServices = new ConcurrentHashMap<Long, RegisteredService>();
                
        for (final RegisteredService r : this.serviceRegistryDao.load()) {
            log.debug("Adding registered service " + r.getServiceId());
            localServices.put(r.getId(), r);
        }
        
        this.services = localServices;
        log.info(String.format("Loaded %s services.", this.services.size()));
    }
    
    private RegisteredService constructDefaultRegisteredService(final List<String> attributes) {
        final RegisteredServiceImpl r = new RegisteredServiceImpl();
        r.setAllowedToProxy(true);
        r.setAnonymousAccess(false);
        r.setEnabled(true);
        r.setSsoEnabled(true);
        r.setAllowedAttributes(attributes);
        
        if (attributes == null || attributes.isEmpty()) {
            r.setIgnoreAttributes(true);
        }
        return r;
    }
    
}

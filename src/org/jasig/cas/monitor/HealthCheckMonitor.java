package org.jasig.cas.monitor;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.validation.constraints.NotNull;

/**
 * Simple health check monitor that reports the overall health as the greatest reported {@link StatusCode} of an arbitrary number of individual checks.
 */
@SuppressWarnings("rawtypes")
public class HealthCheckMonitor implements Monitor<HealthStatus> {
	
    /** Individual monitors that comprise health check. */
	@NotNull
    private Collection<Monitor> monitors = Collections.emptySet();

    /**
     * Sets the monitors that comprise the health check.
     *
     * @param monitors Collection of monitors responsible for observing various aspects of CAS.
     */
    public void setMonitors(final Collection<Monitor> monitors) {
        this.monitors = monitors;
    }

    /** {@inheritDoc} */
    public String getName() {
        return HealthCheckMonitor.class.getSimpleName();
    }

    /** {@inheritDoc} */
    public HealthStatus observe() {
        final Map<String, Status> results = new LinkedHashMap<String, Status>(this.monitors.size());
        StatusCode code = StatusCode.UNKNOWN;
        Status result;
        for (final Monitor monitor : this.monitors) {
            result = monitor.observe();
            if (result.getCode().value() > code.value()) {
                code = result.getCode();
            }
            results.put(monitor.getName(), result);
        }

        return new HealthStatus(code, results);
    }
    
}

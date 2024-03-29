package org.jasig.cas.web.support;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

/**
 * Implementation of a HandlerInterceptorAdapter that keeps track of a mapping
 * of IP Addresses to number of failures to authenticate.
 * <p>
 * Note, this class relies on an external method for decrementing the counts (i.e. a Quartz Job) and runs independent of the
 * threshold of the parent.
 */
public abstract class AbstractInMemoryThrottledSubmissionHandlerInterceptorAdapter extends AbstractThrottledSubmissionHandlerInterceptorAdapter {

    private final ConcurrentMap<String, Date> ipMap = new ConcurrentHashMap<String, Date>();

    @Override
    protected final boolean exceedsThreshold(final HttpServletRequest request) {
        final Date last = this.ipMap.get(constructKey(request));
        if (last == null) {
            return false;
        }
        return submissionRate(new Date(), last) > getThresholdRate();
    }

    @Override
    protected final void recordSubmissionFailure(final HttpServletRequest request) {
        this.ipMap.put(constructKey(request), new Date());
    }

    protected abstract String constructKey(HttpServletRequest request);

    /**
     * This class relies on an external configuration to clean it up. It ignores the threshold data in the parent class.
     */
    public final void decrementCounts() {
        final Set<String> keys = this.ipMap.keySet();
        log.debug("Decrementing counts for throttler.  Starting key count: " + keys.size());

        final Date now = new Date();
        String key;
        for (final Iterator<String> iter = keys.iterator(); iter.hasNext();) { 
            key = iter.next();
            if (submissionRate(now, this.ipMap.get(key)) < getThresholdRate()) {
                log.trace("Removing entry for key {}", key);
                iter.remove();
            }
        }
        log.debug("Done decrementing count for throttler.");
    }

    /**
     * Computes the instantaneous rate in between two given dates corresponding to two submissions.
     *
     * @param a First date.
     * @param b Second date.
     *
     * @return  Instantaneous submission rate in submissions/sec, e.g. <code>a - b</code>.
     */
    private double submissionRate(final Date a, final Date b) {
        return 1000.0 / (a.getTime() - b.getTime());
    }
    
}

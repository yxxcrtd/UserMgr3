package org.jasig.cas.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.registry.TicketRegistry;
import org.perf4j.log4j.GraphingStatisticsAppender;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 统计控制器
 */
public final class StatisticsController extends AbstractController {

    /**
     * 一天中的毫秒数
     */
    private static final int NUMBER_OF_MILLISECONDS_IN_A_DAY = 86400000;

    /**
     * 一小时中的毫秒数
     */
    private static final int NUMBER_OF_MILLISECONDS_IN_AN_HOUR = 3600000;

    /**
     * 一分钟中的毫秒数
     */
    private static final int NUMBER_OF_MILLISECONDS_IN_A_MINUTE = 60000;

    /**
     * 一秒中的毫秒数
     */
    private static final int NUMBER_OF_MILLISECONDS_IN_A_SECOND = 1000;

    private final TicketRegistry ticketRegistry;

    /**
     * 从开始到现在的运行时间
     */
    private final Date upTimeStartDate = new Date();

    private String casTicketSuffix;

    public StatisticsController(final TicketRegistry ticketRegistry) {
        this.ticketRegistry = ticketRegistry;
    }

    public void setCasTicketSuffix(final String casTicketSuffix) {
        this.casTicketSuffix = casTicketSuffix;
    }

    @Override
    protected ModelAndView handleRequestInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws Exception {
        final ModelAndView modelAndView = new ModelAndView("viewStatisticsView");
        modelAndView.addObject("startTime", this.upTimeStartDate);
        final double difference = System.currentTimeMillis() - this.upTimeStartDate.getTime();

        modelAndView.addObject("upTime", calculateUptime(difference, new LinkedList<Integer>(Arrays.asList(NUMBER_OF_MILLISECONDS_IN_A_DAY, NUMBER_OF_MILLISECONDS_IN_AN_HOUR, NUMBER_OF_MILLISECONDS_IN_A_MINUTE, NUMBER_OF_MILLISECONDS_IN_A_SECOND, 1)), new LinkedList<String>(Arrays.asList("day","hour","minute","second","millisecond"))));
        modelAndView.addObject("totalMemory", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        modelAndView.addObject("maxMemory", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        modelAndView.addObject("freeMemory", Runtime.getRuntime().freeMemory() / 1024 / 1024);
        modelAndView.addObject("availableProcessors", Runtime.getRuntime().availableProcessors());
        modelAndView.addObject("serverHostName", httpServletRequest.getServerName());
        modelAndView.addObject("serverIpAddress", httpServletRequest.getLocalAddr());
        modelAndView.addObject("casTicketSuffix", this.casTicketSuffix);

        int unexpiredTgts = 0;
        int unexpiredSts = 0;
        int expiredTgts = 0;
        int expiredSts = 0;

        try {
            final Collection<Ticket> tickets = this.ticketRegistry.getTickets();

            for (final Ticket ticket : tickets) {
                if (ticket instanceof ServiceTicket) {
                    if (ticket.isExpired()) {
                        expiredSts++;
                    } else {
                        unexpiredSts++;
                    }
                } else {
                    if (ticket.isExpired()) {
                        expiredTgts++;
                    } else {
                        unexpiredTgts++;
                    }
                }
            }
        } catch (final UnsupportedOperationException e) {
            // this means the ticket registry doesn't support this information.
        }

        final Collection<GraphingStatisticsAppender> appenders = GraphingStatisticsAppender.getAllGraphingStatisticsAppenders();

        modelAndView.addObject("unexpiredTgts", unexpiredTgts);
        modelAndView.addObject("unexpiredSts", unexpiredSts);
        modelAndView.addObject("expiredTgts", expiredTgts);
        modelAndView.addObject("expiredSts", expiredSts);
        modelAndView.addObject("pageTitle", modelAndView.getViewName());
        modelAndView.addObject("graphingStatisticAppenders", appenders);

        return modelAndView;
    }

    protected String calculateUptime(final double difference, final Queue<Integer> calculations, final Queue<String> labels) {
        if (calculations.isEmpty()) {
            return "";
        }

        final int value = calculations.remove();
        final double time = Math.floor(difference / value);
        final double newDifference = difference - (time * value);
        final String currentLabel = labels.remove();
        final String label = time == 0 || time > 1 ? currentLabel + "s" : currentLabel;

        return Integer.toString(new Double(time).intValue()) + " "+ label + " " + calculateUptime(newDifference, calculations, labels);
    }

}

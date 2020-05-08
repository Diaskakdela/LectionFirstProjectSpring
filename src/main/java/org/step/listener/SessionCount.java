package org.step.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionCount implements HttpSessionListener {

    private static final int ZERO = 0;
    private AtomicInteger totalTodaySessions = new AtomicInteger(ZERO);
    private AtomicInteger currentSessions = new AtomicInteger(ZERO);
    private AtomicInteger maxSessions = new AtomicInteger(ZERO);
    private ServletContext context = null;
    private StringBuffer dateToday = new StringBuffer();
    private StringBuffer getTodayValidDate = new StringBuffer(LocalDate.now().toString());

    @Override
    public void sessionCreated(HttpSessionEvent event) {

        if (!dateToday.toString().equalsIgnoreCase(getTodayValidDate.toString())) {
            totalTodaySessions.set(ZERO);
            dateToday.replace(ZERO, dateToday.length(), getTodayValidDate.toString());
        }

        totalTodaySessions.incrementAndGet();
        currentSessions.incrementAndGet();

        if (totalTodaySessions.get() > maxSessions.get()) {
            maxSessions = totalTodaySessions;
        }
        if (context == null) {
            storeInServletContext(event);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        currentSessions.decrementAndGet();
    }

    public int getTotalTodaySessions() {
        return totalTodaySessions.get();
    }

    public int getCurrentSessions() {
        return currentSessions.get();
    }

    public int getMaxSessions() {
        return maxSessions.get();
    }

    private void storeInServletContext(HttpSessionEvent event) {

        String sessionCounter = "sessionCounter";

        HttpSession session = event.getSession();
        context = session.getServletContext();
        context.setAttribute(sessionCounter, this.maxSessions);
    }
}

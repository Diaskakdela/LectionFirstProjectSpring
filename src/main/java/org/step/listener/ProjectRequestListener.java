package org.step.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class ProjectRequestListener implements ServletRequestListener {
    public ProjectRequestListener() {
    }

    public void requestDestroyed(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        System.out.println("Request from " + request.getContextPath() + " was destroyed");
    }

    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        System.out.println("Request from " + request.getContextPath() + " was created.");
    }
}

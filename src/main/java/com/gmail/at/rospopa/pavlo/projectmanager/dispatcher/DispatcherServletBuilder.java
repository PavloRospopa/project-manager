package com.gmail.at.rospopa.pavlo.projectmanager.dispatcher;

import com.gmail.at.rospopa.pavlo.projectmanager.controller.Controller;

import javax.servlet.*;
import java.util.*;

public class DispatcherServletBuilder {
    private final ServletContext servletContext;

    private Map<String, Controller> urlControllerMap = new HashMap<>();

    public DispatcherServletBuilder(ServletContext sc) {
        this.servletContext = sc;
    }

    public DispatcherServletBuilder addMapping(String url, Controller controller) {
        urlControllerMap.put(url, controller);
        return this;
    }

    public DispatcherServletBuilder reset() {
        urlControllerMap.clear();
        return this;
    }

    public DispatcherServlet build() {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();

        for (String url : urlControllerMap.keySet()) {
            dispatcherServlet.addMapping(url, urlControllerMap.get(url));
        }

        return dispatcherServlet;
    }

    public DispatcherServlet buildAndRegister(String name, String mapping) {
        DispatcherServlet servlet = build();

        ServletRegistration.Dynamic dynamic = servletContext.addServlet(name, servlet);
        dynamic.addMapping(mapping);

        return servlet;
    }
}

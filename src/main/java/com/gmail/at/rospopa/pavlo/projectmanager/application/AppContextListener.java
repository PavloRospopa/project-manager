package com.gmail.at.rospopa.pavlo.projectmanager.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger();

    private Application application = new Application();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().setAttribute("application", application);
        application.setServletContext(servletContextEvent.getServletContext());
        application.setServiceLocator(ServiceLocator.INSTANCE);
        application.init();

        LOGGER.info("Application initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
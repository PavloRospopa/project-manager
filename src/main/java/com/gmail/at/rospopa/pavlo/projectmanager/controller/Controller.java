package com.gmail.at.rospopa.pavlo.projectmanager.controller;

import com.gmail.at.rospopa.pavlo.projectmanager.dispatcher.HttpMethod;
import com.gmail.at.rospopa.pavlo.projectmanager.dispatcher.ReqResWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Controller {
    public static final Logger LOGGER = LogManager.getLogger();

    public void execute(ReqResWrapper reqResWrapper) {
        HttpMethod method = reqResWrapper.getMethod();

        switch (method) {
            case GET:
                get(reqResWrapper);
                break;
            case POST:
                post(reqResWrapper);
                break;
            case PUT:
                put(reqResWrapper);
                break;
            case DELETE:
                delete(reqResWrapper);
                break;
            default:
                LOGGER.warn("Given http method is not supported in system. Method: " + method);
        }

        any(reqResWrapper);
    }

    protected void get(ReqResWrapper reqResWrapper) {}
    protected void post(ReqResWrapper reqResWrapper) {}
    protected void put(ReqResWrapper reqResWrapper) {}
    protected void delete(ReqResWrapper reqResWrapper) {}
    protected void any(ReqResWrapper reqResWrapper) {}
}
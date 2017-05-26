package com.gmail.at.rospopa.pavlo.projectmanager.controller;

import com.gmail.at.rospopa.pavlo.projectmanager.dispatcher.ReqResWrapper;

public class RedirectController extends Controller {

    private String path;

    public RedirectController(String path) {
        this.path = path;
    }

    @Override
    protected void any(ReqResWrapper reqResWrapper) {
        reqResWrapper.renderPage(path);
    }
}
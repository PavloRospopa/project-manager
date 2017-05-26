package com.gmail.at.rospopa.pavlo.projectmanager.dispatcher;

import com.gmail.at.rospopa.pavlo.projectmanager.controller.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String PAGE_SUFFIX = ".jsp";

    private Map<String, Controller> urlControllerMap;

    public DispatcherServlet() {
        urlControllerMap = new HashMap<>();
    }

    public void addMapping(String url, Controller controller) {
        urlControllerMap.put(url, controller);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        dispatch(req, res);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        dispatch(req, res);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        dispatch(req, res);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        dispatch(req, res);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse res) {
        String pathInfo = req.getPathInfo() != null ? req.getPathInfo() : "/";

        ReqResWrapper reqResWrapper = new ReqResWrapper(req, res);

        Controller controller = matchController(pathInfo);
        if (controller == null) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        controller.execute(reqResWrapper);

        if (!tryRedirect(reqResWrapper)) {
            tryRenderPage(reqResWrapper);
        }
    }

    private boolean tryRenderPage(ReqResWrapper reqResWrapper) {
        String renderPage = reqResWrapper.getRenderPage();
        try {
            reqResWrapper.getRequest().getRequestDispatcher("/WEB-INF/pages" + withSuffix(renderPage))
                    .forward(reqResWrapper.getRequest(), reqResWrapper.getResponse());
            return true;
        } catch (ServletException | IOException e) {
            LOGGER.error("An error occurred while forwarding to page", e);
        }
        return false;
    }

    private boolean tryRedirect(ReqResWrapper reqResWrapper) {
        if (reqResWrapper.getRedirectPath() != null) {
            try {
                reqResWrapper.getResponse().sendRedirect("/WEB-INF/pages" + withSuffix(reqResWrapper.getRedirectPath()));
                return true;
            } catch (IOException e) {
                LOGGER.error("An error occurred while redirecting to another page", e);
            }
        }
        return false;
    }

    private Controller matchController(String pathInfo) {
        if (hasSuffix(pathInfo)) {
            pathInfo = pathInfo.substring(0, pathInfo.lastIndexOf(PAGE_SUFFIX));
        }

        return urlControllerMap.get(pathInfo);
    }

    private String withSuffix(String pathInfo) {
        if (!hasSuffix(pathInfo)) {
            return pathInfo + PAGE_SUFFIX;
        }
        return pathInfo;
    }

    private boolean hasSuffix(String pathInfo) {
        return pathInfo.endsWith(PAGE_SUFFIX);
    }
}

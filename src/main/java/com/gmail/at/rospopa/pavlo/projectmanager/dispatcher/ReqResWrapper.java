package com.gmail.at.rospopa.pavlo.projectmanager.dispatcher;

import com.gmail.at.rospopa.pavlo.projectmanager.util.TryOptionalUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class ReqResWrapper {
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String METHOD_PARAM = "__method";

    private HttpServletRequest request;
    private HttpServletResponse response;
    private String redirectPath;
    private String renderPage;

    public ReqResWrapper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getRedirectPath() {
        return redirectPath;
    }

    public String getRenderPage() {
        return renderPage;
    }

    public void redirect(String where) {
        redirectPath = where;
    }

    public void renderPage(String path) {
        this.renderPage = path;
    }

    public void setStatus(int status) {
        response.setStatus(status);
    }

    public Optional<String> getParameter(String param) {
        String s = request.getParameter(param);
        return Optional.ofNullable(s);
    }

    public Optional<Long> getLong(String param) {
        return getParameter(param)
                .flatMap((s) -> TryOptionalUtil.of(() -> Long.valueOf(s)));
    }

    public Optional<Integer> getInt(String param) {
        return getParameter(param)
                .flatMap((s) -> TryOptionalUtil.of(() -> Integer.valueOf(s)));
    }

    public String getString(String param) {
        return getParameter(param).orElse("");
    }

    public Optional<Boolean> getBool(String param) {
        return getParameter(param)
                .flatMap((s) -> TryOptionalUtil.of(() -> s.equals("1") || s.equals("true")));
    }

    public void setRequestAttribute(String key, Object val) {
        request.setAttribute(key, val);
    }

    public HttpMethod getMethod() {
        String httpMethod = getString(METHOD_PARAM);

        try {
            if (!httpMethod.isEmpty())
                return HttpMethod.valueOf(httpMethod.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Malformed HTTP request method");
        }

        return HttpMethod.valueOf(request.getMethod().toUpperCase());
    }
}
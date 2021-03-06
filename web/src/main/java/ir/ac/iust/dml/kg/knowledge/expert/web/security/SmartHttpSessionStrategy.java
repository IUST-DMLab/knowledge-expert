package ir.ac.iust.dml.kg.knowledge.expert.web.security;

import org.springframework.session.Session;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Farsi Knowledge Graph Project
 * Iran University of Science and Technology (Year 2017)
 * Developed by HosseiN Khademi khaledi
 *
 * Return HeaderHttpSessionStrategy for services
 */
public class SmartHttpSessionStrategy implements HttpSessionStrategy {
    private final HttpSessionStrategy cookie = new CookieHttpSessionStrategy();
    private final HttpSessionStrategy header = new HeaderHttpSessionStrategy();

    @Override
    public String getRequestedSessionId(HttpServletRequest request) {
        return getStrategy(request).getRequestedSessionId(request);
    }

    @Override
    public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
        getStrategy(request).onNewSession(session, request, response);
    }

    @Override
    public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
        getStrategy(request).onInvalidateSession(request, response);
    }

    private HttpSessionStrategy getStrategy(HttpServletRequest request) {
        return request.getServletPath().startsWith("/services") ? header : cookie;
    }
}

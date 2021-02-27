package com.chrisjaunes.communication.server;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AccountFilter", urlPatterns = {"/account/updateMessage", "/contacts/*", "/group/*", "/talk/*"})
public class AccountFilter implements Filter {
    @Override
    public void init(FilterConfig config) {
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request;
        HttpServletResponse response;
        try {
            request = (HttpServletRequest)req;
            response = (HttpServletResponse) resp;
            String account = (String)request.getSession().getAttribute(Config.STR_ACCOUNT);
            if (null == account) {
                response.sendError(401, Config.STATUS_ACCOUNT_NOT_LOGIN);
            } else {
                chain.doFilter(req, resp);
            }
        } catch (ClassCastException var6) {
            throw new ServletException("non-HTTP request or response");
        }
    }
    @Override
    public void destroy() {
    }
}

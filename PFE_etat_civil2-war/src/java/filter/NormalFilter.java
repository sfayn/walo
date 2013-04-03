/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import bean.User;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author YOU$$EF
 */
public class NormalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        User auth = (User) req.getSession().getAttribute("auth");
        if (auth != null) {
            // User is logged in, so just continue request.
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect(req.getContextPath() + "/faces/index.xhtml");
        } else {
            // User is not logged in, so redirect to index.
            chain.doFilter(request, response);
        }
    }

    // You need to override init() and destroy() as well, but they can be kept empty.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
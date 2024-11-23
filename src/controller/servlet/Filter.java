package controller.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String requestURI = request.getRequestURI();

        if (requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".jpg") || requestURI.endsWith(".png")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (session == null || session.getAttribute("userId") == null) {
            request.getRequestDispatcher("/login").forward(request, response);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

package edu.bbte.idde.leim2041.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter({"/index", "/"})
public class RequestFilter extends HttpFilter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        LOG.info("{}\n{}\n{}",
                req.getMethod(),
                req.getRequestURI(),
                res.getStatus());
        HttpSession sess = req.getSession(false);
        if (sess != null && sess.getAttribute("username") != null || req.getRequestURI().contains("/static")) {
            LOG.info("Filter passed, user is logged in and session is active");
            chain.doFilter(req, res);
        } else {
            LOG.info("User is not logged in, redirecting to login page");
            chain.doFilter(req, res);
            res.sendRedirect("login");
        }
    }
}
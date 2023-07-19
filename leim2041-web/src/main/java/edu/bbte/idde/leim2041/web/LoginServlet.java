package edu.bbte.idde.leim2041.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static edu.bbte.idde.leim2041.web.LoginDataConstants.STRONGPASSWORD;
import static edu.bbte.idde.leim2041.web.LoginDataConstants.USERNAME;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Redirected to login");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        LOG.info("username:" + userName + "\n" + "password: " + password);
        LOG.info("username:" + USERNAME + "\n" + "password: " + STRONGPASSWORD);

        if (USERNAME.equals(userName) && STRONGPASSWORD.equals(password)) {
            req.getSession().setAttribute("username", userName);
            req.getSession().removeAttribute("error");
            resp.sendRedirect("index");
            LOG.info("login successful, session started");
        } else {
            req.getSession().setAttribute("error", true);
            resp.sendRedirect("login");
            LOG.info("Invalid login credentials");
        }
    }
}
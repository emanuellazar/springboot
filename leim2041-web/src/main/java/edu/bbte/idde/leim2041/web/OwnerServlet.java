package edu.bbte.idde.leim2041.web;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.leim2041.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.model.Owner;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/owner")
public class OwnerServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(RealEstateServlet.class);
    private final OwnerDao ownerDao = AbstractDaoFactory.getDaoFactory().getOwnerDao();
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        objectMapper = ObjectMapperFactory.getObjectMapper();
        LOG.info("Server initialising");
    }

    @Override
    public void destroy() {
        LOG.info("Destroying server");
        super.destroy();
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameter("id") == null) {
            Collection<Owner> owner = ownerDao.findAll();
            resp.setHeader("Content-type", "application/json");
            objectMapper.writeValue(resp.getWriter(), owner);
            LOG.info("Server initialising");
        } else {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                Owner owner = ownerDao.findById(Long.parseLong(String.valueOf(id)));
                if (owner == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    LOG.info("No owner with given id found");
                    return;
                }
                objectMapper.writeValue(resp.getWriter(), owner);
                LOG.info("Ad with given id returned");
            } catch (NumberFormatException e) {
                LOG.info("Incorrect request");
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Owner owner = objectMapper.readValue(req.getInputStream(), Owner.class);
            ownerDao.create(owner);
            objectMapper.writeValue(resp.getOutputStream(), owner);
            resp.setHeader("Content-Type", "application/json");
            LOG.info("Owner created succesfully");
        } catch (DatabindException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Error while creating owner:");
            LOG.info("Error while creating owner:");
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputId = req.getParameter("id");
        try {
            Long id = Long.parseLong(inputId);
            Owner owner = ownerDao.findById(id);

            if (owner == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println("Owner not found");
            } else {
                ownerDao.delete(id);
                objectMapper.writeValue(resp.getOutputStream(), owner);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Invalid id");
        }
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        try {
            Long id = Long.parseLong(idParam);
            Owner owner = ownerDao.findById(id);

            if (owner == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println("Owner not found");
            } else {
                try {
                    owner = objectMapper.readValue(req.getInputStream(), Owner.class);
                    Owner oldOwner = ownerDao.update(id, owner);
                    resp.setHeader("Content-Type", "application/json");
                    objectMapper.writeValue(resp.getOutputStream(), oldOwner);
                } catch (DatabindException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("Invalid owner:" + e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Invalid id");
        }
    }
}

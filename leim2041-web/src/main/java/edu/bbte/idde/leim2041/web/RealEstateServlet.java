package edu.bbte.idde.leim2041.web;

import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.leim2041.backend.dao.AbstractDaoFactory;
import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
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

@WebServlet("/ads")
public class RealEstateServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(RealEstateServlet.class);
    private final RealEstateDao realEstateDao = AbstractDaoFactory.getDaoFactory().getRealEstateDao();
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
            Collection<RealEstate> ads = realEstateDao.findAll();
            resp.setHeader("Content-type", "application/json");
            objectMapper.writeValue(resp.getWriter(), ads);
            LOG.info("Server initialising");
        } else {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                RealEstate oneAd = realEstateDao.findById(Long.parseLong(String.valueOf(id)));
                if (oneAd == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    LOG.info("No ad with given id found");
                    return;
                }
                objectMapper.writeValue(resp.getWriter(), oneAd);
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
            RealEstate advertisment = objectMapper.readValue(req.getInputStream(), RealEstate.class);
            realEstateDao.create(advertisment);
            objectMapper.writeValue(resp.getOutputStream(), advertisment);
            resp.setHeader("Content-Type", "application/json");
            LOG.info("Ad created succesfully");
        } catch (DatabindException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Error while creating ad:");
            LOG.info("Error while creating ad:");
        }
    }

    @SneakyThrows
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputId = req.getParameter("id");
        try {
            Long id = Long.parseLong(inputId);
            RealEstate oneAd = realEstateDao.findById(id);

            if (oneAd == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println("Ad not found");
            } else {
                realEstateDao.delete(id);
                objectMapper.writeValue(resp.getOutputStream(), oneAd);
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
            RealEstate advertisement = realEstateDao.findById(id);

            if (advertisement == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println("Ad not found");
            } else {
                try {
                    advertisement = objectMapper.readValue(req.getInputStream(), RealEstate.class);
                    RealEstate oldAd = realEstateDao.update(id, advertisement);
                    resp.setHeader("Content-Type", "application/json");
                    objectMapper.writeValue(resp.getOutputStream(), oldAd);
                } catch (DatabindException e) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println("Invalid ad:" + e.getMessage());
                }
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Invalid id");
        }
    }
}

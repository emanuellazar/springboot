package edu.bbte.idde.leim2041.backend.dao;

import edu.bbte.idde.leim2041.backend.config.ConfigFactory;
import edu.bbte.idde.leim2041.backend.dao.jdbc.JdbcDaoFactory;
import edu.bbte.idde.leim2041.backend.dao.mem.MemDaoFactory;

public abstract class AbstractDaoFactory {
    private static DaoFactory daoFactory;

    public static synchronized DaoFactory getDaoFactory() {
        if (daoFactory == null) {
            String type = ConfigFactory.getConfig().getDaoType();
            if ("jdbc".equalsIgnoreCase(type)) {
                daoFactory = new JdbcDaoFactory();
            } else {
                daoFactory = new MemDaoFactory();
            }
        }
        return daoFactory;
    }
}


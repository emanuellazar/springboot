package edu.bbte.idde.leim2041.backend.dao.jdbc;

import edu.bbte.idde.leim2041.backend.dao.DaoFactory;
import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;

public class JdbcDaoFactory implements DaoFactory {
    private final JdbcRealEstateDao realEstateDao = new JdbcRealEstateDao();
    private final JdbcOwnerDao ownerDao = new JdbcOwnerDao();

    @Override
    public RealEstateDao getRealEstateDao() {
        return  realEstateDao;
    }

    @Override
    public OwnerDao getOwnerDao() {
        return ownerDao;
    }
}

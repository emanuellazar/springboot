package edu.bbte.idde.leim2041.backend.dao.mem;

import edu.bbte.idde.leim2041.backend.dao.DaoFactory;
import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;

public class MemDaoFactory implements DaoFactory {
    private RealEstateDao realEstateDao;
    private OwnerDao ownerDao;

    @Override
    public RealEstateDao getRealEstateDao() {
        if (realEstateDao == null) {
            realEstateDao = new RealEstateMemDao();
        }
        return  realEstateDao;
    }

    @Override
    public OwnerDao getOwnerDao() {
        if (ownerDao == null) {
            ownerDao = new OwnerMemDao();
        }
        return ownerDao;
    }
}

package edu.bbte.idde.leim2041.backend.dao;

public interface DaoFactory {
    RealEstateDao getRealEstateDao();

    OwnerDao getOwnerDao();
}

package edu.bbte.idde.leim2041.backend.dao;

import edu.bbte.idde.leim2041.backend.model.RealEstate;

import java.util.Collection;

public interface RealEstateDao  extends Dao<RealEstate> {
    Collection<RealEstate> findByCity(String city);
}
package edu.bbte.idde.leim2041.backend.dao;

import edu.bbte.idde.leim2041.backend.model.Owner;

import java.util.Collection;

public interface OwnerDao extends Dao<Owner> {
    Collection<Owner> findByOwnerAge(Integer ownerAge);
}

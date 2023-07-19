package edu.bbte.idde.leim2041.backend.dao.mem;

import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.model.Owner;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class OwnerMemDao extends AbstractMemDao<Owner> implements OwnerDao {

    @Override
    public Collection<Owner> findByOwnerAge(Integer ownerAge) {
        log.info("All ads with the owner who has the age " + ownerAge + " returned");
        return database.values().stream()
                .filter(advertisement -> advertisement.getAge().equals(ownerAge))
                .collect(Collectors.toList());
    }
}

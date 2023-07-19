package edu.bbte.idde.leim2041.backend.dao.mem;

import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Profile("mem")
public final class RealEstateMemDao extends AbstractMemDao<RealEstate> implements RealEstateDao {

    @Override
    public Collection<RealEstate> findByCity(String city) {
        log.info("All ads publised in the city " + city + " returned");
        return database.values().stream()
                .filter(advertisement -> advertisement.getCity().equals(city))
                .collect(Collectors.toList());
    }
}


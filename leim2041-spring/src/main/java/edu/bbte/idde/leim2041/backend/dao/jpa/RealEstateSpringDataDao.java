package edu.bbte.idde.leim2041.backend.dao.jpa;

import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface RealEstateSpringDataDao extends JpaRepository<RealEstate, Long>, RealEstateDao {
}

package edu.bbte.idde.leim2041.backend.dao.jpa;

import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.model.Owner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Profile("jpa")
public interface OwnerSpringDataDao extends OwnerDao, JpaRepository<Owner, Long> {
    @Modifying()
    @Transactional
    @Query("select o from Owner o where o.age  = :#{#ownerAge}")
    @Override
    Collection<Owner> findByOwnerAge(Integer ownerAge);
}

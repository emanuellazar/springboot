package edu.bbte.idde.leim2041.backend.dao.jdbc;

import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.model.Owner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Repository
@Profile("jdbc")
public class JdbcOwnerDao extends AbstractJdbcDao<Owner> implements OwnerDao {
    public JdbcOwnerDao() {
        super(Owner.class);
    }

    @SneakyThrows
    @Override
    public Collection<Owner> findByOwnerAge(Integer ownerAge) {
        StringBuilder sqlFindByOwnerAge = new StringBuilder();
        sqlFindByOwnerAge.append("SELECT * FROM owner WHERE age = ").append(ownerAge.toString());
        Collection<Owner> allOwners = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement(sqlFindByOwnerAge.toString());
        ResultSet set = preparedStatement.executeQuery();
        while (set.next()) {
            Owner owner = buildObjectFromDbData(set);
            allOwners.add(owner);
        }
        return allOwners;
    }
}

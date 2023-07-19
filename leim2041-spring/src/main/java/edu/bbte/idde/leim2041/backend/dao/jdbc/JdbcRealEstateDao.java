package edu.bbte.idde.leim2041.backend.dao.jdbc;

import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
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
public class JdbcRealEstateDao extends AbstractJdbcDao<RealEstate> implements RealEstateDao {
    public JdbcRealEstateDao() {
        super(RealEstate.class);
    }

    @SneakyThrows
    @Override
    public Collection<RealEstate> findByCity(String city) {
        StringBuilder sqlFindByOwnerAge = new StringBuilder();
        sqlFindByOwnerAge.append("SELECT * FROM realestate WHERE city = \'").append(city).append('\'');
        Collection<RealEstate> allRealEstate = new ArrayList<>();
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection
                .prepareStatement(sqlFindByOwnerAge.toString());
        ResultSet set = preparedStatement.executeQuery();
        while (set.next()) {
            RealEstate advertisement = buildObjectFromDbData(set);
            allRealEstate.add(advertisement);
        }
        return allRealEstate;
    }
}

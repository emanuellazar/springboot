package edu.bbte.idde.leim2041.backend.dao.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.leim2041.backend.config.Config;
import edu.bbte.idde.leim2041.backend.config.ConfigFactory;

import javax.sql.DataSource;

public class DataSourceFactory {
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            Config config = ConfigFactory.getConfig();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(config.getDriver());

            hikariConfig.setJdbcUrl("jdbc:mysql://" + config.getUrl() + "/" + config.getDatabase());
            hikariConfig.setUsername(config.getUser());
            hikariConfig.setPassword(config.getPassword());

            hikariConfig.setMaximumPoolSize(config.getPoolSize());

            dataSource = new HikariDataSource(hikariConfig);
        }
        return dataSource;
    }
}

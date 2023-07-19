package edu.bbte.idde.leim2041.backend.config;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class ConfigFactory {
    private static Config instance;

    public static synchronized Config getConfig() {
        if (instance == null) {
            final StringBuilder name = new StringBuilder("/application");
            // a PROFILE kornyezeti valtozobol kiderul, hogy memoriabol vagy adatbazisbol fogjuk az adatokat lekerni
            String daoType = System.getenv("PROFILE");
            // ha prod van a a PROFILE kornyezeti valtozoban, adatbazissal dogozunk, ha dev van vagy nincs
            // semmi, memoriaval dolgozunk
            name.append('-').append("prod".equals(daoType) ? "prod" : "dev").append(".yaml");
            InputStream inputStream = Config.class.getResourceAsStream(name.toString());
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            try {
                instance = objectMapper.readValue(inputStream, Config.class);
            } catch (IOException e) {
                log.error(e.toString());
                instance = new Config();
                instance.setDaoType("mem");
            }
        }
        log.info(instance.getDaoType());
        return instance;
    }
}

package edu.bbte.idde.leim2041.backend.config;

import lombok.Data;

@Data
public class Config {
    private String daoType;
    private String user;
    private String password;
    private String database;
    private String url;
    private Integer poolSize;
    private String driver;
}

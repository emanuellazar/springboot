package edu.bbte.idde.leim2041.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Owner extends BaseEntity {
    private String firstName;
    private String secondName;
    private Integer age;
    private Integer cnp;
}

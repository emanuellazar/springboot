package edu.bbte.idde.leim2041.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OwnerOutDto implements Serializable {
    private Long id;
    private String firstName;
    private String secondName;
    private Integer age;
    private Integer cnp;
    private Long realEstateId;
}

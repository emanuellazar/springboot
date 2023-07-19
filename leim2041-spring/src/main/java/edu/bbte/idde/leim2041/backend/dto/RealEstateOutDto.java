package edu.bbte.idde.leim2041.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RealEstateOutDto implements Serializable {
    private Long id;
    private String city;
    private String neighbourhood;
    private Integer price;
    private Integer numberOfRooms;
    private Integer yearBuilt;
    private Boolean hasElevator;
    private Long ownerId;
}

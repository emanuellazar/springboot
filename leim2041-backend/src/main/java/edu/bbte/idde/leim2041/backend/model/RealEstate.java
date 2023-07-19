package edu.bbte.idde.leim2041.backend.model;

import edu.bbte.idde.leim2041.backend.annotations.ReferenceToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RealEstate extends BaseEntity {
    private String city;
    private String neighbourhood;
    private Integer price;
    private Integer numberOfRooms;
    private Integer yearBuilt;
    private Boolean hasElevator;
    @ReferenceToOne(referenceTableName = "Owner")
    private Long ownerId;
}
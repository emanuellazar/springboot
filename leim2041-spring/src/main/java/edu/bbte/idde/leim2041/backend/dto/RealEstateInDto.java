package edu.bbte.idde.leim2041.backend.dto;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Value
public class RealEstateInDto implements Serializable {
    @NonNull
    String city;

    @NonNull
    String neighbourhood;

    @Positive
    @NotNull
    Integer price;

    @Positive
    @NotNull
    Integer numberOfRooms;

    @Positive
    @NotNull
    Integer yearBuilt;

    @NotNull
    Boolean hasElevator;

    @Positive
    Long ownerId;
}

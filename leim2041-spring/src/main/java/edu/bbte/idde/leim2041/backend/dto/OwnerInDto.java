package edu.bbte.idde.leim2041.backend.dto;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@Value
public class OwnerInDto implements Serializable {
    @NonNull
    String firstName;

    @NonNull
    String secondName;

    @NonNull
    @Positive
    Integer age;

    @Positive
    @NonNull
    Integer cnp;
}

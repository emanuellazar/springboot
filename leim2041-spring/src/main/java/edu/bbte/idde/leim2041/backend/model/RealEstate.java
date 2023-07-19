package edu.bbte.idde.leim2041.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Repository
@Entity
@Table(name = "realestate")
public class RealEstate extends BaseEntity {
    private String city;
    private String neighbourhood;
    private Integer price;
    private Integer numberOfRooms;
    private Integer yearBuilt;
    private Boolean hasElevator;
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Owner owner;
}
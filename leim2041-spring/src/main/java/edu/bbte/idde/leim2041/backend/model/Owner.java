package edu.bbte.idde.leim2041.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
@Entity
@Table(name = "owner")
public class Owner extends BaseEntity {
    private String firstName;
    private String secondName;
    private Integer age;
    private Integer cnp;
    @JsonIgnore
    @OneToMany(
            mappedBy = "owner",
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private Collection<RealEstate> realEstates;
}

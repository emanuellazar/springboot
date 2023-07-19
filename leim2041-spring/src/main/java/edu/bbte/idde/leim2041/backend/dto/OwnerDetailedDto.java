package edu.bbte.idde.leim2041.backend.dto;

import edu.bbte.idde.leim2041.backend.model.RealEstate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OwnerDetailedDto extends OwnerOutDto {
    Collection<RealEstate> realEstates;
}

package edu.bbte.idde.leim2041.backend.dto;

import edu.bbte.idde.leim2041.backend.model.Owner;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class RealEstateDetailedDto extends RealEstateOutDto {
    Owner owner;
}

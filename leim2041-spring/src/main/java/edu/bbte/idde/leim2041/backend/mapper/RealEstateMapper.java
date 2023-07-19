package edu.bbte.idde.leim2041.backend.mapper;

import edu.bbte.idde.leim2041.backend.dto.RealEstateDetailedDto;
import edu.bbte.idde.leim2041.backend.dto.RealEstateInDto;
import edu.bbte.idde.leim2041.backend.dto.RealEstateOutDto;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class RealEstateMapper {
    public abstract RealEstate realEstateFromDto(RealEstateInDto realEstateInDto);

    public abstract RealEstateDetailedDto dtoFromRealEstate(RealEstate realEstate);

    @IterableMapping(elementTargetType = RealEstateOutDto.class)
    public abstract Collection<RealEstateDetailedDto> dtosFromRealEstate(Collection<RealEstate> realEstates);
}

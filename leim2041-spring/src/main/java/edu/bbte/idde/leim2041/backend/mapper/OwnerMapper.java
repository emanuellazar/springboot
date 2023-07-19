package edu.bbte.idde.leim2041.backend.mapper;

import edu.bbte.idde.leim2041.backend.dto.OwnerDetailedDto;
import edu.bbte.idde.leim2041.backend.dto.OwnerInDto;
import edu.bbte.idde.leim2041.backend.model.Owner;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class OwnerMapper {
    public abstract Owner ownerFromDto(OwnerInDto ownerInDto);

    public abstract OwnerDetailedDto dtoFromOwner(Owner owner);

    @IterableMapping(elementTargetType = OwnerDetailedDto.class)
    public abstract Collection<OwnerDetailedDto> dtosFromOwner(Collection<Owner> owners);
}

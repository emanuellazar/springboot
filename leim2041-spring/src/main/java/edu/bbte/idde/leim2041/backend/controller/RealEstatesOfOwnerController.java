package edu.bbte.idde.leim2041.backend.controller;

import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.dto.RealEstateDetailedDto;
import edu.bbte.idde.leim2041.backend.dto.RealEstateInDto;
import edu.bbte.idde.leim2041.backend.mapper.RealEstateMapper;
import edu.bbte.idde.leim2041.backend.model.Owner;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/owner")
public class RealEstatesOfOwnerController {

    @Autowired
    OwnerDao ownerDao;

    @Autowired
    RealEstateMapper realEstateMapper;

    @GetMapping("/{ownerId}/ads")
    public Collection<RealEstateDetailedDto> findRealEstatesOfOwner(@PathVariable("ownerId") Long ownerId) {
        Owner owner = ownerDao.findById(ownerId)
                .orElseThrow(NotFoundException::new);
        return realEstateMapper.dtosFromRealEstate(owner.getRealEstates());
    }

    @SneakyThrows
    @PostMapping("/{ownerId}/ads")
    public String addRealEstateToOwners(
            @RequestBody @Valid RealEstateInDto realEstateInDto,
            @PathVariable("ownerId") Long ownerId) {
        Owner owner = ownerDao.findById(ownerId)
                .orElseThrow(NotFoundException::new);
        RealEstate realEstate = realEstateMapper.realEstateFromDto(realEstateInDto);
        realEstate.setOwner(owner);
        owner.getRealEstates().add(realEstate);
        ownerDao.saveAndFlush(owner);
        return  "RealEstate succesfully added to owner";
    }

    @SneakyThrows
    @DeleteMapping("/{ownerId}/ads/{realEstateId}")
    public void deleteRealEstateFromOwner(@PathVariable("ownerId") Long ownerId,
                                          @PathVariable("realEstateId") Long realEstateId) {
        Owner owner = ownerDao.findById(ownerId)
                .orElseThrow(NotFoundException::new);
        if (!owner.getRealEstates().removeIf(realEstate -> realEstate.getId().equals(realEstateId))) {
            throw new NotFoundException();
        }
        ownerDao.saveAndFlush(owner);
    }
}

package edu.bbte.idde.leim2041.backend.controller;

import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.dao.RealEstateDao;
import edu.bbte.idde.leim2041.backend.dto.RealEstateDetailedDto;
import edu.bbte.idde.leim2041.backend.dto.RealEstateInDto;
import edu.bbte.idde.leim2041.backend.dto.RealEstateOutDto;
import edu.bbte.idde.leim2041.backend.mapper.RealEstateMapper;
import edu.bbte.idde.leim2041.backend.model.Owner;
import edu.bbte.idde.leim2041.backend.model.RealEstate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/ads")
public class RealEstateController {

    @Autowired
    private RealEstateDao realEstateDao;

    @Autowired
    private OwnerDao ownerDao;

    @Autowired
    private RealEstateMapper realEstateMapper;

    @GetMapping
    public Collection<RealEstateDetailedDto> findAll() {
        return realEstateMapper.dtosFromRealEstate(realEstateDao.findAll());
    }

    @GetMapping("/{id}")
    public RealEstateOutDto findById(@PathVariable("id") Long id) {
        Optional<RealEstate> oneAd = realEstateDao.findById(id);
        if (!oneAd.isPresent()) {
            throw new NotFoundException();
        }
        return realEstateMapper.dtoFromRealEstate(oneAd.get());
    }

    @GetMapping("/city/{cityName}")
    public Collection<RealEstateDetailedDto> findByCity(@PathVariable("cityName") String city) {
        Collection<RealEstateDetailedDto> realEstateOutDtos = realEstateMapper
                .dtosFromRealEstate(realEstateDao.findByCity(city));
        if (realEstateOutDtos.isEmpty()) {
            throw new NotFoundException();
        }
        return realEstateOutDtos;
    }

    @ResponseBody
    @PostMapping
    public String saveAndFlush(@RequestBody @Valid RealEstateInDto realEstateInDto) {
        Optional<Owner> owner = ownerDao.findById(realEstateInDto.getOwnerId());
        RealEstate realEstate = realEstateMapper.realEstateFromDto(realEstateInDto);
        realEstate.setOwner(owner.get());
        realEstateDao.saveAndFlush(realEstate);
        return "Hello from GeneralSpringController, your id is " + realEstate.getId();
    }

    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable("id") Long id) {
        realEstateDao.deleteById(id);
    }
}

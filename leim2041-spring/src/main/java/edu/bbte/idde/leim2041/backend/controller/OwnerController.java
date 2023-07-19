package edu.bbte.idde.leim2041.backend.controller;

import edu.bbte.idde.leim2041.backend.dao.OwnerDao;
import edu.bbte.idde.leim2041.backend.dto.OwnerDetailedDto;
import edu.bbte.idde.leim2041.backend.dto.OwnerInDto;
import edu.bbte.idde.leim2041.backend.dto.OwnerOutDto;
import edu.bbte.idde.leim2041.backend.mapper.OwnerMapper;
import edu.bbte.idde.leim2041.backend.model.Owner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerDao ownerDao;

    @Autowired
    private OwnerMapper ownerMapper;

    @GetMapping
    public Collection<OwnerDetailedDto> findAll() {
        return ownerMapper.dtosFromOwner(ownerDao.findAll());
    }

    @GetMapping("/{id}")
    public OwnerOutDto findById(@PathVariable("id") Long id) {
        Optional<Owner> owner = ownerDao.findById(id);
        if (!owner.isPresent()) {
            throw new NotFoundException();
        }
        return ownerMapper.dtoFromOwner(owner.get());
    }

    @GetMapping("/age/{ownerAge}")
    public Collection<OwnerDetailedDto> findByOwnerAge(@PathVariable("ownerAge") Integer ownerAge) {
        Collection<OwnerDetailedDto> ownerOutDtos = ownerMapper
                .dtosFromOwner(ownerDao.findByOwnerAge(ownerAge));
        if (ownerOutDtos.isEmpty()) {
            throw new NotFoundException();
        }
        return ownerOutDtos;
    }

    @ResponseBody
    @PostMapping
    public String saveAndFlush(@RequestBody @Valid OwnerInDto ownerInDto) {
        Owner owner = ownerDao.saveAndFlush(ownerMapper.ownerFromDto(ownerInDto));
        return owner.getId().toString();
    }

    @DeleteMapping({"/{id}"})
    public void delete(@PathVariable("id") Long id) {
        ownerDao.deleteById(id);
    }
}

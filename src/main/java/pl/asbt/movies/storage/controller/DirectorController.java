package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.DirectorDto;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.DirectorMapper;
import pl.asbt.movies.storage.servise.DirectorService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/directors")
public class DirectorController {
/*
    @Autowired
    DirectorService directorService;

    @Autowired
    DirectorMapper directorMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createDirector(@RequestBody DirectorDto directorDto) {
        directorService.saveDirector(directorDto);
    }

    @GetMapping(value = "/{directorId}")
    public DirectorDto getDirector(@PathVariable Long directorId) throws SearchingException {
        return directorMapper.mapToDirectorDto(directorService.getDirector(directorId).orElseThrow(SearchingException::new));
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<DirectorDto> getDirectorByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return directorMapper.mapToDirectorsDto(directorService.getAllDirectorsByNameAndSurname(name, surname));
    }

    @GetMapping
    public List<DirectorDto> getDirectors() {
        return directorMapper.mapToDirectorsDto(directorService.getAllDirectors());
    }

    @DeleteMapping(value = "/{directorId}")
    public void deleteDirector(@PathVariable Long directorId) {
        directorService.deleteDirector(directorId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteDirectorByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        directorService.deleteDirectorByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public DirectorDto updateDirector(@RequestBody DirectorDto directorDto) {
        return directorMapper.mapToDirectorDto(directorService.updateDirector(directorDto));
    }*/
}

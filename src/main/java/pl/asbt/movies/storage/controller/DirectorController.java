package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.DirectorDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.DirectorFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/directors")
public class DirectorController {

    private final DirectorFacade directorFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createDirector(@Validated @RequestBody DirectorDto directorDto) {
        directorFacade.createDirector(directorDto);
    }

    @GetMapping(value = "/{directorId}")
    public DirectorDto getDirector(@Validated @PathVariable Long directorId) throws StorageException {
        return directorFacade.fetchDirector(directorId);
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<DirectorDto> getDirectorByNameAndSurname(@Validated @PathVariable String name,
                                                         @Validated @PathVariable String surname) {
        return directorFacade.fetchDirectorByNameAndSurname(name, surname);
    }

    @GetMapping
    public List<DirectorDto> getDirectors() {
        return directorFacade.fetchDirectors();
    }

    @DeleteMapping(value = "/{directorId}")
    public void deleteDirector(@Validated @PathVariable Long directorId) {
        directorFacade.deleteDirector(directorId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteDirectorByNameAndSurname(@Validated @PathVariable String name,
                                               @Validated @PathVariable String surname) {
        directorFacade.deleteDirectorByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public DirectorDto updateDirector(@Validated @RequestBody DirectorDto directorDto) {
        return directorFacade.updateDirector(directorDto);
    }
}

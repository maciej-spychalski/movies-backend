package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.DirectorDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.DirectorMapper;
import pl.asbt.movies.storage.servise.DirectorService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/directors")
public class DirectorController {

    private final DirectorService directorService;
    private final DirectorMapper directorMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createDirector(@Validated @RequestBody DirectorDto directorDto) {
        directorService.saveDirector(directorMapper.mapToDirector(directorDto));
    }

    @GetMapping(value = "/{directorId}")
    public DirectorDto getDirector(@Validated @PathVariable Long directorId) throws StorageException {
        return directorMapper.mapToDirectorDto(directorService.getDirector(directorId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no director with given id.")
                        .build()
        ));
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<DirectorDto> getDirectorByNameAndSurname(@Validated @PathVariable String name,
                                                         @Validated @PathVariable String surname) {
        return directorMapper.mapToDirectorsDto(directorService.getAllDirectorsByNameAndSurname(name, surname));
    }

    @GetMapping
    public List<DirectorDto> getDirectors() {
        return directorMapper.mapToDirectorsDto(directorService.getAllDirectors());
    }

    @DeleteMapping(value = "/{directorId}")
    public void deleteDirector(@Validated @PathVariable Long directorId) {
        directorService.deleteDirector(directorId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteDirectorByNameAndSurname(@Validated @PathVariable String name,
                                               @Validated @PathVariable String surname) {
        directorService.deleteDirectorByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public DirectorDto updateDirector(@Validated @RequestBody DirectorDto directorDto) {
        return directorMapper.mapToDirectorDto(directorService.updateDirector(directorDto));
    }
}

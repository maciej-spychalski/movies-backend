package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.ActorDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.ActorMapper;
import pl.asbt.movies.storage.servise.ActorService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/actors")
public class ActorController {

    private final ActorService actorService;
    private final ActorMapper actorMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createActor(@Validated @RequestBody ActorDto actorDto) {
        actorService.saveActor(actorMapper.mapToActor(actorDto));
    }

    @GetMapping(value = "/{actorId}")
    public ActorDto getActor(@Validated @PathVariable Long actorId) throws StorageException {
        return actorMapper.mapToActorDto(actorService.getActor(actorId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no actor with given id.")
                        .build()
        ));
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<ActorDto> getActorByNameAndSurname(@Validated@PathVariable String name,
                                                   @Validated @PathVariable String surname) {
        return actorMapper.mapToActorsDto(actorService.getActorsByNameAndSurname(name, surname));
    }


    @GetMapping()
    public List<ActorDto> getActors() {
        return actorMapper.mapToActorsDto(actorService.getAllActors());
    }

    @DeleteMapping(value = "/{actorId}")
    public void deleteActor(@PathVariable Long actorId) {
        actorService.deleteActor(actorId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteActorByNameAndSurname(@Validated @PathVariable String name,
                                            @Validated @PathVariable String surname) {
        actorService.deleteActorsByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ActorDto updateActor(@Validated @RequestBody ActorDto actorDto) {
        return actorMapper.mapToActorDto(actorService.updateActor(actorDto));
    }

}
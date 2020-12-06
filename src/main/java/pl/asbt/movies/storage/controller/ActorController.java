package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.ActorDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.ActorFacade;


import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/actors")
public class ActorController {

    private final ActorFacade actorFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createActor(@Validated @RequestBody ActorDto actorDto) {
        actorFacade.createActor(actorDto);
    }

    @GetMapping(value = "/{actorId}")
    public ActorDto getActor(@Validated @PathVariable Long actorId) throws StorageException {
        return actorFacade.fetchActor(actorId);
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<ActorDto> getActorByNameAndSurname(@Validated@PathVariable String name,
                                                   @Validated @PathVariable String surname) {
        return actorFacade.fetchActorByNameAndSurname(name, surname);
    }

    @GetMapping()
    public List<ActorDto> getActors() {
        return actorFacade.fetchActors();
    }

    @DeleteMapping(value = "/{actorId}")
    public void deleteActor(@Validated @PathVariable Long actorId) {
        actorFacade.deleteActor(actorId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteActorByNameAndSurname(@Validated @PathVariable String name,
                                            @Validated @PathVariable String surname) {
        actorFacade.deleteActorByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ActorDto updateActor(@Validated @RequestBody ActorDto actorDto) {
        return actorFacade.updateActor(actorDto);
    }

}
package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.ActorDto;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.ActorMapper;
import pl.asbt.movies.storage.servise.ActorService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
//@RequestMapping("/v1/storage/actor")
@RequestMapping("/v1/storage/actors")
public class ActorController {
/*

    @Autowired
    ActorService actorService;

    @Autowired
    ActorMapper actorMapper;

    //    @PostMapping(value = "createActor", consumes = APPLICATION_JSON_VALUE)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createActor(@RequestBody ActorDto actorDto) {
        actorService.saveActor(actorDto);
    }

//    @GetMapping(value = "getActor")
    @GetMapping(value = "/{actorId}")
    public ActorDto getActor(@PathVariable Long actorId) throws SearchingException {
        return actorMapper.mapToActorDto(actorService.getActor(actorId).orElseThrow(SearchingException::new));
    }

//    @GetMapping(value = "getActorByNameAndSurname")
    @GetMapping(value = "/{name}/{surname}")
    public List<ActorDto> getActorByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return actorMapper.mapToActorsDto(actorService.getActorsByNameAndSurname(name, surname));
    }

    //    @GetMapping(value = "getActors")
    @GetMapping()
    public List<ActorDto> getActors() {
        return actorMapper.mapToActorsDto(actorService.getAllActors());
    }

//    @DeleteMapping(value = "deleteActor")
    @DeleteMapping(value = "/{actorId}")
    public void deleteActor(@PathVariable Long actorId) {
        actorService.deleteActor(actorId);
    }

    //    @DeleteMapping(value = "deleteActorByNameAndSurname")
    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteActorByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        actorService.deleteActorsByNameAndSurname(name, surname);
    }

//    @PutMapping(value = "updateActor", consumes = APPLICATION_JSON_VALUE)
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ActorDto updateActor(@RequestBody ActorDto actorDto) {
        return actorMapper.mapToActorDto(actorService.updateActor(actorDto));
    }
*/

}
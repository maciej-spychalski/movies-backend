package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.ActorDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.ActorMapper;
import pl.asbt.movies.storage.servise.ActorService;

import java.util.List;


@RequiredArgsConstructor
@Component
public class ActorFacade {
    private final ActorService actorService;
    private final ActorMapper actorMapper;

    public ActorDto createActor(ActorDto actorDto) {
        return actorMapper.mapToActorDto(actorService.saveActor(actorMapper.mapToActor(actorDto)));
    }

    public ActorDto fetchActor(Long actorId) throws StorageException {
        return actorMapper.mapToActorDto(actorService.getActor(actorId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no actor with given id.")
                        .build()
        ));
    }

    public List<ActorDto> fetchActorByNameAndSurname(String name, String surname) {
        return actorMapper.mapToActorsDto(actorService.getActorsByFirstnameAndSurname(name, surname));
    }

    public List<ActorDto> fetchActors() {
        return actorMapper.mapToActorsDto(actorService.getAllActors());
    }

    public void deleteActor(Long actorId) {
        actorService.deleteActor(actorId);
    }

    public void deleteActorByNameAndSurname(String name, String surname) {
        actorService.deleteActorsByNameAndSurname(name, surname);
    }

    public ActorDto updateActor(ActorDto actorDto) {
        return actorMapper.mapToActorDto(actorService.updateActor(actorDto));
    }
}

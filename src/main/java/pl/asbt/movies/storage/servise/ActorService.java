package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.domain.Actor;
import pl.asbt.movies.storage.dto.ActorDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.ActorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ActorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorService.class);
    private final ActorRepository actorRepository;

    public Actor saveActor(final Actor actor) {
        return actorRepository.save(actor);
    }

    public Optional<Actor> getActor(final Long id) {
        return actorRepository.findById(id);
    }

    public List<Actor> getActorsByNameAndSurname(final String firstname, final String surname) {
        return actorRepository.findByFirstnameAndAndSurname(firstname, surname);
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public void deleteActor(final Long id) {
        actorRepository.deleteById(id);
    }

    public void deleteActorsByNameAndSurname(final String firstname, final String surname) {
        actorRepository.deleteByFirstnameAndSurname(firstname, surname);
    }

    public Actor updateActor(final ActorDto actorDto) {
        Actor result = new Actor();
        Long actorId = actorDto.getId();
        try {
            Actor actor = getActor(actorId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no actor with given id.")
                            .build()
            );
            actor.setFirstname(actorDto.getFirstname());
            actor.setSurname(actorDto.getSurname());
            return saveActor(actor);
        } catch (Exception e) {
            LOGGER.error("Actor: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}

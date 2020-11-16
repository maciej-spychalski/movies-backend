package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.domain.Actor;
import pl.asbt.movies.storage.domain.ActorDto;
import pl.asbt.movies.storage.repository.ActorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActorService.class);
    private ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

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
            Actor actor = getActor(actorId).orElseThrow(SearchingException::new);
            actor.setFirstname(actorDto.getFirstname());
            actor.setSurname(actorDto.getSurname());
            return saveActor(actor);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_ACTOR);
        }
        return result;
    }

}

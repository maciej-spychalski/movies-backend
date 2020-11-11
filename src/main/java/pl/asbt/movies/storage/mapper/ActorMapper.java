package pl.asbt.movies.storage.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Actor;
import pl.asbt.movies.storage.domain.ActorDto;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActorMapper {

    public Actor mapToActor(final ActorDto actorDto) {
        return new Actor(
                actorDto.getFirstname(),
                actorDto.getSurname());
    }

    public ActorDto mapToActorDto(final Actor actor) {
        return new ActorDto(
                actor.getId(),
                actor.getFirstname(),
                actor.getSurname(),
                actor.getMovies().stream()
                        .map(m -> m.getTitle())
                        .collect(Collectors.toList()));
    }

    public List<ActorDto> mapToActorsDto(final List<Actor> actors) {
        return actors.stream()
                .map(a -> mapToActorDto(a))
                .collect(Collectors.toList());
    }

}

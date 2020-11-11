package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
import pl.asbt.movies.storage.domain.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends CrudRepository<Actor, Long> {

    @Override
    Actor save(Actor actor);

    @Override
    Optional<Actor> findById(Long id);

    Optional<Actor> findByFirstnameAndAndSurname(String firstname, String surname);

    @Override
    List<Actor> findAll();

    @Override
    void deleteById(Long id);

    void deleteByFirstnameAndSurname(String firstname, String surname);
}

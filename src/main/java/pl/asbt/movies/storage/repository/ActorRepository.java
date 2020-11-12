package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorRepository extends CrudRepository<Actor, Long> {

    @Override
    Actor save(Actor actor);

    @Override
    Optional<Actor> findById(Long id);

    List<Actor> findByFirstnameAndAndSurname(String firstname, String surname);

    @Override
    List<Actor> findAll();

    @Override
    void deleteById(Long id);

    @Transactional
    void deleteByFirstnameAndSurname(String firstname, String surname);
}

package pl.asbt.movies.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Actor;

import java.util.List;

@Transactional
@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    List<Actor> findByFirstnameAndAndSurname(String firstname, String surname);

    void deleteByFirstnameAndSurname(String firstname, String surname);
}


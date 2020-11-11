package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
import pl.asbt.movies.storage.domain.Director;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository extends CrudRepository<Director, Long> {

    @Override
    Director save(Director director);

    @Override
    Optional<Director> findById(Long id);

    Optional<Director> findByFirstnameAndAndSurname(String firstname, String surname);

    @Override
    List<Director> findAll();

    @Override
    void deleteById(Long id);

    void deleteByFirstnameAndSurname(String firstname, String surname);
}

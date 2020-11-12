package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Writer;

import java.util.List;
import java.util.Optional;

public interface WriterRepository extends CrudRepository<Writer, Long> {

    @Override
    Writer save(Writer writer);

    @Override
    Optional<Writer> findById(Long id);

    List<Writer> findByFirstnameAndAndSurname(String firstname, String surname);

    @Override
    List<Writer> findAll();

    @Override
    void deleteById(Long id);

    @Transactional
    void deleteByFirstnameAndAndSurname(String firstname, String surname);
}

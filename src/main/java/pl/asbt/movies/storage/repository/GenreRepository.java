package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    @Override
    Genre save(Genre genre);

    @Override
    Optional<Genre> findById(Long id);

    List<Genre> findByType(String type);

    @Override
    List<Genre> findAll();

    @Override
    void deleteById(Long id);

    @Transactional
    void deleteByType(String type);
}

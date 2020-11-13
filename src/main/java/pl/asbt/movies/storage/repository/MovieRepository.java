package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Override
    Movie save(Movie movie);

    @Override
    Optional<Movie> findById(Long id);

    List<Movie> findByTitle(String title);

    @Override
    List<Movie> findAll();

    @Override
    void deleteById(Long id);

    @Transactional
    void deleteByTitle(String title);
}

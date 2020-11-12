package pl.asbt.movies.storage.repository;

import org.springframework.data.repository.CrudRepository;
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

    void deleteByTitle(String title);
}

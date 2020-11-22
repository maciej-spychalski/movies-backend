package pl.asbt.movies.storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.Genre;

import java.util.List;

@Transactional
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    List<Genre> findByType(String type);

    void deleteByType(String type);
}

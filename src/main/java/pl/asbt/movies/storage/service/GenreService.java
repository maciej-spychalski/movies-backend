package pl.asbt.movies.storage.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Genre;
import pl.asbt.movies.storage.dto.GenreDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreService.class);
    private final GenreRepository genreRepository;

    public Genre saveGenre(final Genre genre) {
        return genreRepository.save(genre);
    }

    public Optional<Genre> getGenre(final Long id) {
        return genreRepository.findById(id);
    }

    public List<Genre> getAllGenresByType(final String type) {
        return genreRepository.findByType(type);
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public void deleteGenre(final Long id) {
        genreRepository.deleteById(id);
    }

    public void deleteGenreByType(final String type) {
        genreRepository.deleteByType(type);
    }

    public Genre updateGenre(final GenreDto genreDto) {
        Genre result = new Genre();
        Long genreId = genreDto.getId();
        try {
            Genre genre = getGenre(genreId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no genre with given id.")
                            .build()
            );
            genre.setType(genreDto.getType());
            return genreRepository.save(genre);
        } catch (Exception e) {
            LOGGER.error("Genre: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}

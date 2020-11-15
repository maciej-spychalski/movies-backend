package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Genre;
import pl.asbt.movies.storage.domain.GenreDto;
import pl.asbt.movies.storage.exception.CreatingException;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.GenreMapper;
import pl.asbt.movies.storage.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreService.class);
    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

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
            Genre genre = getGenre(genreId).orElseThrow(SearchingException::new);
            genre.setType(genreDto.getType());
            return saveGenre(genre);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_GENRE);
        }
        return result;
    }

}

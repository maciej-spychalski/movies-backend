package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.exception.GenreNotFoundException;
import pl.asbt.movies.storage.domain.Genre;
import pl.asbt.movies.storage.domain.GenreDto;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.mapper.GenreMapper;
import pl.asbt.movies.storage.repository.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenreService.class);
    private GenreRepository genreRepository;
    private GenreMapper genreMapper;

    @Autowired
    public GenreService(GenreRepository genreRepository, GenreMapper genreMapper) {
        this.genreRepository = genreRepository;
        this.genreMapper = genreMapper;
    }

    public Genre createGenre(final GenreDto genreDto) {
        List<Movie> movies = new ArrayList<>();
        return genreRepository.save(genreMapper.mapToGenre(genreDto));
    }

    public Optional<Genre> getGenre(final Long id) {
        return genreRepository.findById(id);
    }

    public Optional<Genre> getGenre(final String type) {
        return genreRepository.findByType(type);
    }

    public List<Genre> getAllGenre() {
        return genreRepository.findAll();
    }

    public void deleteGenre(final Long id) {
        genreRepository.deleteById(id);
    }

    public void deleteGenre(final String type) {
        genreRepository.deleteByType(type);
    }

    public void updateGenre(final GenreDto genreDto) {
        Long id = genreDto.getId();
        try {
            Genre genre = getGenre(id).orElseThrow(GenreNotFoundException::new);
            genre.setType(genreDto.getType());
            genreRepository.save(genre);
        } catch (Exception e) {
            LOGGER.error("There are no genre id = " + id);
        }
    }

}

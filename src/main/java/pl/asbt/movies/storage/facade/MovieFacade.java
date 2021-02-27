package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.MovieDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.MovieMapper;
import pl.asbt.movies.storage.service.MovieService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MovieFacade {
    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public void addDirector(Long movieId, Long directorId) throws StorageException {
        movieService.addDirector(movieId, directorId);
    }

    public void addWriter(Long movieId, Long writerId) throws StorageException {
        movieService.addWriter(movieId, writerId);
    }

    public void removeWriter(Long movieId, Long writerId) throws StorageException {
        movieService.removeWriter(movieId, writerId);
    }

    public void addActor(Long movieId, Long actorId) throws StorageException {
        movieService.addActor(movieId, actorId);
    }

    public void removeActor(Long movieId, Long actorId) throws StorageException {
        movieService.removeActor(movieId, actorId);
    }

    public void addGenre(Long movieId, Long genreId) throws StorageException {
        movieService.addGenre(movieId, genreId);
    }

    public void removeGenre(Long movieId, Long genreId) throws StorageException {
        movieService.removeGenre(movieId, genreId);
    }

    public MovieDto createMovie(MovieDto movieDto) {
        return movieMapper.mapToMovieDto(movieService.saveMovie(movieMapper.mapToMovie(movieDto)));
    }

    public MovieDto fetchMovie(Long movieId) throws StorageException {
        return movieMapper.mapToMovieDto(movieService.getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        ));
    }

    public List<MovieDto> fetchMovieByTitle(String title) {
        return movieMapper.mapToMoviesDto(movieService.getAllMoviesByTitle(title));
    }

    public List<MovieDto> fetchMovies() {
        return movieMapper.mapToMoviesDto(movieService.getAllMovies());
    }

    public void deleteMovie(Long titleId) {
        movieService.deleteMovie(titleId);
    }

    public void deleteMovieByTitle(String title) {
        movieService.deleteMovieByTitle(title);
    }

    public MovieDto updateMovie(MovieDto movieDto) {
        return movieMapper.mapToMovieDto(movieService.updateMovie(movieDto));
    }
}

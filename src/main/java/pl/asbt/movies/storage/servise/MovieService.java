package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.*;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final GenreService genreService;
    private final WriterService writerService;

    public Movie saveMovie(final Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie addDirector(final Long movieId, final Long directorId) throws StorageException {
        Movie movie = getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        );
        Director director = directorService.getDirector(directorId).orElse(new Director());
        movie.setDirector(director);
        director.getMovies().add(movie);
        return saveMovie(movie);
    }

    public Movie addWriter(Long movieId, Long writerId) throws StorageException {
        Movie movie = getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        );
        Writer writer = writerService.getWriter(writerId).orElse(new Writer());
        movie.getWriters().add(writer);
        writer.getMovies().add(movie);
        return saveMovie(movie);
    }

    public Movie removeWriter(Long movieId, Long writerId) throws StorageException {
        Movie movie = getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        );
        Writer writer = writerService.getWriter(writerId).orElse(new Writer());
        movie.getWriters().remove(writer);
        writer.getMovies().remove(movie);
        return saveMovie(movie);
    }

    public Movie addActor(Long movieId, Long actorId) throws StorageException {
        Movie movie = getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        );
        Actor actor = actorService.getActor(actorId).orElse(new Actor());
        movie.getActors().add(actor);
        actor.getMovies().add(movie);
        return saveMovie(movie);
    }

    public Movie removeActor(Long movieId, Long actorId) throws StorageException {
        Movie movie = getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        );
        Actor actor = actorService.getActor(actorId).orElse(new Actor());
        movie.getActors().remove(actor);
        actor.getMovies().remove(movie);
        return saveMovie(movie);
    }

    public Movie addGenre(Long movieId, Long genreId) throws StorageException {
        Movie movie = getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        );
        Genre genre = genreService.getGenre(genreId).orElse(new Genre());
        movie.getGenres().add(genre);
        genre.getMovies().add(movie);
        return movieRepository.save(movie);
    }

    public Movie removeGenre(Long movieId, Long genreId) throws StorageException {
        Movie movie = getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        );
        Genre genre = genreService.getGenre(genreId).orElse(new Genre());
        movie.getGenres().remove(genre);
        genre.getMovies().remove(movie);
        return saveMovie(movie);
    }


    public Optional<Movie> getMovie(final Long id) {
        return movieRepository.findById(id);
    }

    public List<Movie> getAllMoviesByTitle(final String title) {
        return movieRepository.findByTitle(title);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(final Long id) {
        movieRepository.deleteById(id);
    }

    public void deleteMovieByTitle(final String title) {
        movieRepository.deleteByTitle(title);
    }

    public Movie updateMovie(final MovieDto movieDto) {
        Movie result = new Movie();
        Long movieId = movieDto.getId();
        try {
            Movie movie = getMovie(movieId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no movie with given id.")
                            .build()
            );
            movie.setTitle(movieDto.getTitle());
            movie.setDuration(movieDto.getDuration());
            movie.setPrice(movieDto.getPrice());
            return saveMovie(movie);
        } catch (Exception e) {
            LOGGER.error("Movie: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}

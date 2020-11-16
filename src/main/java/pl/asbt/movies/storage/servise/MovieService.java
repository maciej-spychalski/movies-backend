package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.*;
import pl.asbt.movies.storage.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    private MovieRepository movieRepository;
    private MovieMapper movieMapper;
    private ActorService actorService;
    private DirectorService directorService;
    private GenreService genreService;
    private WriterService writerService;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper, ActorService actorService,
                        DirectorService directorService, GenreService genreService, WriterService writerService) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.actorService = actorService;
        this.directorService = directorService;
        this.genreService = genreService;
        this.writerService = writerService;
    }

    private List<Genre> getGenres(MovieDto movieDto) {
        List<Genre> genres = new ArrayList<>();
        for(GenreDto theGenre : movieDto.getGenresDto()) {
            Genre genre = genreService.getGenre(theGenre.getId()).orElse(new Genre());
            genre.getMovies().add(new Movie(movieDto.getTitle(), movieDto.getDuration()));
            genres.add(genre);
        }
        return genres;
    }

    private List<Actor> getActors(MovieDto movieDto) {
        List<Actor> actors = new ArrayList<>();
        for(ActorDto theActor : movieDto.getActorsDto()) {
            Actor actor = actorService.getActor(theActor.getId()).orElse(new Actor());
            actor.getMovies().add(new Movie(movieDto.getTitle(), movieDto.getDuration()));
            actors.add(actor);
        }
        return actors;
    }

    private List<Writer> getWriters(MovieDto movieDto) {
        List<Writer> writers = new ArrayList<>();
        for(WriterDto theWriter : movieDto.getWritersDto()) {
            Writer writer = writerService.getWriter(theWriter.getId()).orElse(new Writer());
            writer.getMovies().add(new Movie(movieDto.getTitle(), movieDto.getDuration()));
            writers.add(writer);
        }
        return writers;
    }

    private Director getDirector(MovieDto movieDto) {
        DirectorDto directorDto = movieDto.getDirectorDto();
        Director director = directorService.getDirector(directorDto.getId()).orElse(new Director());
        director.getMovies().add(new Movie(movieDto.getTitle(), movieDto.getDuration()));
        return director;
    }

    public Movie saveMovie(final Movie movie) {
        return movieRepository.save(movie);
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
            Movie movie = getMovie(movieId).orElseThrow(SearchingException::new);
            movie.setTitle(movieDto.getTitle());
            movie.setDuration(movieDto.getDuration());
            return saveMovie(movie);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_MOVIE);
        }
        return result;
    }

}

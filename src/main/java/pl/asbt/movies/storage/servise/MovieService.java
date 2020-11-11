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

    public Movie movieFromMovieDto (final MovieDto movieDto) {
        DirectorDto directorDto = movieDto.getDirectorDto();
        Director director = directorService.getDirector(directorDto.getFirstname(), directorDto.getSurname())
                .orElse(new Director(directorDto.getFirstname(), directorDto.getSurname()));

        List<Writer> writers = new ArrayList<>();
        for(WriterDto theWriter : movieDto.getWritersDto()) {
            Writer writer = writerService.getWriter(theWriter.getFirstname(), theWriter.getSurname())
                    .orElse(new Writer(theWriter.getFirstname(), theWriter.getSurname()));
            writers.add(writer);
        }

        List<Actor> actors = new ArrayList<>();
        for(ActorDto theActor : movieDto.getActorsDto()) {
            Actor actor = actorService.getActor(theActor.getFirstname(), theActor.getSurname())
                    .orElse(new Actor(theActor.getFirstname(), theActor.getSurname()));
            actors.add(actor);
        }

        List<Genre> genres = new ArrayList<>();
        for(GenreDto theGenre : movieDto.getGenresDto()) {
            Genre genre = genreService.getGenre(theGenre.getType())
                    .orElse(new Genre(theGenre.getType()));
            genres.add(genre);
        }

        return movieMapper.mapToMovie(movieDto, director, writers, actors, genres);
    }

    public Movie createMovie(final MovieDto movieDto) {
        return movieRepository.save(movieFromMovieDto(movieDto));
    }

    public Optional<Movie> getMovie(final Long id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> getMovie(final String title) {
        return movieRepository.findByTitle(title);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public void deleteMovie(final Long id) {
        movieRepository.deleteById(id);
    }

    public void deleteMovie(final String title) {
        movieRepository.deleteByTitle(title);
    }

    public void updateMovie(final MovieDto movieDto) {
        Long id = movieDto.getId();
        Movie themovie = movieFromMovieDto(movieDto);

        try {
            Movie movie = getMovie(id).orElseThrow(SearchingException::new);
            movie.setTitle(themovie.getTitle());
            movie.setDuration(themovie.getDuration());
            movie.setWriters(themovie.getWriters());
            movie.setActors(themovie.getActors());
            movie.setGenres(themovie.getGenres());
            movie.setDuration(themovie.getDuration());
            movieRepository.save(movie);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_MOVIE);
        }
    }

}

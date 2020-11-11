package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.exception.*;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.mapper.MovieMapper;
import pl.asbt.movies.storage.repository.MovieRepository;
import pl.asbt.movies.storage.repository.WriterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
    private MovieRepository movieRepository;
    private MovieMapper movieMapper;
    private ActorService actorService;
    private DirectorService directorService;
    private GenreService genreService;
    private StorageItemService storageItemService;
    private WriterService writerService;

    @Autowired
    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper, ActorService actorService,
                        DirectorService directorService, GenreService genreService,
                        StorageItemService storageItemService, WriterService writerService) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.actorService = actorService;
        this.directorService = directorService;
        this.genreService = genreService;
        this.storageItemService = storageItemService;
        this.writerService = writerService;
    }

    public Movie createMovie(final MovieDto movieDto) {
        Director director = null;
        try {
            director = directorService.getDirector(movieDto.getDirectorId()).orElseThrow(DirectorNotFoundException::new);
        } catch (DirectorNotFoundException e) {
            LOGGER.error("There are no director id =" + movieDto.getDirectorId());
        }

//        List<Writer> writers = new ArrayList<>();
//        for(Long writerId : movieDto.getWritersId()) {
//            writers.add(writerService.getWriter(writerId).orElse(new Writer()));
//        }

        List<Writer> writers = movieDto.getWritersId().stream()
                .map(id -> {
                    try {
                        return writerService.getWriter(id).orElseThrow(WriterNotFoundException::new);
                    } catch (WriterNotFoundException e) {
                        LOGGER.error("There are no writer id =" + id);
                    }
                    return null;
                })
                .collect(Collectors.toList());

        List<Actor> actors = movieDto.getActorsId().stream()
                .map(id -> {
                    try {
                        return actorService.getActor(id).orElseThrow(ActorNotFoundException::new);
                    } catch (ActorNotFoundException e) {
                        LOGGER.error("There are no actor id =" + id);
                    }
                    return null;
                })
                .collect(Collectors.toList());

        List<Genre> genres = movieDto.getGenresId().stream()
                .map(id -> {
                    try {
                        return genreService.getGenre(id).orElseThrow(GenreNotFoundException::new);
                    } catch (GenreNotFoundException e) {
                        LOGGER.error("There are no genre id =" + id);
                    }
                    return null;
                })
                .collect(Collectors.toList());

        if(director == null || writers == null || actors == null || genres == null)  {
            LOGGER.error("Movie have missing or wrong fields");
            return new Movie();
        } else {
            return movieRepository.save(movieMapper.mapToMovie(movieDto, director, writers, actors, genres));
        }
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

        try {
            Movie movie = getMovie(id).orElseThrow(MovieNotFoundException::new);
            movie.setTitle(movieDto.getTitle());
            movie.setDuration(movieDto.getDuration());
            //Todo: upgrade pozostałych pól encji
            movieRepository.save(movie);
        } catch (Exception e) {
            LOGGER.error("There are no movie id = " + id);
        }

//        Movie theMovie = createMovie(movieDto);
//        Long theMovieId = theMovie.getId();
//        movieRepository.deleteById(theMovieId);
//        theMovie.setId(id);
//        movieRepository.save(theMovie);
    }

}

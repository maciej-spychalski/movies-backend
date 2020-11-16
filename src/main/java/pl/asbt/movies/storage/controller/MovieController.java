package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.MovieMapper;
import pl.asbt.movies.storage.servise.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @Autowired
    MovieMapper movieMapper;


    @Autowired
    DirectorService directorService;

    @Autowired
    WriterService writerService;

    @Autowired
    ActorService actorService;

    @Autowired
    GenreService genreService;

    private Movie.MovieBuilder movieBuilder = new Movie.MovieBuilder();

    /*@GetMapping(value = "/{name}/{surname}")
    public List<DirectorDto> getDirectorByNameAndSurname(@PathVariable String name, @PathVariable String surname) {*/

    //    @PostMapping(value = "/title", consumes = APPLICATION_JSON_VALUE)
//    public void addTile(@RequestParam String movieTitle) {
    @PatchMapping(value = "/add_title/{movieTitle}")
    public void addTile(@PathVariable String movieTitle) {
        movieBuilder.title(movieTitle);
    }

    //    @PostMapping(value = "/duration", consumes = APPLICATION_JSON_VALUE)
//    public void addDuration(@RequestParam Integer movieDuration) {
    @PatchMapping(value = "/add_duration/{movieDuration}")
    public void addDuration(@PathVariable Integer movieDuration) {
        movieBuilder.duration(movieDuration);
    }

    //    @PostMapping(value = "/director", consumes = APPLICATION_JSON_VALUE)
//    public void addDirector(@RequestParam Long directorId) {
    @PatchMapping(value = "/add_director/{directorId}")
    public void addDirector(@PathVariable Long directorId) {
        movieBuilder.director(directorService.getDirector(directorId).orElse(new Director()));
    }

//        @PostMapping(value = "/writer", consumes = APPLICATION_JSON_VALUE)
//        public void addWriter(@RequestParam Long writerId) {
    @PatchMapping(value = "/add_writer/{writerId}")
    public void addWriter(@PathVariable Long writerId) {
        movieBuilder.writer(writerService.getWriter(writerId).orElse(new Writer()));
    }

    //    @PostMapping(value = "/actor", consumes = APPLICATION_JSON_VALUE)
//    public void addActor(@RequestParam Long actorId) {
    @PatchMapping(value = "/add_actor/{actorId}")
    public void addActor(@PathVariable Long actorId) {
        movieBuilder.actor(actorService.getActor(actorId).orElse(new Actor()));
    }

    //    @PostMapping(value = "/genre", consumes = APPLICATION_JSON_VALUE)
//    public void addGenre(@RequestParam Long genreId) {
    @PatchMapping(value = "/add_genre/{genreId}")
    public void addGenre(@PathVariable Long genreId) {
        movieBuilder.genre(genreService.getGenre(genreId).orElse(new Genre()));
    }

    @PatchMapping(value = "/build/{movieTitle}")
    public void movieBuild(@PathVariable String movieTitle) {
        movieBuilder.title(movieTitle);
        Movie movie = movieBuilder.build();
        movieService.saveMovie(movie);
        movieBuilder = new Movie.MovieBuilder();
    }
/*@PatchMapping(value = "/add_title/{movieTitle}")
    public void addTile(@PathVariable String movieTitle) {
        movieBuilder.title(movieTitle);*/

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createMovie(@RequestBody MovieDto movieDto) {
        movieService.saveMovie(movieMapper.mapToMovie(movieDto));
    }

    @GetMapping(value = "/{movieId}")
    public MovieDto getMovie(@PathVariable Long movieId) throws SearchingException {
        return movieMapper.mapToMovieDto(movieService.getMovie(movieId).orElseThrow(SearchingException::new));
    }

    @GetMapping(value = "/title/{title}")
    public List<MovieDto> getMovieByTitle(@PathVariable String title) {
        return movieMapper.mapToMoviesDto(movieService.getAllMoviesByTitle(title));
    }

    @GetMapping
    public List<MovieDto> getMovies() {
        return movieMapper.mapToMoviesDto(movieService.getAllMovies());
    }

    @DeleteMapping(value = "/{titleId}")
    public void deleteMovie(@PathVariable Long titleId) {
        movieService.deleteMovie(titleId);
    }

    @DeleteMapping(value = "/title/{title}")
    public void deleteMovieByTitle(@PathVariable String title) {
        movieService.deleteMovieByTitle(title);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public MovieDto updateMovie(@RequestBody MovieDto movieDto) {
        return movieMapper.mapToMovieDto(movieService.updateMovie(movieDto));
    }
}

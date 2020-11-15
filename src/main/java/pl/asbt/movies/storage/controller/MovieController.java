package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.MovieDto;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.MovieMapper;
import pl.asbt.movies.storage.servise.MovieService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/movies")
public class MovieController {
/*
    @Autowired
    MovieService movieService;

    @Autowired
    MovieMapper movieMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createMovie(@RequestBody MovieDto movieDto) {
        movieService.saveMovie(movieDto);
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
    }*/
}

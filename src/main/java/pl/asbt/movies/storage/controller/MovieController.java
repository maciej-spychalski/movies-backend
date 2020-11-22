package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.MovieDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.MovieMapper;
import pl.asbt.movies.storage.servise.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    @PatchMapping(value = "/{movieId}/add-director/{directorId}")
    public void addDirector(@Validated @PathVariable Long movieId,
                            @Validated @PathVariable Long directorId) throws StorageException {
        movieService.addDirector(movieId, directorId);
    }

    @PatchMapping(value = "/{movieId}/add-writer/{writerId}")
    public void addWriter(@Validated @PathVariable Long movieId,
                          @Validated @PathVariable Long writerId) throws StorageException {
        movieService.addWriter(movieId, writerId);
    }

    @PatchMapping(value = "/{movieId}/remove-writer/{writerId}")
    public void removeWriter(@Validated @PathVariable Long movieId,
                             @Validated @PathVariable Long writerId) throws StorageException {
        movieService.removeWriter(movieId, writerId);
    }

    @PatchMapping(value = "/{movieId}/add-actor/{actorId}")
    public void addActor(@Validated @PathVariable Long movieId,
                         @Validated @PathVariable Long actorId) throws StorageException {
        movieService.addActor(movieId, actorId);
    }

    @PatchMapping(value = "/{movieId}/remove-actor/{actorId}")
    public void removeActor(@Validated @PathVariable Long movieId,
                            @Validated @PathVariable Long actorId) throws StorageException {
        movieService.removeActor(movieId, actorId);
    }

    @PatchMapping(value = "/{movieId}/add-genre/{genreId}")
    public void addGenre(@Validated @PathVariable Long movieId,
                         @Validated @PathVariable Long genreId) throws StorageException {
        movieService.addGenre(movieId, genreId);
    }

    @PatchMapping(value = "/{movieId}/remove-genre/{genreId}")
    public void removeGenre(@Validated @PathVariable Long movieId,
                            @Validated @PathVariable Long genreId) throws StorageException {
        movieService.removeGenre(movieId, genreId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createMovie(@Validated @RequestBody MovieDto movieDto) {
        movieService.saveMovie(movieMapper.mapToMovie(movieDto));
    }

    @GetMapping(value = "/{movieId}")
    public MovieDto getMovie(@Validated @PathVariable Long movieId) throws StorageException {
        return movieMapper.mapToMovieDto(movieService.getMovie(movieId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no movie with given id.")
                        .build()
        ));
    }

    @GetMapping(value = "/title/{title}")
    public List<MovieDto> getMovieByTitle(@Validated @PathVariable String title) {
        return movieMapper.mapToMoviesDto(movieService.getAllMoviesByTitle(title));
    }

    @GetMapping
    public List<MovieDto> getMovies() {
        return movieMapper.mapToMoviesDto(movieService.getAllMovies());
    }

    @DeleteMapping(value = "/{titleId}")
    public void deleteMovie(@Validated @PathVariable Long titleId) {
        movieService.deleteMovie(titleId);
    }

    @DeleteMapping(value = "/title/{title}")
    public void deleteMovieByTitle(@Validated @PathVariable String title) {
        movieService.deleteMovieByTitle(title);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public MovieDto updateMovie(@Validated @RequestBody MovieDto movieDto) {
        return movieMapper.mapToMovieDto(movieService.updateMovie(movieDto));
    }
}

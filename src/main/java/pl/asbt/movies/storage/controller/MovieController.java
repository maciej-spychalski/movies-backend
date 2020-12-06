package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.MovieDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.MovieFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/movies")
public class MovieController {

    private final MovieFacade movieFacade;

//    @PatchMapping(value = "/{movieId}/add-director/{directorId}")
//    @PutMapping(value = "/{movieId}/add-director/{directorId}")
    @PostMapping(value = "/{movieId}/add-director/{directorId}")
    public void addDirector(@Validated @PathVariable Long movieId,
                            @Validated @PathVariable Long directorId) throws StorageException {
        movieFacade.addDirector(movieId, directorId);
    }

//    @PatchMapping(value = "/{movieId}/add-writer/{writerId}")
//    @PutMapping(value = "/{movieId}/add-writer/{writerId}")
    @PostMapping(value = "/{movieId}/add-writer/{writerId}")
    public void addWriter(@Validated @PathVariable Long movieId,
                          @Validated @PathVariable Long writerId) throws StorageException {
        movieFacade.addWriter(movieId, writerId);
    }

//    @PatchMapping(value = "/{movieId}/remove-writer/{writerId}")
//    @PutMapping(value = "/{movieId}/remove-writer/{writerId}")
    @PostMapping(value = "/{movieId}/remove-writer/{writerId}")
    public void removeWriter(@Validated @PathVariable Long movieId,
                             @Validated @PathVariable Long writerId) throws StorageException {
        movieFacade.removeWriter(movieId, writerId);
    }

//    @PatchMapping(value = "/{movieId}/add-actor/{actorId}")
//    @PutMapping(value = "/{movieId}/add-actor/{actorId}")
    @PostMapping(value = "/{movieId}/add-actor/{actorId}")
    public void addActor(@Validated @PathVariable Long movieId,
                         @Validated @PathVariable Long actorId) throws StorageException {
        movieFacade.addActor(movieId, actorId);
    }

//    @PatchMapping(value = "/{movieId}/remove-actor/{actorId}")
//    @PutMapping(value = "/{movieId}/remove-actor/{actorId}")
    @PostMapping(value = "/{movieId}/remove-actor/{actorId}")
    public void removeActor(@Validated @PathVariable Long movieId,
                            @Validated @PathVariable Long actorId) throws StorageException {
        movieFacade.removeActor(movieId, actorId);
    }

//    @PatchMapping(value = "/{movieId}/add-genre/{genreId}")
//    @PutMapping(value = "/{movieId}/add-genre/{genreId}")
    @PostMapping(value = "/{movieId}/add-genre/{genreId}")
    public void addGenre(@Validated @PathVariable Long movieId,
                         @Validated @PathVariable Long genreId) throws StorageException {
        movieFacade.addGenre(movieId, genreId);
    }

//    @PatchMapping(value = "/{movieId}/remove-genre/{genreId}")
//    @PutMapping(value = "/{movieId}/remove-genre/{genreId}")
    @PostMapping(value = "/{movieId}/remove-genre/{genreId}")
    public void removeGenre(@Validated @PathVariable Long movieId,
                            @Validated @PathVariable Long genreId) throws StorageException {
        movieFacade.removeGenre(movieId, genreId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createMovie(@Validated @RequestBody MovieDto movieDto) {
        movieFacade.createMovie(movieDto);
    }

    @GetMapping(value = "/{movieId}")
    public MovieDto getMovie(@Validated @PathVariable Long movieId) throws StorageException {
        return movieFacade.fetchMovie(movieId);
    }

    @GetMapping(value = "/title/{title}")
    public List<MovieDto> getMovieByTitle(@Validated @PathVariable String title) {
        return movieFacade.fetchMovieByTitle(title);
    }

    @GetMapping
    public List<MovieDto> getMovies() {
        return movieFacade.fetchMovies();
    }

    @DeleteMapping(value = "/{titleId}")
    public void deleteMovie(@Validated @PathVariable Long titleId) {
        movieFacade.deleteMovie(titleId);
    }

    @DeleteMapping(value = "/title/{title}")
    public void deleteMovieByTitle(@Validated @PathVariable String title) {
        movieFacade.deleteMovieByTitle(title);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public MovieDto updateMovie(@Validated @RequestBody MovieDto movieDto) {
        return movieFacade.updateMovie(movieDto);
    }
}

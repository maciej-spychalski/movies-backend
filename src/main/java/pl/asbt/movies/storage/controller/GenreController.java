package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.GenreDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.GenreFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/genres")
public class GenreController {

    private final GenreFacade genreFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createGenre(@Validated @RequestBody GenreDto genreDto) {
        genreFacade.createGenre(genreDto);
    }

    @GetMapping(value = "/{genreId}")
    public GenreDto getGenre(@Validated @PathVariable Long genreId) throws StorageException {
        return genreFacade.fetchGenre(genreId);
    }

    @GetMapping(value = "/type/{type}")
    public List<GenreDto> getGenreByType(@Validated @PathVariable String type) {
        return genreFacade.fetchGenreByType(type);
    }

    @GetMapping
    public List<GenreDto> getGenres() {
        return genreFacade.fetchGenres();
    }

    @DeleteMapping(value = "/{genreId}")
    public void deleteGenre(@Validated @PathVariable Long genreId) {
        genreFacade.deleteGenre(genreId);
    }

    @DeleteMapping(value = "/type/{type}")
    public void deleteGenreByType(@Validated @PathVariable String type) {
        genreFacade.deleteGenreByType(type);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public GenreDto updateGenre(@Validated @RequestBody GenreDto genreDto) {
        return genreFacade.updateGenre(genreDto);
    }
}

package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.GenreDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.GenreMapper;
import pl.asbt.movies.storage.servise.GenreService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/genres")
public class GenreController {

    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createGenre(@Validated @RequestBody GenreDto genreDto) {
        genreService.saveGenre(genreMapper.mapToGenre(genreDto));
    }

    @GetMapping(value = "/{genreId}")
    public GenreDto getGenre(@Validated @PathVariable Long genreId) throws StorageException {
        return genreMapper.mapToGenreDto(genreService.getGenre(genreId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no genre with given id.")
                        .build()
        ));
    }

    @GetMapping(value = "/type/{type}")
    public List<GenreDto> getGenreByType(@Validated @PathVariable String type) {
        return genreMapper.mapToGenresDto(genreService.getAllGenresByType(type));
    }
    @GetMapping
    public List<GenreDto> getGenres() {
        return genreMapper.mapToGenresDto(genreService.getAllGenres());
    }

    @DeleteMapping(value = "/{genreId}")
    public void deleteGenre(@Validated @PathVariable Long genreId) {
        genreService.deleteGenre(genreId);
    }

    @DeleteMapping(value = "/type/{type}")
    public void deleteGenreByType(@Validated @PathVariable String type) {
        genreService.deleteGenreByType(type);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public GenreDto updateGenre(@Validated @RequestBody GenreDto genreDto) {
        return genreMapper.mapToGenreDto(genreService.updateGenre(genreDto));
    }
}

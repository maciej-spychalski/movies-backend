package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.GenreDto;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.GenreMapper;
import pl.asbt.movies.storage.servise.GenreService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/genres")
public class GenreController {

    @Autowired
    GenreService genreService;

    @Autowired
    GenreMapper genreMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createGenre(@RequestBody GenreDto genreDto) {
        genreService.createGenre(genreDto);
    }

    @GetMapping(value = "/{genreId}")
    public GenreDto getGenre(@PathVariable Long genreId) throws SearchingException {
        return genreMapper.mapToGenreDto(genreService.getGenre(genreId).orElseThrow(SearchingException::new));
    }

    @GetMapping(value = "/type/{type}")
    public List<GenreDto> getGenreByType(@PathVariable String type) {
        return genreMapper.mapToGenresDto(genreService.getAllGenresByType(type));
    }
    @GetMapping
    public List<GenreDto> getGenres() {
        return genreMapper.mapToGenresDto(genreService.getAllGenres());
    }

    @DeleteMapping(value = "/{genreId}")
    public void deleteGenre(@PathVariable Long genreId) {
        genreService.deleteGenre(genreId);
    }

//    @DeleteMapping(value = "/{type}")
    @DeleteMapping(value = "/type/{type}")
    public void deleteGenreByType(@PathVariable String type) {
        genreService.deleteGenreByType(type);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public GenreDto updateGenre(@RequestBody GenreDto genreDto) {
        return genreMapper.mapToGenreDto(genreService.updateGenre(genreDto));
    }
}

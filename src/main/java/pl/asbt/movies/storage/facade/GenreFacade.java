package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.GenreDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.GenreMapper;
import pl.asbt.movies.storage.service.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class GenreFacade {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    public GenreDto createGenre(GenreDto genreDto) {
        return genreMapper.mapToGenreDto(genreService.saveGenre(genreMapper.mapToGenre(genreDto)));
    }

    public GenreDto fetchGenre(Long genreId) throws StorageException {
        return genreMapper.mapToGenreDto(genreService.getGenre(genreId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no genre with given id.")
                        .build()
        ));
    }

    public List<GenreDto> fetchGenreByType(String type) {
        return genreMapper.mapToGenresDto(genreService.getAllGenresByType(type));
    }

    public List<GenreDto> fetchGenres() {
        return genreMapper.mapToGenresDto(genreService.getAllGenres());
    }

    public void deleteGenre(Long genreId) {
        genreService.deleteGenre(genreId);
    }

    public void deleteGenreByType(String type) {
        genreService.deleteGenreByType(type);
    }

    public GenreDto updateGenre(GenreDto genreDto) {
        return genreMapper.mapToGenreDto(genreService.updateGenre(genreDto));
    }
}

package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Genre;
import pl.asbt.movies.storage.dto.GenreDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    public Genre mapToGenre(final GenreDto genreDto) {
        return new Genre(
                genreDto.getType());
    }

    public GenreDto mapToGenreDto(final Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getType(),
                genre.getMovies().stream()
                        .map(m -> m.getTitle())
                        .collect(Collectors.toList()));
    }

    public List<GenreDto> mapToGenresDto(final List<Genre> genres) {
        return genres.stream()
                .map(g -> mapToGenreDto(g))
                .collect(Collectors.toList());
    }

}

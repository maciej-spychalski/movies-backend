package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Genre;
import pl.asbt.movies.storage.domain.GenreDto;
import pl.asbt.movies.storage.domain.Movie;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    public Genre mapToGenre(final GenreDto genreDto, List<Movie> movies) {
        return new Genre(
                genreDto.getType(),
                movies);
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

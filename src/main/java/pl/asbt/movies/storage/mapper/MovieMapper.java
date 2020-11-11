package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public Movie mapToMovie(final MovieDto movieDto, Director director, List<Writer> writers,
                            List<Actor> actors, List<Genre> genres) {
        return new Movie(
                movieDto.getTitle(),
                director,
                writers,
                actors,
                genres,
                movieDto.getDuration());
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector().getId(),

                movie.getWriters().stream()
                        .map(w -> w.getId())
                        .collect(Collectors.toList()),

                movie.getActors().stream()
                        .map(a -> a.getId())
                        .collect(Collectors.toList()),

                movie.getGenres().stream()
                        .map(g -> g.getId())
                        .collect(Collectors.toList()),

                movie.getDuration());
    }

    public List<MovieDto> mapToMoviesDto(final List<Movie> movies) {
        return movies.stream()
                .map(m -> mapToMovieDto(m))
                .collect(Collectors.toList());
    }

}

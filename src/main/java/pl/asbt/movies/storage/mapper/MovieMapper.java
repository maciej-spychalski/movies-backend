package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    public Movie mapToMovie(final MovieDto movieDto, Director director, List<Writer> writers,
                            List<Actor> actors, List<Genre> genres, StorageItem storageItem) {
        return new Movie(
                movieDto.getTitle(),
                director,
                writers,
                actors,
                genres,
                storageItem,
                movieDto.getDuration());
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector().getFirstname() + " " + movie.getDirector().getSurname(),

                movie.getWriters().stream()
                .map(w -> w.getFirstname() + " " + w.getSurname())
                .collect(Collectors.toList()),

                movie.getActors().stream()
                .map(a -> a.getFirstname() + " " + a.getSurname())
                .collect(Collectors.toList()),

                movie.getGenres().stream()
                .map(g -> g.getType())
                .collect(Collectors.toList()),

                movie.getStorageItem().getId(),
                movie.getDuration());
    }

    public List<MovieDto> mapToMoviesDto(final List<Movie> movies) {
        return movies.stream()
                .map(m -> mapToMovieDto(m))
                .collect(Collectors.toList());
    }

}

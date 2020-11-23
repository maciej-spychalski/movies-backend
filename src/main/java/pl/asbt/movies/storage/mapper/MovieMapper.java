package pl.asbt.movies.storage.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.DirectorDto;
import pl.asbt.movies.storage.dto.MovieDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MovieMapper {

    private final ActorMapper actorMapper;
    private final DirectorMapper directorMapper;
    private final GenreMapper genreMapper;
    private final WriterMapper writerMapper;
    private final StorageItemMapper storageItemMapper;

    public Movie mapToMovie(final MovieDto movieDto) {
        return new Movie(movieDto.getTitle(), movieDto.getDuration());
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        DirectorDto directorDto = null;
        if (movie.getDirector() != null) {
            directorDto = directorMapper.mapToDirectorDto(movie.getDirector());
        }
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                directorDto,
//                directorMapper.mapToDirectorDto(movie.getDirector()),
                writerMapper.mapToWritersDto(movie.getWriters()),
                actorMapper.mapToActorsDto(movie.getActors()),
                genreMapper.mapToGenresDto(movie.getGenres()),
                movie.getDuration());
    }

    public List<MovieDto> mapToMoviesDto(final List<Movie> movies) {
        return movies.stream()
                .map(m -> mapToMovieDto(m))
                .collect(Collectors.toList());
    }

}

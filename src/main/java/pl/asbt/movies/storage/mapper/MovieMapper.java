package pl.asbt.movies.storage.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieMapper {

    private ActorMapper actorMapper;
    private DirectorMapper directorMapper;
    private GenreMapper genreMapper;
    private WriterMapper writerMapper;
    private StorageItemMapper storageItemMapper;

    @Autowired
    public MovieMapper(ActorMapper actorMapper, DirectorMapper directorMapper,
                       GenreMapper genreMapper, WriterMapper writerMapper,
                       StorageItemMapper storageItemMapper) {
        this.actorMapper = actorMapper;
        this.directorMapper = directorMapper;
        this.genreMapper = genreMapper;
        this.writerMapper = writerMapper;
        this.storageItemMapper = storageItemMapper;
    }

    public Movie mapToMovie(final MovieDto movieDto) {
        return new Movie(movieDto.getTitle(), movieDto.getDuration());
    }

    public MovieDto mapToMovieDto(final Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                directorMapper.mapToDirectorDto(movie.getDirector()),
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

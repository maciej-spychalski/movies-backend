package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DirectorMapper {

    public Director mapToDirector(final DirectorDto directorDto, List<Movie> movies) {
        return new Director(
                directorDto.getFirstname(),
                directorDto.getSurname(),
                movies);
    }

    public DirectorDto mapToDirectorDto(final Director director) {
        return new DirectorDto(
                director.getId(),
                director.getFirstname(),
                director.getSurname(),
                director.getMovies().stream()
                        .map(m -> m.getTitle())
                        .collect(Collectors.toList()));
    }

    public List<DirectorDto> mapToDirectorsDto(final List<Director> directors) {
        return directors.stream()
                .map(d -> mapToDirectorDto(d))
                .collect(Collectors.toList());
    }
}


package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.DirectorDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DirectorMapper {

    public Director mapToDirector(final DirectorDto directorDto) {
        return new Director(
                directorDto.getFirstname(),
                directorDto.getSurname());
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


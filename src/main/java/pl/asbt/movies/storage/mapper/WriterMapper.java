package pl.asbt.movies.storage.mapper;

import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.Writer;
import pl.asbt.movies.storage.domain.WriterDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WriterMapper {

    public Writer mapToWriter(final WriterDto writerDto, List<Movie> movies) {
        return new Writer(
                writerDto.getFirstname(),
                writerDto.getSurname(),
                movies);
    }

    public WriterDto mapToWriterDto (final Writer writer) {
        return new WriterDto(
                writer.getId(),
                writer.getFirstname(),
                writer.getSurname(),
                writer.getMovies().stream()
                .map(m -> m.getTitle())
                .collect(Collectors.toList()));
    }

    public List<WriterDto> mapToWritersDto(final List<Writer> writers) {
        return writers.stream()
                .map(w -> mapToWriterDto(w))
                .collect(Collectors.toList());
    }

}

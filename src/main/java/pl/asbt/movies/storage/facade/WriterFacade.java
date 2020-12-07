package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.WriterDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.WriterMapper;
import pl.asbt.movies.storage.servise.WriterService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class WriterFacade {
    private final WriterService writerService;
    private final WriterMapper writerMapper;

    public WriterDto createWriter(WriterDto writerDto) {
        return writerMapper.mapToWriterDto(writerService.saveWriter(writerMapper.mapToWriter(writerDto)));
    }

    public WriterDto fetchWriter(Long writerId) throws StorageException {
        return writerMapper.mapToWriterDto(writerService.getWriter(writerId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no writer with given id.")
                        .build()));
    }

    public List<WriterDto> fetchWriterByNameAndSurname(String name, String surname) {
        return writerMapper.mapToWritersDto(writerService.getAllWritersByNameAndSurname(name, surname));
    }

    public List<WriterDto> fetchWriters() {
        return writerMapper.mapToWritersDto(writerService.getAllWriters());
    }

    public void deleteWriter(Long writerId) {
        writerService.deleteWriter(writerId);
    }

    public void deleteWriterByNameAndSurname(String name, String surname) {
        writerService.deleteWriterByNameAndSurname(name, surname);
    }

    public WriterDto updateWriter(WriterDto writerDto) {
        return writerMapper.mapToWriterDto(writerService.updateWriter(writerDto));
    }
}

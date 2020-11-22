package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.WriterDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.WriterMapper;
import pl.asbt.movies.storage.servise.WriterService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/writers")
public class WriterController {
    @Autowired
    WriterService writerService;

    @Autowired
    WriterMapper writerMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createWriter(@Validated @RequestBody WriterDto writerDto) {
        writerService.saveWriter(writerMapper.mapToWriter(writerDto));
    }

    @GetMapping(value = "/{writerId}")
    public WriterDto getWriter(@Validated @PathVariable Long writerId) throws StorageException {
        return writerMapper.mapToWriterDto(writerService.getWriter(writerId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no writer with given id.")
                        .build()));
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<WriterDto> getWriterByNameAndSurname(@Validated @PathVariable String name,
                                                     @PathVariable String surname) {
        return writerMapper.mapToWritersDto(writerService.getAllWritersByNameAndSurname(name, surname));
    }

    @GetMapping
    public List<WriterDto> getWriters() {
        return writerMapper.mapToWritersDto(writerService.getAllWriters());
    }

    @DeleteMapping(value = "/{writerId}")
    public void deleteWriter(@Validated @PathVariable Long writerId) {
        writerService.deleteWriter(writerId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteWriterByNameAndSurname(@Validated @PathVariable String name,
                                             @Validated @PathVariable String surname) {
        writerService.deleteWriterByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public WriterDto updateWriter(@Validated @RequestBody WriterDto writerDto) {
        return writerMapper.mapToWriterDto(writerService.updateWriter(writerDto));
    }
}

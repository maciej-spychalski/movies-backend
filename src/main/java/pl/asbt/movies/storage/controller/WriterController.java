package pl.asbt.movies.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.domain.WriterDto;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.mapper.WriterMapper;
import pl.asbt.movies.storage.servise.WriterService;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/storage/writers")
public class WriterController {
/*
    @Autowired
    WriterService writerService;

    @Autowired
    WriterMapper writerMapper;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createWriter(@RequestBody WriterDto writerDto) {
        writerService.saveWriter(writerDto);
    }

    @GetMapping(value = "/{writerId}")
    public WriterDto getWriter(@PathVariable Long writerId) throws SearchingException {
        return writerMapper.mapToWriterDto(writerService.getWriter(writerId).orElseThrow(SearchingException::new));
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<WriterDto> getWriterByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        return writerMapper.mapToWritersDto(writerService.getAllWritersByNameAndSurname(name, surname));
    }

    @GetMapping
    public List<WriterDto> getWriters() {
        return writerMapper.mapToWritersDto(writerService.getAllWriters());
    }

    @DeleteMapping(value = "/{writerId}")
    public void deleteWriter(@PathVariable Long writerId) {
        writerService.deleteWriter(writerId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteWriterByNameAndSurname(@PathVariable String name, @PathVariable String surname) {
        writerService.deleteWriterByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public WriterDto updateWriter(@RequestBody WriterDto writerDto) {
        return writerMapper.mapToWriterDto(writerService.updateWriter(writerDto));
    }*/
}

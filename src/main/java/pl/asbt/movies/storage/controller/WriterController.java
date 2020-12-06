package pl.asbt.movies.storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.asbt.movies.storage.dto.WriterDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.facade.WriterFacade;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/storage/writers")
public class WriterController {
    private final WriterFacade writerFacade;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public void createWriter(@Validated @RequestBody WriterDto writerDto) {
        writerFacade.createWriter(writerDto);
    }

    @GetMapping(value = "/{writerId}")
    public WriterDto getWriter(@Validated @PathVariable Long writerId) throws StorageException {
        return writerFacade.fetchWriter(writerId);
    }

    @GetMapping(value = "/{name}/{surname}")
    public List<WriterDto> getWriterByNameAndSurname(@Validated @PathVariable String name,
                                                     @Validated @PathVariable String surname) {
        return writerFacade.fetchWriterByNameAndSurname(name, surname);
    }

    @GetMapping
    public List<WriterDto> getWriters() {
        return writerFacade.fetchWriters();
    }

    @DeleteMapping(value = "/{writerId}")
    public void deleteWriter(@Validated @PathVariable Long writerId) {
        writerFacade.deleteWriter(writerId);
    }

    @DeleteMapping(value = "/{name}/{surname}")
    public void deleteWriterByNameAndSurname(@Validated @PathVariable String name,
                                             @Validated @PathVariable String surname) {
        writerFacade.deleteWriterByNameAndSurname(name, surname);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public WriterDto updateWriter(@Validated @RequestBody WriterDto writerDto) {
        return writerFacade.updateWriter(writerDto);
    }
}

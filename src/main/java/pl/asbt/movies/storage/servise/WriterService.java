package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.domain.Writer;
import pl.asbt.movies.storage.dto.WriterDto;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.WriterRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterService.class);
    private final WriterRepository writerRepository;


    public Writer saveWriter(final Writer writer) {
        return writerRepository.save(writer);
    }

    public Optional<Writer> getWriter(final Long id) {
        return writerRepository.findById(id);
    }

    public List<Writer> getAllWritersByNameAndSurname(final String firstname, final String surname) {
        return writerRepository.findByFirstnameAndAndSurname(firstname, surname);
    }

    public List<Writer> getAllWriters() {
        return writerRepository.findAll();
    }

    public void deleteWriter(final Long id) {
        writerRepository.deleteById(id);
    }

    public void deleteWriterByNameAndSurname(final String firstname, final String surname) {
        writerRepository.deleteByFirstnameAndAndSurname(firstname, surname);
    }

    public Writer updateWriter(final WriterDto writerDto) {
        Writer result = new Writer();
        Long writerId = writerDto.getId();
        try {
            Writer writer = getWriter(writerId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no writer with given id.")
                            .build()
            );
            writer.setFirstname(writerDto.getFirstname());
            writer.setSurname(writerDto.getSurname());
            return saveWriter(writer);
        } catch (Exception e) {
            LOGGER.error("Writer: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}

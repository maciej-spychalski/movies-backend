package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.domain.Writer;
import pl.asbt.movies.storage.domain.WriterDto;
import pl.asbt.movies.storage.repository.WriterRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterService.class);
    private WriterRepository writerRepository;

    @Autowired
    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

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
            Writer writer = getWriter(writerId).orElseThrow(SearchingException::new);
            writer.setFirstname(writerDto.getFirstname());
            writer.setSurname(writerDto.getSurname());
            return saveWriter(writer);
        } catch (Exception e) {
            LOGGER.error(SearchingException.ERR_NO_WRITER);
        }
        return result;
    }

}

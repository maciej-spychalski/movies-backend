package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.exception.WriterNotFoundException;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.domain.Writer;
import pl.asbt.movies.storage.domain.WriterDto;
import pl.asbt.movies.storage.mapper.WriterMapper;
import pl.asbt.movies.storage.repository.WriterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WriterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriterService.class);
    private WriterRepository writerRepository;
    private WriterMapper writerMapper;

    @Autowired
    public WriterService(WriterRepository writerRepository, WriterMapper writerMapper) {
        this.writerRepository = writerRepository;
        this.writerMapper = writerMapper;
    }

    public Writer createWriter(final WriterDto writerDto) {
        List<Movie> movies = new ArrayList<>();
        return writerRepository.save(writerMapper.mapToWriter(writerDto, movies));
    }

    public Optional<Writer> getWriter(final Long id) {
        return writerRepository.findById(id);
    }

    public Optional<Writer> getWriter(final String firstname, final String surname) {
        return writerRepository.findByFirstnameAndAndSurname(firstname, surname);
    }

    public List<Writer> getAllWriters() {
        return writerRepository.findAll();
    }

    public void deleteWriter(final Long id) {
        writerRepository.deleteById(id);
    }

    public void deleteWriter(final String firstname, final String surname) {
        writerRepository.deleteByFirstnameAndAndSurname(firstname, surname);
    }

    public void updateWriter(final WriterDto writerDto) {
        Long id = writerDto.getId();
        try {
            Writer writer = getWriter(id).orElseThrow(WriterNotFoundException::new);
            writer.setFirstname(writerDto.getFirstname());
            writer.setSurname(writer.getSurname());
            writerRepository.save(writer);
        } catch (Exception e) {
            LOGGER.error("There are no writer id = " + id);
        }
    }

}
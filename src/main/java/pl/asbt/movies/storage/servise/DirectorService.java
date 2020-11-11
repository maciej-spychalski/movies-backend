package pl.asbt.movies.storage.servise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.asbt.movies.exception.DirectorNotFoundException;
import pl.asbt.movies.storage.domain.Director;
import pl.asbt.movies.storage.domain.DirectorDto;
import pl.asbt.movies.storage.domain.Movie;
import pl.asbt.movies.storage.mapper.DirectorMapper;
import pl.asbt.movies.storage.repository.DirectorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectorService.class);
    private DirectorRepository directorRepository;
    private DirectorMapper directorMapper;

    @Autowired
    public DirectorService(DirectorRepository directorRepository, DirectorMapper directorMapper) {
        this.directorRepository = directorRepository;
        this.directorMapper = directorMapper;
    }

    public Director createDirector(final DirectorDto directorDto) {
        List<Movie> movies = new ArrayList<>();
        return directorRepository.save(directorMapper.mapToDirector(directorDto, movies));
    }

    public Optional<Director> getDirector(final Long id) {
        return directorRepository.findById(id);
    }

    public Optional<Director> getDirector(final String firstname, final String surname) {
        return directorRepository.findByFirstnameAndAndSurname(firstname, surname);
    }

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public void deleteDirector(final Long id) {
        directorRepository.deleteById(id);
    }

    public void deleteDirector(final String firstname, final String surname) {
        directorRepository.deleteByFirstnameAndSurname(firstname, surname);
    }

    public void updateDirector(final DirectorDto directorDto) {
        Long id = directorDto.getId();
        try {
            Director director = getDirector(id).orElseThrow(DirectorNotFoundException::new);
            director.setFirstname(directorDto.getFirstname());
            director.setSurname(directorDto.getSurname());
            directorRepository.save(director);
        } catch (Exception e) {
            LOGGER.error("There are no director id = " + id);
        }
    }

}

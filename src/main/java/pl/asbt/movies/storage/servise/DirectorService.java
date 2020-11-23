package pl.asbt.movies.storage.servise;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.asbt.movies.storage.domain.Director;
import pl.asbt.movies.storage.dto.DirectorDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.repository.DirectorRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DirectorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectorService.class);
    private final DirectorRepository directorRepository;

    public Director saveDirector(final Director director) {
        return directorRepository.save(director);
    }

    public Optional<Director> getDirector(final Long id) {
        return directorRepository.findById(id);
    }

    public List<Director> getAllDirectorsByNameAndSurname(final String firstname, final String surname) {
        return directorRepository.findByFirstnameAndAndSurname(firstname, surname);
    }

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public void deleteDirector(final Long id) {
        directorRepository.deleteById(id);
    }

    public void deleteDirectorByNameAndSurname(final String firstname, final String surname) {
        directorRepository.deleteByFirstnameAndSurname(firstname, surname);
    }

    public Director updateDirector(final DirectorDto directorDto) {
        Director result = new Director();
        Long directorId = directorDto.getId();
        try {
            Director director = getDirector(directorId).orElseThrow(() ->
                    StorageException.builder()
                            .errorType(ErrorType.NOT_FOUND)
                            .message("There are no director with given id.")
                            .build()
            );
            director.setFirstname(directorDto.getFirstname());
            director.setSurname(directorDto.getSurname());
            return directorRepository.save(director);
        } catch (Exception e) {
            LOGGER.error("Director: " + ErrorType.NOT_FOUND.name());
        }
        return result;
    }

}

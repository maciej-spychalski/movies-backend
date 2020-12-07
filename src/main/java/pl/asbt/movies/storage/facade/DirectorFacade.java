package pl.asbt.movies.storage.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.asbt.movies.storage.dto.DirectorDto;
import pl.asbt.movies.storage.exception.ErrorType;
import pl.asbt.movies.storage.exception.StorageException;
import pl.asbt.movies.storage.mapper.DirectorMapper;
import pl.asbt.movies.storage.servise.DirectorService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DirectorFacade {
    private final DirectorService directorService;
    private final DirectorMapper directorMapper;

    public DirectorDto createDirector(DirectorDto directorDto) {
        return directorMapper.mapToDirectorDto(directorService.saveDirector(directorMapper.mapToDirector(directorDto)));
    }

    public DirectorDto fetchDirector(Long directorId) throws StorageException {
        return directorMapper.mapToDirectorDto(directorService.getDirector(directorId).orElseThrow(() ->
                StorageException.builder()
                        .errorType(ErrorType.NOT_FOUND)
                        .message("There are no director with given id.")
                        .build()
        ));
    }

    public List<DirectorDto> fetchDirectorByNameAndSurname(String name, String surname) {
        return directorMapper.mapToDirectorsDto(directorService.getAllDirectorsByNameAndSurname(name, surname));
    }

    public List<DirectorDto> fetchDirectors() {
        return directorMapper.mapToDirectorsDto(directorService.getAllDirectors());
    }

    public void deleteDirector(Long directorId) {
        directorService.deleteDirector(directorId);
    }

    public void deleteDirectorByNameAndSurname(String name, String surname) {
        directorService.deleteDirectorByNameAndSurname(name, surname);
    }

    public DirectorDto updateDirector(DirectorDto directorDto) {
        return directorMapper.mapToDirectorDto(directorService.updateDirector(directorDto));
    }
}

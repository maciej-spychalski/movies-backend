package pl.asbt.movies.storage.servise;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.Director;
import pl.asbt.movies.storage.domain.DirectorDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectorServiceTest {

    @Autowired
    DirectorService directorService;

    @Test
    public void createDirectorTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "Name1", "Surname1", movies);

        int directorQuantity = directorService.getAllDirectors().size();
        // When
        Director director1 = directorService.createDirector(directorDto);

        // Then
        List<Director> directors = directorService.getAllDirectors();
        long director1Id = director1.getId();
        assertEquals(directorQuantity + 1, directors.size());

        // CleanUp
        directorService.deleteDirector(director1Id);
    }

    @Test
    public void getDirectorTestSuit() {
        // Given
        List<String> movies = new ArrayList<>();
        DirectorDto directorDto1 = new DirectorDto(1L, "Name1", "Surname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        // When
        Director directorDB1 = directorService.getDirector(director1ID).orElse(new Director());

        // Then
        assertEquals("Name1", directorDB1.getFirstname());
        assertEquals("Surname1", directorDB1.getSurname());

        //CleanUp
        directorService.deleteDirector(director1ID);
    }

    @Test
    public void getDirectorByNameAndSurnameTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        DirectorDto directorDto1 = new DirectorDto(1L, "Name1", "Surname1", movies);
        DirectorDto directorDto2 = new DirectorDto(2L, "Name2", "Surname2", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();
        Director director2 = directorService.createDirector(directorDto2);
        Long director2ID = director2.getId();

        // When
        List<Director> directors;
        directors = directorService.getAllDirectorsByNameAndSurname(director2.getFirstname(), director2.getSurname());

        // Then
        assertEquals("Name2", directors.get(0).getFirstname());
        assertEquals("Surname2", directors.get(0).getSurname());

        // CleanUp
        directorService.deleteDirector(director1ID);
        directorService.deleteDirector(director2ID);
    }

    @Test
    public void getAllDirectorsTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        DirectorDto directorDto1 = new DirectorDto(1L, "Name1", "Surname1", movies);
        DirectorDto directorDto2 = new DirectorDto(2L, "Name2", "Surname2", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();
        Director director2 = directorService.createDirector(directorDto2);
        Long director2ID = director2.getId();

        // When
        List<Director> directors;
        directors = directorService.getAllDirectors();

        // Then
        assertTrue(directors.size() > 1);

        // CleanUp
        directorService.deleteDirector(director1ID);
        directorService.deleteDirector(director2ID);
    }

    @Test
    public void deleteDirectorTestSuit() {
        // Given
        List<String> movies = new ArrayList<>();
        DirectorDto directorDto1 = new DirectorDto(1L, "Name1", "Surname1", movies);
        DirectorDto directorDto2 = new DirectorDto(2L, "Name2", "Surname2", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();
        Director director2 = directorService.createDirector(directorDto2);
        Long director2ID = director2.getId();
        int directorsNumber = directorService.getAllDirectors().size();

        // When
        directorService.deleteDirector(director1ID);

        // Then
        assertEquals(directorsNumber - 1, directorService.getAllDirectors().size());

        // CleanUp
        directorService.deleteDirector(director2ID);
    }

    @Test
    public void deleteDirectorsByNameAndSurnameTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        DirectorDto directorDto1 = new DirectorDto(1L, "Name1", "Surname1", movies);
        DirectorDto directorDto2 = new DirectorDto(2L, "Name2", "Surname2", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();
        Director director2 = directorService.createDirector(directorDto2);
        Long director2ID = director2.getId();
        int directorsNumber = directorService.getAllDirectors().size();

        // When
        directorService.deleteDirectorByNameAndSurname(director2.getFirstname(), director2.getSurname());

        // Then
        assertEquals(directorsNumber - 1, directorService.getAllDirectors().size());

        // CleanUp
        directorService.deleteDirector(director1ID);
    }

    @Test
    public void updateDirectorTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        DirectorDto directorDto1 = new DirectorDto(1L, "Name1", "Surname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();
        DirectorDto directorDto2 = new DirectorDto(director1ID, "Name2", "Surname2", movies);

        // When
        directorService.updateDirector(directorDto2);

        // Then
        Director director2 = directorService.getDirector(director1ID).orElse(new Director());
        assertEquals("Name2", director2.getFirstname());
        assertEquals("Surname2", director2.getSurname());

        // CleanUp
        directorService.deleteDirector(director1ID);
    }

}

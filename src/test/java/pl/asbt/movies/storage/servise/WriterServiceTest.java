package pl.asbt.movies.storage.servise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.Writer;
import pl.asbt.movies.storage.domain.WriterDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WriterServiceTest {

    @Autowired
    WriterService writerService;

    @Test
    public void createWriterTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        WriterDto writerDto = new WriterDto(1L, "Name1", "Surname1", movies);

        int writerQuantity = writerService.getAllWriters().size();
        // When
        Writer writer1 = writerService.createWriter(writerDto);

        // Then
        List<Writer> writers = writerService.getAllWriters();
        long writer1Id = writer1.getId();
        assertEquals(writerQuantity + 1, writers.size());

        // CleanUp
        writerService.deleteWriter(writer1Id);
    }

    @Test
    public void getWriterTestSuit() {
        // Given
        List<String> movies = new ArrayList<>();
        WriterDto writerDto1 = new WriterDto(1L, "Name1", "Surname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1ID = writer1.getId();

        // When
        Writer writerDB1 = writerService.getWriter(writer1ID).orElse(new Writer());

        // Then
        assertEquals("Name1", writerDB1.getFirstname());
        assertEquals("Surname1", writerDB1.getSurname());

        //CleanUp
        writerService.deleteWriter(writer1ID);
    }

    @Test
    public void getWriterByNameAndSurnameTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        WriterDto writerDto1 = new WriterDto(1L, "Name1", "Surname1", movies);
        WriterDto writerDto2 = new WriterDto(2L, "Name2", "Surname2", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1ID = writer1.getId();
        Writer writer2 = writerService.createWriter(writerDto2);
        Long writer2ID = writer2.getId();

        // When
        List<Writer> writers;
        writers = writerService.getAllWritersByNameAndSurname(writer2.getFirstname(), writer2.getSurname());

        // Then
        assertEquals("Name2", writers.get(0).getFirstname());
        assertEquals("Surname2", writers.get(0).getSurname());

        // CleanUp
        writerService.deleteWriter(writer1ID);
        writerService.deleteWriter(writer2ID);
    }

    @Test
    public void getAllWritersTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        WriterDto writerDto1 = new WriterDto(1L, "Name1", "Surname1", movies);
        WriterDto writerDto2 = new WriterDto(2L, "Name2", "Surname2", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1ID = writer1.getId();
        Writer writer2 = writerService.createWriter(writerDto2);
        Long writer2ID = writer2.getId();

        // When
        List<Writer> writers;
        writers = writerService.getAllWriters();

        // Then
        assertTrue(writers.size() > 1);

        // CleanUp
        writerService.deleteWriter(writer1ID);
        writerService.deleteWriter(writer2ID);
    }

    @Test
    public void deleteWriterTestSuit() {
        // Given
        List<String> movies = new ArrayList<>();
        WriterDto writerDto1 = new WriterDto(1L, "Name1", "Surname1", movies);
        WriterDto writerDto2 = new WriterDto(2L, "Name2", "Surname2", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1ID = writer1.getId();
        Writer writer2 = writerService.createWriter(writerDto2);
        Long writer2ID = writer2.getId();
        int writersNumber = writerService.getAllWriters().size();

        // When
        writerService.deleteWriter(writer1ID);

        // Then
        assertEquals(writersNumber - 1, writerService.getAllWriters().size());

        // CleanUp
        writerService.deleteWriter(writer2ID);
    }

    @Test
    public void deleteWritersByNameAndSurnameTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        WriterDto writerDto1 = new WriterDto(1L, "Name1", "Surname1", movies);
        WriterDto writerDto2 = new WriterDto(2L, "Name2", "Surname2", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1ID = writer1.getId();
        Writer writer2 = writerService.createWriter(writerDto2);
        Long writer2ID = writer2.getId();
        int writersNumber = writerService.getAllWriters().size();

        // When
        writerService.deleteWriterByNameAndSurname(writer2.getFirstname(), writer2.getSurname());

        // Then
        assertEquals(writersNumber - 1, writerService.getAllWriters().size());

        // CleanUp
        writerService.deleteWriter(writer1ID);
    }

    @Test
    public void updateWriterTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        WriterDto writerDto1 = new WriterDto(1L, "Name1", "Surname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1ID = writer1.getId();
        WriterDto writerDto2 = new WriterDto(writer1ID, "Name2", "Surname2", movies);

        // When
        writerService.updateWriter(writerDto2);

        // Then
        Writer writer2 = writerService.getWriter(writer1ID).orElse(new Writer());
        assertEquals("Name2", writer2.getFirstname());
        assertEquals("Surname2", writer2.getSurname());

        // CleanUp
        writerService.deleteWriter(writer1ID);
    }
}

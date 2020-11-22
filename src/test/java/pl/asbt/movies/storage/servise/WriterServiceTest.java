package pl.asbt.movies.storage.servise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.Writer;
import pl.asbt.movies.storage.dto.WriterDto;

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
        Writer writer1 = new Writer("Name1", "Surname1");
        int writerQuantity = writerService.getAllWriters().size();

        // When
        writer1 = writerService.saveWriter(writer1);

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
        Writer writer1 = new Writer( "Name1", "Surname1");
        writer1 = writerService.saveWriter(writer1);
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
        Writer writer1 = new Writer( "Name1", "Surname1");
        Writer writer2 = new Writer( "Name2", "Surname2");
        writer1 = writerService.saveWriter(writer1);
        Long writer1ID = writer1.getId();
        writer2 = writerService.saveWriter(writer2);
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
        Writer writer1 = new Writer( "Name1", "Surname1");
        Writer writer2 = new Writer( "Name2", "Surname2");
        writer1 = writerService.saveWriter(writer1);
        Long writer1ID = writer1.getId();
        writer2 = writerService.saveWriter(writer2);
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
        Writer writer1 = new Writer( "Name1", "Surname1");
        Writer writer2 = new Writer( "Name2", "Surname2");
        writer1 = writerService.saveWriter(writer1);
        Long writer1ID = writer1.getId();
        writer2 = writerService.saveWriter(writer2);
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
        Writer writer1 = new Writer( "Name1", "Surname1");
        Writer writer2 = new Writer( "Name2", "Surname2");
        writer1 = writerService.saveWriter(writer1);
        Long writer1ID = writer1.getId();
        writer2 = writerService.saveWriter(writer2);
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
        Writer writer1 = new Writer( "Name1", "Surname1");
        writer1 = writerService.saveWriter(writer1);
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

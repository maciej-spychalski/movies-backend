package pl.asbt.movies.storage.servise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.Genre;
import pl.asbt.movies.storage.domain.GenreDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenreServiceTest {

    @Autowired
    GenreService genreService;

    @Test
    public void createGenreTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        int genresQuantity = genreService.getAllGenres().size();

        // When
        Genre genre1 = genreService.createGenre(genreDto1);

        // Then
        List<Genre> genres = genreService.getAllGenres();
        Long genre1Id = genre1.getId();
        assertEquals(genresQuantity + 1, genres.size());

        // CleanUp
        genreService.deleteGenre(genre1Id);
    }

    @Test
    public void getGenreTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1Id = genre1.getId();

        // When
        Genre genreDB1 = genreService.getGenre(genre1Id).orElse(new Genre());

        // Then
        assertEquals("Comedy", genreDB1.getType());

        // CleanUp
        genreService.deleteGenre(genre1Id);
    }

    @Test
    public void getGenreByType() {
        // Given
        List<String> movies = new ArrayList<>();
        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        GenreDto genreDto2 = new GenreDto(2L, "Sci-fi", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1Id = genre1.getId();
        Genre genre2 = genreService.createGenre(genreDto2);
        Long genre2Id = genre2.getId();

        // When
        List<Genre> genres;
        genres = genreService.getAllGenresByType(genre2.getType());

        // Then
        assertEquals("Sci-fi", genres.get(0).getType());
        assertTrue(genres.size() > 0);

        // CleanUp
        genreService.deleteGenre(genre1Id);
        genreService.deleteGenre(genre2Id);
    }

    @Test
    public void getAllGenresTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        GenreDto genreDto2 = new GenreDto(2L, "Sci-fi", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1Id = genre1.getId();
        Genre genre2 = genreService.createGenre(genreDto2);
        Long genre2Id = genre2.getId();

        // When
        List<Genre> genres = genreService.getAllGenres();
        genres = genreService.getAllGenres();

        // Then
        assertTrue(genres.size() > 1);

        // CleanUp
        genreService.deleteGenre(genre1Id);
        genreService.deleteGenre(genre2Id);
    }

    @Test
    public void deleteGenreTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        GenreDto genreDto2 = new GenreDto(2L, "Sci-fi", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1Id = genre1.getId();
        Genre genre2 = genreService.createGenre(genreDto2);
        Long genre2Id = genre2.getId();
        int genresQuantity = genreService.getAllGenres().size();

        // When
        genreService.deleteGenre(genre1Id);

        // Then
        assertEquals(genresQuantity - 1, genreService.getAllGenres().size());

        // CleanUp
        genreService.deleteGenre(genre2Id);
    }

    @Test
    public void deleteGenresByType() {
        // Given
        List<String> movies = new ArrayList<>();
        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        GenreDto genreDto2 = new GenreDto(2L, "Sci-fi", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1Id = genre1.getId();
        Genre genre2 = genreService.createGenre(genreDto2);
        int genresQuantity = genreService.getAllGenres().size();

        // When
        genreService.deleteGenreByType(genre2.getType());

        // Then
        assertEquals(genresQuantity - 1, genreService.getAllGenres().size());

        // CleanUp
        genreService.deleteGenre(genre1Id);
    }

    @Test
    public void updateGenreTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();
        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1Id = genre1.getId();
        GenreDto genreDto2 = new GenreDto(genre1Id, "Sci-fi", movies);

        // When
        genreService.updateGenre(genreDto2);

        // Then
        Genre genre2 = genreService.getGenre(genre1Id).orElse(new Genre());
        assertEquals("Sci-fi", genre2.getType());

        // CleanUp
        genreService.deleteGenre(genre1Id);

    }
}

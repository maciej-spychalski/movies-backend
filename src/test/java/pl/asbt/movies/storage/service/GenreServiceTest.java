package pl.asbt.movies.storage.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.Genre;
import pl.asbt.movies.storage.dto.GenreDto;

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
    public void saveGenreTestSuite() {
        // Given
        Genre genre1 = new Genre( "Comedy");
        int genresQuantity = genreService.getAllGenres().size();

        // When
        genre1 = genreService.saveGenre(genre1);

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
        Genre genre1 = new Genre( "Comedy");
        genre1 = genreService.saveGenre(genre1);
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
        Genre genre1 = new Genre( "Comedy");
        Genre genre2 = new Genre( "Sci-fi");
        genre1 = genreService.saveGenre(genre1);
        Long genre1Id = genre1.getId();
        genre2 = genreService.saveGenre(genre2);
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
        Genre genre1 = new Genre("Comedy");
        Genre genre2 = new Genre( "Sci-fi");
        genre1 = genreService.saveGenre(genre1);
        Long genre1Id = genre1.getId();
        genre2 = genreService.saveGenre(genre2);
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
        Genre genre1 = new Genre( "Comedy");
        Genre genre2 = new Genre( "Sci-fi");
        genre1 = genreService.saveGenre(genre1);
        Long genre1Id = genre1.getId();
        genre2 = genreService.saveGenre(genre2);
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
        Genre genre1 = new Genre( "Comedy");
        Genre genre2 = new Genre( "Sci-fi");
        genre1 = genreService.saveGenre(genre1);
        Long genre1Id = genre1.getId();
        genre2 = genreService.saveGenre(genre2);
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
        Genre genre1 = new Genre( "Comedy");
        genre1 = genreService.saveGenre(genre1);
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

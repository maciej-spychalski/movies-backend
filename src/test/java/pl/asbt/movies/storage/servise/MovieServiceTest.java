package pl.asbt.movies.storage.servise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.dto.*;
import pl.asbt.movies.storage.exception.StorageException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieServiceTest {

    @Autowired
    DirectorService directorService;

    @Autowired
    WriterService writerService;

    @Autowired
    ActorService actorService;

    @Autowired
    GenreService genreService;

    @Autowired
    MovieService movieService;

    private Movie movie1;
    private Movie movie2;
    private Director director1;
    private Director director2;
    private Director director3;
    private Writer writer1;
    private Writer writer2;
    private Writer writer3;
    private Actor actor1;
    private Actor actor2;
    private Actor actor3;
    private Genre genre1;
    private Genre genre2;
    private Genre genre3;

    @Before
    public void createData() {
        director1 = new Director("DirectorName11", "DirectorSurname11");
        director2 = new Director("DirectorName12", "DirectorSurname12");
        director3 = new Director("DirectorName13", "DirectorSurname13");
        writer1 = new Writer("WriterName11", "WriterSurname11");
        writer2 = new Writer("WriterName12", "WriterSurname12");
        writer3 = new Writer("WriterName13", "WriterSurname13");
        actor1 = new Actor("ActorName11", "ActorSurname11");
        actor2 = new Actor("ActorName12", "ActorSurname12");
        actor3 = new Actor("ActorName13", "ActorSurname13");
        genre1 = new Genre("Type11");
        genre2 = new Genre("Type12");
        genre3 = new Genre("Type13");

        movie1 = new Movie.MovieBuilder()
                .title("Title11")
                .director(director1)
                .writer(writer1)
                .actor(actor1)
                .genre(genre1)
                .duration(90)
                .price(new BigDecimal(10.00))
                .build();

        director1.getMovies().add(movie1);
        writer1.getMovies().add(movie1);
        actor1.getMovies().add(movie1);
        genre1.getMovies().add(movie1);

        movie2 = new Movie.MovieBuilder()
                .title("Title12")
                .director(director2)
                .writer(writer2)
                .actor(actor2)
                .genre(genre2)
                .duration(110)
                .price(new BigDecimal(20.00))
                .build();

        director2.getMovies().add(movie2);
        writer2.getMovies().add(movie2);
        actor2.getMovies().add(movie2);
        genre2.getMovies().add(movie2);

        directorService.saveDirector(director3);
        writerService.saveWriter(writer3);
        actorService.saveActor(actor3);
        genreService.saveGenre(genre3);
    }

    @After
    public void removeDate() {
        Long director1Id = director1.getId();
        directorService.deleteDirector(director1Id);
        Long director2Id = director2.getId();
        directorService.deleteDirector(director2Id);
        Long director3Id = director3.getId();
        directorService.deleteDirector(director3Id);
        Long writer1Id = writer1.getId();
        writerService.deleteWriter(writer1Id);
        Long writer2Id = writer2.getId();
        writerService.deleteWriter(writer2Id);
        Long writer3Id = writer3.getId();
        writerService.deleteWriter(writer3Id);
        Long actor1Id = actor1.getId();
        actorService.deleteActor(actor1Id);
        Long actor2Id = actor2.getId();
        actorService.deleteActor(actor2Id);
        Long actor3Id = actor3.getId();
        actorService.deleteActor(actor3Id);
        Long genre1Id = genre1.getId();
        genreService.deleteGenre(genre1Id);
        Long genre2Id = genre2.getId();
        genreService.deleteGenre(genre2Id);
        Long genre3Id = genre3.getId();
        genreService.deleteGenre(genre3Id);
    }


    @Test
    public void createMovieTestSuite() {
        // Given
        int moviesQuantity = movieService.getAllMovies().size();

        // When
        movie1 = movieService.saveMovie(movie1);
        movie2 = movieService.saveMovie(movie2);

        // Then
        List<Movie> movies1 = movieService.getAllMovies();
        assertEquals(moviesQuantity + 2, movies1.size());

        // CleanUp
        Long movie1Id = movie1.getId();
        movieService.deleteMovie(movie1Id);
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
    }

    @Test
    public void getMovieTestSuite() {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movie2 = movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();

        // When
        Movie movieDB1 = movieService.getMovie(movie1Id).orElse(new Movie());

        // Then
        assertEquals("Title11", movieDB1.getTitle());
        assertEquals(90, (int) movieDB1.getDuration());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }

    @Test
    public void getMovieByTitleTestSuite() {
        // Given
        movie1 = movieService.saveMovie(movie1);
        movie2 = movieService.saveMovie(movie2);

        // When
        List<Movie> movieList = new ArrayList<>();
        movieList = movieService.getAllMoviesByTitle("Title12");

        // Then
        assertEquals("Title12", movieList.get(0).getTitle());
        assertEquals(110, (int) movieList.get(0).getDuration());

        // CleanUp
        Long movie1Id = movie1.getId();
        movieService.deleteMovie(movie1Id);
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
    }

    @Test
    public void getAllMoviesTestSuite() {
        // Given
        movie1 = movieService.saveMovie(movie1);
        movie2 = movieService.saveMovie(movie2);

        // When
        List<Movie> movieList = new ArrayList<>();
        movieList = movieService.getAllMovies();

        // Then
        assertTrue(movieList.size() > 1);

        // CleanUp
        Long movie1Id = movie1.getId();
        movieService.deleteMovie(movie1Id);
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
    }

    @Test
    public void deleteMovieTestSuite() {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movie2 = movieService.saveMovie(movie2);

        int moviesQuantity = movieService.getAllMovies().size();

        // When
        movieService.deleteMovie(movie1Id);
        List<Movie> movies = movieService.getAllMovies();

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        // CleanUp
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
    }

    @Test
    public void deleteMovieByTitleTestSuite() {
        // Given
        movie1 = movieService.saveMovie(movie1);
        movie2 = movieService.saveMovie(movie2);

        int moviesQuantity = movieService.getAllMovies().size();

        // When
        movieService.deleteMovieByTitle("Title11");

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        // CleanUp
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
    }

    @Test
    public void updateMovieTestSuite() {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movie2 = movieService.saveMovie(movie2);

        List<String> moviesTitle = new ArrayList<>();
        DirectorDto directorDto = new DirectorDto(1L, "Name13", "Surname13", moviesTitle);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        MovieDto movieDto2 = new MovieDto(movie1Id, "Title13", directorDto, writersDto, actorsDto,  genresDto, 95, new BigDecimal(15.00));

        // When
        movieService.updateMovie(movieDto2);

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals("Title13", theMovie.getTitle());
        assertEquals(95, (int) theMovie.getDuration());
        assertEquals(15.00, theMovie.getPrice().doubleValue(), 0.01);

        // CleanUp
        movieService.deleteMovie(movie1Id);
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
    }

    @Transactional
    @Test
    public void addDirectorTestSuite() throws StorageException {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();

        // When
        movieService.addDirector(movie1Id, director3.getId());

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals("DirectorName13", theMovie.getDirector().getFirstname());
        assertEquals("DirectorSurname13", theMovie.getDirector().getSurname());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }

    @Transactional
    @Test
    public void addWriterTestSuite() throws StorageException {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();
        int writersQuantity = movie1.getWriters().size();

        // When
        movieService.addWriter(movie1Id, writer3.getId());

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals(writersQuantity + 1, theMovie.getWriters().size());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }

    @Transactional
    @Test
    public void removeWriterTestSuite() throws StorageException {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movie1 = movieService.addWriter(movie1Id, writer3.getId());
        Long writerId = movie1.getWriters().get(0).getId();
        movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();
        int writersQuantity = movie1.getWriters().size();

        // When
        movieService.removeWriter(movie1Id, writerId);

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals(writersQuantity - 1, theMovie.getWriters().size());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }

    @Transactional
    @Test
    public void addActorTestSuite() throws StorageException {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();
        int actorsQuantity = movie1.getActors().size();

        // When
        movieService.addActor(movie1Id, actor3.getId());

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals(actorsQuantity + 1, theMovie.getActors().size());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }

    @Transactional
    @Test
    public void removeActorTestSuite() throws StorageException {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movie1 = movieService.addActor(movie1Id, actor3.getId());
        Long actorId = movie1.getActors().get(0).getId();
        movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();
        int actorsQuantity = movie1.getActors().size();

        // When
        movieService.removeActor(movie1Id, actorId);

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals(actorsQuantity - 1, theMovie.getActors().size());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }

    @Transactional
    @Test
    public void addGenreTestSuite() throws StorageException {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();
        int genresQuantity = movie1.getGenres().size();

        // When
        movieService.addGenre(movie1Id, genre3.getId());

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals(genresQuantity + 1, theMovie.getGenres().size());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }

    @Transactional
    @Test
    public void removeGenreTestSuite() throws StorageException {
        // Given
        movie1 = movieService.saveMovie(movie1);
        Long movie1Id = movie1.getId();
        movie1 = movieService.addGenre(movie1Id, genre3.getId());
        Long genreId = movie1.getGenres().get(0).getId();
        movieService.saveMovie(movie2);
        Long movie2Id = movie2.getId();
        int genresQuantity = movie1.getGenres().size();

        // When
        movieService.removeGenre(movie1Id, genreId);

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals(genresQuantity - 1, theMovie.getGenres().size());

        // CleanUp
        movieService.deleteMovie(movie1Id);
        movieService.deleteMovie(movie2Id);
    }


}
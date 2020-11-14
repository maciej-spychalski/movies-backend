package pl.asbt.movies.storage.servise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@Transactional
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

    @Autowired
    MovieRepository movieRepository;

    private DirectorDto directorDto;
    private List<WriterDto> writersDto = new ArrayList<>();
    private List<ActorDto> actorsDto = new ArrayList<>();
    private List<GenreDto> genresDto = new ArrayList<>();

    @Before
    public void createData() {
        List<String> movies = new ArrayList<>();

        directorDto = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director = directorService.createDirector(directorDto);
        Long director1ID = director.getId();
        directorDto = new DirectorDto(director1ID, "DirectorName1", "DirectorSurname1", movies);

        WriterDto writerDto = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer = writerService.createWriter(writerDto);
        Long writer1Id = writer.getId();
        writerDto = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        writersDto.add(writerDto);

        ActorDto actorDto = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor = actorService.createActor(actorDto);
        Long actor1ID = actor.getId();
        actorDto = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        actorsDto.add(actorDto);

        GenreDto genreDto = new GenreDto(1L, "Comedy", movies);
        Genre genre = genreService.createGenre(genreDto);
        Long genreID = genre.getId();
        genreDto = new GenreDto(genreID, "Comedy", movies);
        genresDto.add(genreDto);
    }

    @After
    public void deleteData() {
        directorService.deleteDirector(directorDto.getId());
        writerService.deleteWriter(writersDto.get(0).getId());
        actorService.deleteActor(actorsDto.get(0).getId());
        genreService.deleteGenre(genresDto.get(0).getId());
    }

    @Test
    public void createMovieTestSuite() {
        // Given
        int moviesQuantity = movieService.getAllMovies().size();

        // When
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);

        // Then
        List<Movie> movies1 = movieService.getAllMovies();
        Long movie1ID = movie1.getId();
        assertEquals(moviesQuantity + 1, movies1.size());

        //CleanUp
        movieService.deleteMovie(movie1ID);
    }

    @Test
    public void getMovieTestSuite() {
        // Given
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();

        // When
        Movie movieDB1 = movieService.getMovie(movie1ID).orElse(new Movie());

        // Then
        assertEquals(movieDto1.getTitle(), movieDB1.getTitle());
        assertEquals(movieDto1.getDuration(), movieDB1.getDuration());

        //CleanUp
        movieService.deleteMovie(movie1ID);
    }

    @Test
    public void getMovieByTitleTestSuite() {
        // Given
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto, writersDto, actorsDto, genresDto, 95);
        Movie movie2 = movieService.createMovie(movieDto2);
        Long movie2ID = movie2.getId();

        // When
        List<Movie> movieList = new ArrayList<>();
        movieList = movieService.getAllMoviesByTitle("Title2");

        // Then
        assertEquals(movieDto2.getTitle(), movieList.get(0).getTitle());
        assertEquals(movieDto2.getDuration(), movieList.get(0).getDuration());

        //CleanUp
        movieService.deleteMovie(movie1ID);
        movieService.deleteMovie(movie2ID);
    }

    @Test
    public void getAllMoviesTestSuite() {
        // Given
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto, writersDto, actorsDto, genresDto, 95);
        Movie movie2 = movieService.createMovie(movieDto2);
        Long movie2ID = movie2.getId();

        // When
        List<Movie> movieList = new ArrayList<>();
        movieList = movieService.getAllMovies();

        // Then
        assertTrue(movieList.size() > 1);

        //CleanUp
        movieService.deleteMovie(movie1ID);
        movieService.deleteMovie(movie2ID);
    }

    @Test
    public void deleteMovieTestSuite() {
        // Given
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto, writersDto, actorsDto, genresDto, 95);
        Movie movie2 = movieService.createMovie(movieDto2);
        Long movie2ID = movie2.getId();

        int moviesQuantity = movieService.getAllMovies().size();

        // When
        movieService.deleteMovie(movie1ID);

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        //CleanUp
        movieService.deleteMovie(movie2ID);
    }

    @Test
    public void deleteMovieByTitleTestSuite() {
        // Given
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto, writersDto, actorsDto, genresDto, 95);
        Movie movie2 = movieService.createMovie(movieDto2);
        Long movie2ID = movie2.getId();

        int moviesQuantity = movieService.getAllMovies().size();

        // When
        movieService.deleteMovieByTitle(movieDto1.getTitle());

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        //CleanUp
        movieService.deleteMovie(movie2ID);
    }

    @Test
    public void updateMovieTestSuite() {
        // Given
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(movie1ID, "Title2", directorDto, writersDto, actorsDto, genresDto, 95);

        // When
        movieService.updateMovie(movieDto2);

        // Then
        Movie theMovie = movieService.getMovie(movie1ID).orElse(new Movie());
        assertEquals(movieDto2.getTitle(), theMovie.getTitle());
        assertEquals(movieDto2.getDuration(), theMovie.getDuration());

        //CleanUp
        movieService.deleteMovie(movie1ID);
    }
}

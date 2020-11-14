package pl.asbt.movies.storage.servise;

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

    @Test
    public void createMovieTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        int moviesQuantity = movieService.getAllMovies().size();

        // When
        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);

        // Then
        List<Movie> movies1 = movieService.getAllMovies();
        Long movie1ID = movie1.getId();
        assertEquals(moviesQuantity + 1, movies1.size());

        //CleanUp
        movieService.deleteMovie(movie1ID);
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);
    }

    @Test
    public void getMovieTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();

        // When
        Movie movieDB1 = movieService.getMovie(movie1ID).orElse(new Movie());

        // Then
        assertEquals(movieDto1.getTitle(), movieDB1.getTitle());
        assertEquals(movieDto1.getDuration(), movieDB1.getDuration());

        //CleanUp
        movieService.deleteMovie(movie1ID);
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);
    }

    @Test
    public void getMovieByTitleTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto1, writersDto, actorsDto, genresDto, 95);
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
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);
    }

    @Test
    public void getAllMoviesTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto1, writersDto, actorsDto, genresDto, 95);
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
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);
    }

    @Test
    public void deleteMovieTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto1, writersDto, actorsDto, genresDto, 95);
        Movie movie2 = movieService.createMovie(movieDto2);
        Long movie2ID = movie2.getId();

        int moviesQuantity = movieService.getAllMovies().size();

        // When
        movieService.deleteMovie(movie1ID);

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        //CleanUp
        movieService.deleteMovie(movie2ID);
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);
    }

    @Test
    public void deleteMovieByTitleTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(2L, "Title2", directorDto1, writersDto, actorsDto, genresDto, 95);
        Movie movie2 = movieService.createMovie(movieDto2);
        Long movie2ID = movie2.getId();

        int moviesQuantity = movieService.getAllMovies().size();

        // When
        movieService.deleteMovieByTitle(movieDto1.getTitle());

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        //CleanUp
        movieService.deleteMovie(movie2ID);
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);
    }

    @Test
    public void updateMovieTestSuite() {
        // Given
        List<String> movies = new ArrayList<>();

        DirectorDto directorDto1 = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
        Director director1 = directorService.createDirector(directorDto1);
        Long director1ID = director1.getId();

        WriterDto writerDto1 = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
        Writer writer1 = writerService.createWriter(writerDto1);
        Long writer1Id = writer1.getId();
        writerDto1 = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto1);

        ActorDto actorDto1 = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
        Actor actor1 = actorService.createActor(actorDto1);
        Long actor1ID = actor1.getId();
        actorDto1 = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
        List<ActorDto> actorsDto =  new ArrayList<>();
        actorsDto.add(actorDto1);

        GenreDto genreDto1 = new GenreDto(1L, "Comedy", movies);
        Genre genre1 = genreService.createGenre(genreDto1);
        Long genre1ID = genre1.getId();
        genreDto1 = new GenreDto(genre1ID, "Comedy", movies);
        List<GenreDto> genresDto = new ArrayList<>();
        genresDto.add(genreDto1);

        MovieDto movieDto1 = new MovieDto(1L, "Title1", directorDto1, writersDto, actorsDto, genresDto, 90);
        Movie movie1 = movieService.createMovie(movieDto1);
        Long movie1ID = movie1.getId();
        MovieDto movieDto2 = new MovieDto(movie1ID, "Title2", directorDto1, writersDto, actorsDto, genresDto, 95);
        /*Movie movie2 = movieService.createMovie(movieDto2);
        Long movie2ID = movie2.getId();*/

        // When
        movieService.updateMovie(movieDto2);

        // Then
        Movie theMovie = movieService.getMovie(movie1ID).orElse(new Movie());
        assertEquals(movieDto2.getTitle(), theMovie.getTitle());
        assertEquals(movieDto2.getDuration(), theMovie.getDuration());

        //CleanUp
        movieService.deleteMovie(movie1ID);
        directorService.deleteDirector(director1ID);
        writerService.deleteWriter(writer1Id);
        actorService.deleteActor(actor1ID);
        genreService.deleteGenre(genre1ID);

    }
}

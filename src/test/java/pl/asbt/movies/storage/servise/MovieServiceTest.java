package pl.asbt.movies.storage.servise;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;

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
    private Writer writer1;
    private Writer writer2;
    private Actor actor1;
    private Actor actor2;
    private Genre genre1;
    private Genre genre2;

    @Before
    public void createData() {
        director1 = new Director("DirectorName1", "DirectorSurname1");
        writer1 = new Writer("WriterName1", "WriterSurname1");
        writer2 = new Writer("WriterName2", "WriterSurname3");
        actor1 = new Actor("ActorName1", "ActorSurname1");
        actor2 = new Actor("ActorName2", "ActorSurname2");
        genre1 = new Genre("Comedy");
        genre2 = new Genre("Sci-fi");

        movie1 = new Movie.MovieBuilder()
                .title("Title1")
                .director(director1)
                .writer(writer1)
                .writer(writer2)
                .actor(actor1)
                .actor(actor2)
                .genre(genre1)
                .genre(genre2)
                .duration(90)
                .build();

        movie2 = new Movie.MovieBuilder()
                .title("Title2")
                .director(director1)
                .writer(writer1)
                .writer(writer2)
                .actor(actor1)
                .actor(actor2)
                .genre(genre1)
                .genre(genre2)
                .duration(110)
                .build();

        /*director1.getMovies().add(movie1);
        writer1.getMovies().add(movie1);
        writer2.getMovies().add(movie1);
        actor1.getMovies().add(movie1);
        actor2.getMovies().add(movie1);
        genre1.getMovies().add(movie1);
        genre2.getMovies().add(movie1);

        director1.getMovies().add(movie2);
        writer1.getMovies().add(movie2);
        writer2.getMovies().add(movie2);
        actor1.getMovies().add(movie2);
        actor2.getMovies().add(movie2);
        genre1.getMovies().add(movie2);
        genre2.getMovies().add(movie2);*/
    }

    @After
    public void removeDate() {
        Long director1Id = director1.getId();
        directorService.deleteDirector(director1Id);
        Long writer1Id = writer1.getId();
        writerService.deleteWriter(writer1Id);
        Long writer2Id = writer2.getId();
        writerService.deleteWriter(writer2Id);
        Long actor1Id = actor1.getId();
        actorService.deleteActor(actor1Id);
        Long actor2Id = actor2.getId();
        actorService.deleteActor(actor2Id);
        Long genre1Id = genre1.getId();
        genreService.deleteGenre(genre1Id);
        Long genre2Id = genre2.getId();
        genreService.deleteGenre(genre2Id);
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

        //CleanUp
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
        assertEquals("Title1", movieDB1.getTitle());
        assertEquals(90, (int) movieDB1.getDuration());

        //CleanUp
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
        movieList = movieService.getAllMoviesByTitle("Title2");

        // Then
        assertEquals("Title2", movieList.get(0).getTitle());
        assertEquals(110, (int) movieList.get(0).getDuration());

        //CleanUp
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

        //CleanUp
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

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        //CleanUp
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
        movieService.deleteMovieByTitle("Title1");

        // Then
        assertEquals(moviesQuantity -1, movieService.getAllMovies().size());

        //CleanUp
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
        DirectorDto directorDto = new DirectorDto(1L, "Name3", "Surname3", moviesTitle);
        List<WriterDto> writersDto = new ArrayList<>();
        List<ActorDto> actorsDto = new ArrayList<>();
        List<GenreDto> genresDto = new ArrayList<>();
        MovieDto movieDto2 = new MovieDto(movie1Id, "Title3", directorDto, writersDto, actorsDto,  genresDto, 95);

        // When
        movieService.updateMovie(movieDto2);

        // Then
        Movie theMovie = movieService.getMovie(movie1Id).orElse(new Movie());
        assertEquals("Title3", theMovie.getTitle());
        assertEquals(95, (int) theMovie.getDuration());

        //CleanUp
        movieService.deleteMovie(movie1Id);
        Long movie2Id = movie2.getId();
        movieService.deleteMovie(movie2Id);
    }

}


//        ////////////////////////////////////////////////////
//        System.out.println();
//        System.out.println();
//        List<Movie> movies = movieService.getAllMovies();
//        for(Movie theMovie : movies) {
//            System.out.println("Movie Id: " + theMovie.getId());
//            System.out.println("    Movie Title: " + theMovie.getTitle());
//            System.out.println("    Director Id: " + theMovie.getDirector().getId());
//            System.out.print("      Movie Director: " + theMovie.getDirector().getFirstname());
//            System.out.println(" " + theMovie.getDirector().getSurname());
//
//            List<Writer> writers = theMovie.getWriters();
//            System.out.println("    Writers:");
//            for(Writer theWriter : writers) {
//                System.out.println("        Writer Id: " + theWriter.getId());
//                System.out.print("          Writer: " + theWriter.getFirstname());
//                System.out.println(" " + theWriter.getSurname());
//                List<Movie> writerMovies = theWriter.getMovies();
//                System.out.print("              Movies: ");
//                for(Movie movie : writerMovies) {
//                    System.out.print(movie.getTitle() + ", ");
//                }
//                System.out.println();
//            }
//
//            List<Actor> actors = theMovie.getActors();
//            System.out.println("    Actors:");
//            for(Actor theActor : actors) {
//                System.out.println("        Actor Id: " + theActor.getId());
//                System.out.print("          Actor: " + theActor.getFirstname());
//                System.out.println(" " + theActor.getSurname());
//                List<Movie> actorMovies = theActor.getMovies();
//                System.out.print("              Movies: ");
//                for(Movie movie : actorMovies) {
//                    System.out.print(movie.getTitle() + ", ");
//                }
//                System.out.println();
//            }
//
//            List<Genre> genres = theMovie.getGenres();
//            System.out.println("    Genres:");
//            for(Genre theGenre : genres) {
//                System.out.println("        Genre Id: " + theGenre.getId());
//                System.out.println("          Genre: " + theGenre.getType());
//                List<Movie> genreMovies = theGenre.getMovies();
//                System.out.print("              Movies: ");
//                for(Movie movie : genreMovies) {
//                    System.out.print(movie.getTitle() + ", ");
//                }
//                System.out.println();
//            }
//            System.out.println("    Duration: " + theMovie.getDuration());
//            System.out.println();
//        }
//        System.out.println();
//        System.out.println();
//        ////////////////////////////////////////////////////
package pl.asbt.movies.storage.servise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.exception.StorageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MakeCollectionTest {

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
    StorageItemService storageItemService;

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Test
    public void makeCollectionTest() throws StorageException {
        // Create Actors
        List<Actor> actors = new ArrayList<Actor>();
        for(int i = 1; i < 11; i++) {
            Actor actor = new Actor("ActorName" + i, "ActorSurname" + i);
            actor = actorService.saveActor(actor);
            actors.add(actor);
        };
        // Create Directors
        List<Director> directors = new ArrayList<Director>();
        for(int i = 1; i < 11; i++) {
            Director director = new Director("DirectorName" + i, "DirectorSurname" + i);
            director = directorService.saveDirector(director);
            directors.add(director);
        }
        // Create Writers
        List<Writer> writers = new ArrayList<Writer>();
        for(int i = 1; i < 11; i++) {
            Writer writer = new Writer("WriterName" + i, "WriterSurname" + i);
            writer = writerService.saveWriter(writer);
            writers.add(writer);
        }
        // Create Genres
        List<Genre> generes = new ArrayList<>();
        for(int i = 1; i < 11; i++) {
            Genre genre = new Genre("GenreType" + i);
            genre = genreService.saveGenre(genre);
            generes.add(genre);
        }

//        Random generator = new Random();
//        Movie movie;
//        for(int i = 1; i < 11; i++) {
//            movie = new Movie("MovieTitle" + i, generator.nextInt(210) + 30);
//            movie = movieService.saveMovie(movie);
//            Long movieId = movie.getId();
//
//            int directorNumber = generator.nextInt(10);
//            Long directorId = directors.get(directorNumber).getId();
//            movieService.addDirector(movieId, directorId);
//
//            int startWriter = generator.nextInt(7);
//            for(int j = startWriter; j < startWriter + 4; j++) {
//                movieService.addWriter(movieId, writers.get(j).getId());
//            }
//
//            int startActor = generator.nextInt(7);
//            for(int j = startActor; j < startActor + 4; j++) {
//                movieService.addActor(movieId, actors.get(j).getId());
//            }
//
//            int startGenre = generator.nextInt(7);
//            for(int j = startGenre; j < startGenre + 4; j++) {
//                movieService.addGenre(movieId, generes.get(j).getId());
//            }
//
//        }
    }
}

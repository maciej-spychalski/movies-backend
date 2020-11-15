package pl.asbt.movies.storage.servise;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.*;
import pl.asbt.movies.storage.exception.SearchingException;
import pl.asbt.movies.storage.repository.StorageItemRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageItemServiceTest {
//
////    @InjectMocks
////    private StorageItemService storageItemService;
////
////    @Mock
////    private MovieService movieService;
//
//    @Autowired
//    StorageItemService storageItemService;
//
//    @Autowired
//    StorageItemRepository storageItemRepository;
//
//    @Autowired
//    DirectorService directorService;
//
//    @Autowired
//    WriterService writerService;
//
//    @Autowired
//    ActorService actorService;
//
//    @Autowired
//    GenreService genreService;
//
//    @Autowired
//    MovieService movieService;
//
//    private DirectorDto directorDto;
//    private List<WriterDto> writersDto = new ArrayList<>();
//    private List<ActorDto> actorsDto = new ArrayList<>();
//    private List<GenreDto> genresDto = new ArrayList<>();
//    private MovieDto movieDto1;
//    private MovieDto movieDto2;
//
//
//    @Before
//    public void createData() {
//        List<String> movies = new ArrayList<>();
//
//        directorDto = new DirectorDto(1L, "DirectorName1", "DirectorSurname1", movies);
//        Director director = directorService.saveDirector(directorDto);
//        Long director1ID = director.getId();
//        directorDto = new DirectorDto(director1ID, "DirectorName1", "DirectorSurname1", movies);
//
//        WriterDto writerDto = new WriterDto(1L, "WriterName1", "WriterSurname1", movies);
//        Writer writer = writerService.saveWriter(writerDto);
//        Long writer1Id = writer.getId();
//        writerDto = new WriterDto(writer1Id, "WriterName1", "WriterSurname1", movies);
//        writersDto.add(writerDto);
//
//        ActorDto actorDto = new ActorDto(1L, "ActorName1", "ActorSurname1", movies);
//        Actor actor = actorService.saveActor(actorDto);
//        Long actor1ID = actor.getId();
//        actorDto = new ActorDto(actor1ID, "ActorName1", "ActorSurname1", movies);
//        actorsDto.add(actorDto);
//
//        GenreDto genreDto = new GenreDto(1L, "Comedy", movies);
//        Genre genre = genreService.saveGenre(genreDto);
//        Long genreID = genre.getId();
//        genreDto = new GenreDto(genreID, "Comedy", movies);
//        genresDto.add(genreDto);
//
//        movieDto1 = new MovieDto(1L, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
//        Movie movie1 = movieService.saveMovie(movieDto1);
//        Long movie1ID = movie1.getId();
//        movieDto1 = new MovieDto(movie1ID, "Title1", directorDto, writersDto, actorsDto, genresDto, 90);
//
//        movieDto2 = new MovieDto(2L, "Title2", directorDto, writersDto, actorsDto, genresDto, 95);
//        Movie movie2 = movieService.saveMovie(movieDto2);
//        Long movie2ID = movie2.getId();
//        movieDto2 = new MovieDto(movie2ID, "Title2", directorDto, writersDto, actorsDto, genresDto, 95);
//    }
//
//    /*@After
//    public void deleteData() {
//        movieService.deleteMovie(movieDto1.getId());
//        movieService.deleteMovie(movieDto2.getId());
//        directorService.deleteDirector(directorDto.getId());
//        writerService.deleteWriter(writersDto.get(0).getId());
//        actorService.deleteActor(actorsDto.get(0).getId());
//        genreService.deleteGenre(genresDto.get(0).getId());
//    }*/
//
//    @Test
//    public void createStorageItemTestSuite() throws SearchingException {
//        // Given
//        int storageItemQuantity = storageItemService.getAllStorageItems().size();
//
//        // When
//        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movieDto1.getId(),2);
//        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);
//        Long storageItem1ID = storageItem1.getId();
//
//        // Then
//        List<StorageItem> storageItems = storageItemService.getAllStorageItems();
//        assertEquals(storageItemQuantity + 1, storageItems.size());
//
//        //CleanUp
//        storageItemService.deleteStorageItem(storageItem1ID);
//    }
//
//    @Test
//    public void getStorageItemTestSuite() throws SearchingException {
//        // Given
//        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movieDto1.getId(),2);
//        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);
//        Long storageItem1ID = storageItem1.getId();
//
//        // When
//        StorageItem storageItemDB1 = storageItemService.getStorageItem(storageItem1ID).orElse(new StorageItem());
//
//        // Then
//        assertEquals(storageItemDto1.getQuantity(), storageItemDB1.getQuantity());
//        assertEquals(movieDto1.getTitle(), storageItemDB1.getMovie().getTitle());
//
//        //CleanUp
//        storageItemService.deleteStorageItem(storageItem1ID);
//    }
//
//    @Test
//    public void getStorageItemByMovieTitle() throws SearchingException {
//        // Given
//        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movieDto1.getId(),2);
//        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);
//        Long storageItem1ID = storageItem1.getId();
//
//        StorageItemDto storageItemDto2 = new StorageItemDto(2L, movieDto2.getTitle(), movieDto2.getId(),4);
//        StorageItem storageItem2 = storageItemService.createStorageItem(storageItemDto2);
//        Long storageItem2ID = storageItem2.getId();
//
//        // When
//        List<StorageItem> storageItems = storageItemService.getStorageItemsByMovieTitle(movieDto2.getTitle());
//
//        // Then
//        assertEquals(storageItemDto2.getQuantity(), storageItems.get(0).getQuantity());
//        assertEquals(movieDto2.getTitle(), storageItems.get(0).getMovie().getTitle());
//
//        //CleanUp
//        /*storageItemService.deleteStorageItem(storageItem1ID);
//        storageItemService.deleteStorageItem(storageItem2ID)*/;
//    }
//
//    @Test
//    public void getAllStorageItemsTestSuite() throws SearchingException {
//        // Given
//        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movieDto1.getId(),2);
//        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);
//        Long storageItem1ID = storageItem1.getId();
//
//        StorageItemDto storageItemDto2 = new StorageItemDto(2L, movieDto2.getTitle(), movieDto2.getId(),4);
//        StorageItem storageItem2 = storageItemService.createStorageItem(storageItemDto2);
//        Long storageItem2ID = storageItem2.getId();
//
//        // When
//        List<StorageItem> storageItems = storageItemService.getAllStorageItems();
//
//        // Then
//        assertTrue(storageItems.size() > 1);
//
//        //CleanUp
//        storageItemService.deleteStorageItem(storageItem1ID);
//        storageItemService.deleteStorageItem(storageItem2ID);
//    }
//
//    @Test
//    public void deleteStorageItem() throws SearchingException {
//        // Given
//        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movieDto1.getId(),2);
//        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);
//        Long storageItem1ID = storageItem1.getId();
//
//        StorageItemDto storageItemDto2 = new StorageItemDto(2L, movieDto2.getTitle(), movieDto2.getId(),4);
//        StorageItem storageItem2 = storageItemService.createStorageItem(storageItemDto2);
//        Long storageItem2ID = storageItem2.getId();
//
//        int storageItemsQuantity = storageItemService.getAllStorageItems().size();
//
//        // When
//        storageItemService.deleteStorageItem(storageItem1ID);
//
//        // Then
//        assertEquals(storageItemsQuantity - 1, storageItemService.getAllStorageItems().size());
//
//        //CleanUp
//        storageItemService.deleteStorageItem(storageItem2ID);
//    }
//
//    @Test
//    public void deleteStorageItemsByMovieTitle() throws SearchingException {
//        // Given
//        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movieDto1.getId(),2);
//        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);
//        Long storageItem1ID = storageItem1.getId();
//
//        StorageItemDto storageItemDto2 = new StorageItemDto(2L, movieDto2.getTitle(), movieDto2.getId(),4);
//        StorageItem storageItem2 = storageItemService.createStorageItem(storageItemDto2);
//        Long storageItem2ID = storageItem2.getId();
//
//        int storageItemsQuantity = storageItemService.getAllStorageItems().size();
//
//        // When
//        storageItemService.deleteStorageItemByMovieTitle(movieDto1.getTitle());
//
//        // Then
//        assertEquals(storageItemsQuantity - 1, storageItemService.getAllStorageItems().size());
//
//        //CleanUp
//        storageItemService.deleteStorageItem(storageItem2ID);
//    }
//
//    @Test
//    public void updateStorageItem() throws SearchingException {
//        // Given
//        StorageItemDto storageItemDto1 = new StorageItemDto(1L, movieDto1.getTitle(), movieDto1.getId(),2);
//        StorageItem storageItem1 = storageItemService.createStorageItem(storageItemDto1);
//        Long storageItem1ID = storageItem1.getId();
//        StorageItemDto storageItemDto2 = new StorageItemDto(storageItem1ID, movieDto2.getTitle(), movieDto2.getId(),4);
//
//        // When
//        storageItemService.updateStorageItem(storageItemDto2);
//
//        // Then
//        StorageItem theStorageItem = storageItemService.getStorageItem(storageItem1ID).orElse(new StorageItem());
//        assertEquals(storageItemDto2.getQuantity(), theStorageItem.getQuantity());
//        assertEquals(movieDto2.getTitle(), theStorageItem.getMovie().getTitle());
//
//        //CleanUp
//        storageItemService.deleteStorageItem(storageItem1ID);
//    }
}

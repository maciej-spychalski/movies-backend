package pl.asbt.movies.storage.servise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.asbt.movies.storage.domain.Actor;
import pl.asbt.movies.storage.dto.ActorDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorServiceTest {

    @Autowired
    ActorService actorService;

    @Test
    public void saveActorTestSuite() {
        // Given
        Actor actor1 = new Actor("Name1", "Surname1");
        int actorsQuantity = actorService.getAllActors().size();

        // When
        actor1 = actorService.saveActor(actor1);

        // Then
        List<Actor> actors = actorService.getAllActors();
        long actor1Id = actor1.getId();
        assertEquals(actorsQuantity + 1, actors.size());

        // CleanUp
        actorService.deleteActor(actor1Id);
    }

    @Test
    public void getActorTestSuite() {
        // Given
        Actor actor1 = new Actor( "Name1", "Surname1");
        actor1 = actorService.saveActor(actor1);
        Long actor1ID = actor1.getId();

        // When
        Actor actorDB1 = actorService.getActor(actor1ID).orElse(new Actor());

        // Then
        assertEquals("Name1", actorDB1.getFirstname());
        assertEquals("Surname1", actorDB1.getSurname());

        // CleanUp
        actorService.deleteActor(actor1ID);
    }

    @Test
    public void getActorsByNameAndSurnameTestSuite() {
        // Given
        Actor actor1 = new Actor( "Name1", "Surname1");
        Actor actor2 = new Actor("Name2", "Surname2");
        actor1 = actorService.saveActor(actor1);
        Long actor1ID = actor1.getId();
        actor2 = actorService.saveActor(actor2);
        Long actor2ID = actor2.getId();

        // When
        List<Actor> actors;
        actors = actorService.getActorsByNameAndSurname(actor2.getFirstname(), actor2.getSurname());

        // Then
        assertEquals("Name2", actors.get(0).getFirstname());
        assertEquals("Surname2", actors.get(0).getSurname());
        assertTrue(actors.size() > 0);

        // CleanUp
        actorService.deleteActor(actor1ID);
        actorService.deleteActor(actor2ID);
    }

    @Test
    public void getAllActorsTestSuite() {
        // Given
        Actor actor1 = new Actor("Name1", "Surname1");
        Actor actor2 = new Actor( "Name2", "Surname2");
        actor1 = actorService.saveActor(actor1);
        Long actor1ID = actor1.getId();
        actor2 = actorService.saveActor(actor2);
        Long actor2ID = actor2.getId();

        // When
        List<Actor> actors;
        actors = actorService.getAllActors();

        // Then
        assertTrue(actors.size() > 1);

        // CleanUp
        actorService.deleteActor(actor1ID);
        actorService.deleteActor(actor2ID);
    }

    @Test
    public void deleteActorTestSuite() {
        // Given
        Actor actor1 = new Actor( "Name1", "Surname1");
        Actor actor2 = new Actor( "Name2", "Surname2");
        actor1 = actorService.saveActor(actor1);
        Long actor1ID = actor1.getId();
        actor2 = actorService.saveActor(actor2);
        Long actor2ID = actor2.getId();
        int actorsNumber = actorService.getAllActors().size();

        // When
        actorService.deleteActor(actor1ID);

        // Then
        assertEquals(actorsNumber -1 , actorService.getAllActors().size());

        // CleanUp
        actorService.deleteActor(actor2ID);
    }

    @Test
    public void deleteActorsByNameAndSurnameTestSuite() {
        // Given
        Actor actor1 = new Actor( "Name1", "Surname1");
        Actor actor2 = new Actor( "Name2", "Surname2");
        actor1 = actorService.saveActor(actor1);
        Long actor1ID = actor1.getId();
        actor2 = actorService.saveActor(actor2);
        int actorsNumber = actorService.getAllActors().size();

        // When
        actorService.deleteActorsByNameAndSurname(actor2.getFirstname(), actor2.getSurname());

        // Then
        assertEquals(actorsNumber -1 , actorService.getAllActors().size());

        // CleanUp
        actorService.deleteActor(actor1ID);
    }

    @Test
    public void updateActorTestSuite() {
        // Given
        Actor actor1 = new Actor("Name1", "Surname1");
        actor1 = actorService.saveActor(actor1);
        Long actor1ID = actor1.getId();
        List<String> moviesTitle = new ArrayList<>();
        ActorDto actorDto2 = new ActorDto(actor1ID, "Name2", "Surname2", moviesTitle);

        // When
        actorService.updateActor(actorDto2);

        // Then
        Actor actor2 = actorService.getActor(actor1ID).orElse(new Actor());
        assertEquals("Name2", actor2.getFirstname());
        assertEquals("Surname2", actor2.getSurname());

        // CleanUp
        actorService.deleteActor(actor1ID);
    }

}

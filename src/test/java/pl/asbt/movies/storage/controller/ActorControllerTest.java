package pl.asbt.movies.storage.controller;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.asbt.movies.storage.dto.ActorDto;
import pl.asbt.movies.storage.facade.ActorFacade;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ActorController.class)
public class ActorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ActorFacade actorFacade;

    @Test
    public void createActor() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        ActorDto actorDto = new ActorDto(1L, "ActorFirstname1", "ActorSurname1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(actorDto);

        when(actorFacade.createActor(ArgumentMatchers.any(ActorDto.class))).thenReturn(actorDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getActor() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        ActorDto actorDto = new ActorDto(1L, "ActorFirstname1", "ActorSurname1", movieTitles);

        when(actorFacade.fetchActor(ArgumentMatchers.anyLong())).thenReturn(actorDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/actors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("actorId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("ActorFirstname1")))
                .andExpect(jsonPath("$.surname", is("ActorSurname1")));
    }

    @Test
    public void getActorsByNameAndSurname() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        ActorDto actorDto = new ActorDto(1L, "ActorFirstname1", "ActorSurname1", movieTitles);
        List<ActorDto> actorsDto = new ArrayList<>();
        actorsDto.add(actorDto);

        when(actorFacade.fetchActorByNameAndSurname(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(actorsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/actors/ActorFirstname1/ActorSurname1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("name", "ActorFirstname1")
                .param("surname", "ActorSurname1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is("ActorFirstname1")))
                .andExpect(jsonPath("$[0].surname", is("ActorSurname1")));
    }

    @Test
    public void getActors() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        ActorDto actorDto = new ActorDto(1L, "ActorFirstname1", "ActorSurname1", movieTitles);
        List<ActorDto> actorsDto = new ArrayList<>();
        actorsDto.add(actorDto);

        when(actorFacade.fetchActors()).thenReturn(actorsDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is("ActorFirstname1")))
                .andExpect(jsonPath("$[0].surname", is("ActorSurname1")));
    }

    @Test
    public void deleteActor() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/actors/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("actorId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(actorFacade, Mockito.times(1)).deleteActor(ArgumentMatchers.anyLong());
    }

    @Test
    public void deleteActorByNameAndSurname() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/actors/ActorFirstname1/ActorSurname1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("name", "ActorFirstname1")
                .param("surname", "ActorSurname1"))
                .andExpect(status().isOk());
        Mockito.verify(actorFacade, Mockito.times(1))
                .deleteActorByNameAndSurname(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void updateActor() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        ActorDto actorDto = new ActorDto(1L, "ActorFirstname1", "ActorSurname1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(actorDto);

        when(actorFacade.updateActor(ArgumentMatchers.any(ActorDto.class))).thenReturn(actorDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/actors")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }
}

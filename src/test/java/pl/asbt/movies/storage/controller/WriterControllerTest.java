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
import pl.asbt.movies.storage.dto.WriterDto;
import pl.asbt.movies.storage.facade.WriterFacade;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WriterController.class)
public class WriterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WriterFacade writerFacade;

    @Test
    public void createWriter() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        WriterDto writerDto = new WriterDto(1L, "WriterFirstname1", "WriterSurname1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(writerDto);

        when(writerFacade.createWriter(ArgumentMatchers.any(WriterDto.class))).thenReturn(writerDto);

        // When & Then
        mockMvc.perform(post("/v1/storage/writers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

    @Test
    public void getWriter() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        WriterDto writerDto = new WriterDto(1L, "WriterFirstname1", "WriterSurname1", movieTitles);

        when(writerFacade.fetchWriter(ArgumentMatchers.anyLong())).thenReturn(writerDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/writers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("writerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("WriterFirstname1")))
                .andExpect(jsonPath("$.surname", is("WriterSurname1")));
    }

    @Test
    public void getWriterByNameAndSurname() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        WriterDto writerDto = new WriterDto(1L, "WriterFirstname1", "WriterSurname1", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto);

        when(writerFacade.fetchWriterByNameAndSurname(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(writersDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/writers/WriterFirstname1/WriterSurname1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("name", "WriterFirstname1")
                .param("surname", "WriterSurname1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is("WriterFirstname1")))
                .andExpect(jsonPath("$[0].surname", is("WriterSurname1")));
    }

    @Test
    public void getWriters() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        WriterDto writerDto = new WriterDto(1L, "WriterFirstname1", "WriterSurname1", movieTitles);
        List<WriterDto> writersDto = new ArrayList<>();
        writersDto.add(writerDto);

        when(writerFacade.fetchWriters()).thenReturn(writersDto);

        // When & Then
        mockMvc.perform(get("/v1/storage/writers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is("WriterFirstname1")))
                .andExpect(jsonPath("$[0].surname", is("WriterSurname1")));
    }

    @Test
    public void deleteWriter() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/writers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("writerId", "1"))
                .andExpect(status().isOk());
        Mockito.verify(writerFacade, Mockito.times(1)).deleteWriter(ArgumentMatchers.anyLong());
    }

    @Test
    public void deleteWriterByNameAndSurname() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/storage/writers//WriterFirstname1/WriterSurname1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("name", "WriterFirstname1")
                .param("surname", "WriterSurname1"))
                .andExpect(status().isOk());
        Mockito.verify(writerFacade, Mockito.times(1))
                .deleteWriterByNameAndSurname(ArgumentMatchers.anyString(), ArgumentMatchers.anyString());
    }

    @Test
    public void updateWriter() throws Exception {
        // Given
        List<String> movieTitles = new ArrayList<>();
        WriterDto writerDto = new WriterDto(1L, "WriterFirstname1", "WriterSurname1", movieTitles);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(writerDto);

        when(writerFacade.updateWriter(ArgumentMatchers.any(WriterDto.class))).thenReturn(writerDto);

        // When & Then
        mockMvc.perform(put("/v1/storage/writers")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent));
    }

}

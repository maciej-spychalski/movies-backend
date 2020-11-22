package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class MovieDto {

    private Long id;
    private String title;
    private DirectorDto directorDto;
    private List<WriterDto> writersDto = new ArrayList<>();
    private List<ActorDto> actorsDto = new ArrayList<>();
    private List<GenreDto> genresDto = new ArrayList<>();
    private Integer duration;
}
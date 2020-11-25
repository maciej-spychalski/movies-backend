package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class MovieDto {

    @NotNull(message = "Please provide valid movie Id" )
    private Long id;
    @NotBlank(message = "Please provide valid movie title" )
    private String title;
    private DirectorDto directorDto;
    private List<WriterDto> writersDto = new ArrayList<>();
    private List<ActorDto> actorsDto = new ArrayList<>();
    private List<GenreDto> genresDto = new ArrayList<>();
    private Integer duration;
}

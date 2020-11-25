package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class GenreDto {

    @NotNull(message = "Please provide valid genre Id" )
    private Long id;
    @NotBlank(message = "Please provide genre type")
    private String type;
    private List<String> moviesTitle = new ArrayList<>();

}

package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class DirectorDto {

    @NotNull(message = "Please provide valid director Id" )
    private Long id;
    @NotBlank(message = "Please provide firstname")
    private String firstname;
    @NotBlank(message = "Please provide surname")
    private String surname;
    private List<String> moviesTitle = new ArrayList<>();

}

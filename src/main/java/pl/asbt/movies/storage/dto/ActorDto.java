package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ActorDto {

    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String surname;
    @NotNull
    private List<String> moviesTitle = new ArrayList<>();

}

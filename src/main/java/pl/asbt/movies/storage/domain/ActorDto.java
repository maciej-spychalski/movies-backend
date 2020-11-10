package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class ActorDto {

    private Long id;
    private String firstname;
    private String surname;
    private List<String> moviesTitle = new ArrayList<>();

}

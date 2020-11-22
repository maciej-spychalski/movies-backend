package pl.asbt.movies.storage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class GenreDto {

    private Long id;
    private String type;
    private List<String> moviesTitle = new ArrayList<>();

}

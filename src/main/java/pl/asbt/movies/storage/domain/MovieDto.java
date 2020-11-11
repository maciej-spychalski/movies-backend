package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class MovieDto {

    private Long id;
    private String title;
    private Long directorId;
    private List<Long> writersId = new ArrayList<>();
    private List<Long> actorsId = new ArrayList<>();
    private List<Long> genresId = new ArrayList<>();
    private Integer duration;
}

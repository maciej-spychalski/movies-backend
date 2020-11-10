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
    private String director;
    private List<String> writers = new ArrayList<>();
    private List<String> actors = new ArrayList<>();
    private List<String> genres = new ArrayList<>();
    private Long storageItemId;
    private Integer duration;
}

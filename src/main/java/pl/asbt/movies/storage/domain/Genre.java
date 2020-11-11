package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "GENRES")
public class Genre {

    private Long id;
    private String type;
    private List<Movie> movies = new ArrayList<>();

    public Genre(String type) {
        this.type = type;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_MOVIE_ACTOR",
            joinColumns = {@JoinColumn(name = "ID", referencedColumnName = "GENRE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ID", referencedColumnName = "MOVIE_ID")}
    )
    public List<Movie> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}

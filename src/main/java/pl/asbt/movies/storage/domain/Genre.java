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
@Entity (name = "GENRES")
public class Genre {

    private Long id;
    private String type;
    private List<Movie> movies = new ArrayList<>();

    public Genre(String type) {
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "GENRE_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "TYPE", unique = true)
    public String getType() {
        return type;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_MOVIE_GENRE",
            joinColumns = {@JoinColumn(name = "GENRE_ID", referencedColumnName = "GENRE_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MOVIE_ID", referencedColumnName = "MOVIE_ID")}
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

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
@Entity(name = "WRITERS")
public class Writer {

    private Long id;
    private String firstname;
    private String surname;
    private List<Movie> movies = new ArrayList<>();

    public Writer(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
    }

    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "JOIN_MOVIE_WRITER",
            joinColumns = {@JoinColumn(name = "ID", referencedColumnName = "WRITER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "ID", referencedColumnName = "MOVIE_ID")}
    )
    public List<Movie> getMovies() {
        return movies;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}

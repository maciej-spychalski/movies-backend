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
//@Entity
//@Table (name = "DIRECTORS")
@Entity (name = "DIRECTORS")
@Table (uniqueConstraints = {
        @UniqueConstraint(columnNames = {"FIRSTNAME", "SURNAME"})
})
//@Table (name = "DIRECTORS", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"FIRSTNAME", "SURNAME"})
//})
public class Director {

    private Long id;
    private String firstname;
    private String surname;
    private List<Movie> movies = new ArrayList<>();

    public Director(String firstname, String surname) {
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

    @OneToMany(
            targetEntity = Movie.class,
            mappedBy = "director",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
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

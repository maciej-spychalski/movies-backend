package pl.asbt.movies.storage.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "MOVIES")
public class Movie {

    private Long id;
    private String title;
    private Director director;
    private List<Writer> writers = new ArrayList<>();
    private List<Actor> actors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private Integer duration;
    private BigDecimal price;

    public Movie(String title, Integer duration, BigDecimal price) {
        this.title = title;
        this.duration = duration;
        this.price = price;
    }

    public Movie(String title, Director director, List<Writer> writers, List<Actor> actors, List<Genre> genres,
                 Integer duration, BigDecimal price) {
        this.title = title;
        this.director = director;
        this.writers = writers;
        this.actors = actors;
        this.genres = genres;
        this.duration = duration;
        this.price = price;
    }

    public static class MovieBuilder {
        private String title;
        private Director director;
        private List<Writer> writers = new ArrayList<>();
        private List<Actor> actors = new ArrayList<>();
        private List<Genre> genres = new ArrayList<>();
        private Integer duration;
        private BigDecimal price;

        public MovieBuilder title(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder director(Director director) {
            this.director = director;
            return this;
        }

        public MovieBuilder writer(Writer writer) {
            this.writers.add(writer);
            return this;
        }

        public MovieBuilder actor(Actor actor) {
            this.actors.add(actor);
            return this;
        }

        public MovieBuilder genre(Genre genre) {
            this.genres.add(genre);
            return this;
        }

        public MovieBuilder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public MovieBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Movie build() {
            return new Movie(title, director, writers, actors, genres, duration, price);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "MOVIE_ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "TITLE", unique = true)
    public String getTitle() {
        return title;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "DIRECTOR_ID")
    public Director getDirector() {
        return director;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "movies")
    public List<Writer> getWriters() {
        return writers;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "movies")
    public List<Actor> getActors() {
        return actors;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "movies")
    public List<Genre> getGenres() {
        return genres;
    }

    @Column(name = "DURATION")
    public Integer getDuration() {
        return duration;
    }

    @Column(name = "PRICE")
    public BigDecimal getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

    public void setActors(List<Actor> actorslist) {
        this.actors = actorslist;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

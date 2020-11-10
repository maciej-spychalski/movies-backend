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
@Entity(name = "MOVIES")
public class Movie {

    private Long id;
    private String title;
    private Director director;
    private List<Writer> writers = new ArrayList<>();
    private List<Actor> actors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private StorageItem storageItem;
    private Integer duration;

    public Movie(String title, Director director, List<Writer> writers, List<Actor> actors,
                 List<Genre> genres, StorageItem storageItem, Integer duration) {
        this.title = title;
        this.director = director;
        this.writers = writers;
        this.actors = actors;
        this.genres = genres;
        this.storageItem = storageItem;
        this.duration = duration;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "ID", unique = true)
    public Long getId() {
        return id;
    }

    @Column(name = "TITLE", unique = true)
    public String getTitle() {
        return title;
    }

    @ManyToOne
    @JoinColumn(name = "DIRECTOR_ID")
    public Director getDirector() {
        return director;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "movies")
    public List<Writer> getWriters() {
        return writers;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "movies")
    public List<Actor> getActorslist() {
        return actors;
    }

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "movies")
    public List<Genre> getGenres() {
        return genres;
    }

    @Column(name = "DURATION")
    public Integer getDuration() {
        return duration;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "STORAGE_ITEM_ID")
    public StorageItem getStorageItem() {
        return storageItem;
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

    public void setActorslist(List<Actor> actorslist) {
        this.actors = actorslist;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setStorageItem(StorageItem storageItem) {
        this.storageItem = storageItem;
    }
}

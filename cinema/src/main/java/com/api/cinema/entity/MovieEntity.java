package com.api.cinema.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_movie")
public class MovieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_movie")
    private UUID idMovie;

    @Column(nullable = false, unique = true, length = 150)
    private String movieName;

    @Column(nullable = false, unique = true)
    private Integer movieCapacity;

    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
    private List<SessionEntity> sessions;

    public UUID getIdMovie() {
        return idMovie;
    }


    public void setIdMovie(UUID idMovie) {
        this.idMovie = idMovie;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Integer getMovieCapacity() {
        return movieCapacity;
    }

    public void setMovieCapacity(Integer movieCapacity) {
        this.movieCapacity = movieCapacity;
    }

    public List<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionEntity> sessions) {
        this.sessions = sessions;
    }
}

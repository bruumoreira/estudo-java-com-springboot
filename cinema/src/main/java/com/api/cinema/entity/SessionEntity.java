package com.api.cinema.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_session")
public class SessionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_session")
    private UUID idSession;

    @Column(nullable = false, unique = true)
    private LocalDateTime sessionStart;

    @Column(nullable = false, unique = true)
    private LocalDateTime sessionEnd;;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="idMovie",referencedColumnName="id_movie", insertable=false, updatable=true)
    private MovieEntity movie;

    public UUID getIdSession() {
        return idSession;
    }

    public void setIdSession(UUID idSession) {
        this.idSession = idSession;
    }

    public LocalDateTime getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(LocalDateTime sessionStart) {
        this.sessionStart = sessionStart;
    }

    public LocalDateTime getSessionEnd() {
        return sessionEnd;
    }

    public void setSessionEnd(LocalDateTime sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}

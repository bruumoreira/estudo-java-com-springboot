package com.api.cinema.repository;

import com.api.cinema.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionCustomRepository extends JpaRepository<SessionEntity, UUID> {

    @Query(
            value = "select " +
                    "   ts.session_start sessionStart, " +
                    "   ts.session_end sessionEnd, " +
                    "   tm.movie_name movieName " +
                    " from tb_session ts " +
                    " inner join tb_movie tm on (tm.id_movie = ts.id_movie)", nativeQuery = true)
    Optional<List<SessionCustom>> findAllCustom();

    interface SessionCustom {
        LocalDateTime getSessionStart();
        LocalDateTime getSessionEnd();
        String getMovieName();
    }

}

package com.api.cinema.repository;

import com.api.cinema.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieRepository  extends JpaRepository<MovieEntity, UUID> {

}

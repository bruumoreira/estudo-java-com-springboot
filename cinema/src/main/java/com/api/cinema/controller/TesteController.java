package com.api.cinema.controller;

import com.api.cinema.entity.SessionEntity;
import com.api.cinema.repository.SessionCustomRepository;
import com.api.cinema.repository.SessionRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Api(tags = "teste", description = "teste")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/test")
public class TesteController {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionCustomRepository sessionCustomRepository;

    @GetMapping
    public ResponseEntity<Object> get(){
        List<SessionEntity> sessions = sessionRepository.findAll();
        Optional<List<SessionCustomRepository.SessionCustom>> teste = sessionCustomRepository.findAllCustom();
        return ResponseEntity.status(HttpStatus.OK).body(teste.get());
    }

}

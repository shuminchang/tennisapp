package com.shumin.tennisapp.controller;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.service.AtpPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    private AtpPlayerService atpPlayerService;

    @GetMapping("/")
    public String greet() {
        return "hello";
    }

    @GetMapping("/players")
    public List<AtpPlayerDto> getAllAtpPlayers() {
        return atpPlayerService.getAllAtpPlayers();
    }
}

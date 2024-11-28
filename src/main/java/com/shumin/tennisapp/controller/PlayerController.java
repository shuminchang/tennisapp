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
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class PlayerController {

    @Autowired
    private AtpPlayerService atpPlayerService;

    public void setAtpPlayerService(AtpPlayerService atpPlayerService) {
        this.atpPlayerService = atpPlayerService;
    }

    @GetMapping("/")
    public String greet() {
        return "hello";
    }

    @GetMapping("/players")
    public List<AtpPlayerDto> getAllAtpPlayers() {
        return atpPlayerService.getAllAtpPlayers();
    }

    @GetMapping("/player-by-hand")
    public List<Map<String, Object>> getPlayersByHand() {
        return atpPlayerService.getPlayersByHand().stream()
                .map(result -> Map.of(
                        "hand", result[0],
                        "count", result[1]
                ))
                .collect(Collectors.toList());
    }
}

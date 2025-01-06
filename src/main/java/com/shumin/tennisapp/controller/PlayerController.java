package com.shumin.tennisapp.controller;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.exceptions.PlayerException;
import com.shumin.tennisapp.service.AtpPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<AtpPlayerDto> getAllAtpPlayers() throws PlayerException {
        return atpPlayerService.getAllAtpPlayers();
    }

    @GetMapping("/player-by-hand")
    public List<Map<String, Object>> getPlayersByHand() throws PlayerException {
        return atpPlayerService.getPlayersByHand().stream()
                .map(result -> Map.of(
                        "hand", result[0],
                        "count", result[1]
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/height-vs-age")
    public List<Map<String, Object>> getHeightVsAge() throws PlayerException {
        return atpPlayerService.findHeightVsAge().stream()
                .map(result -> Map.of(
                        "height", result[0],
                        "age", result[1]
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/height-distribution")
    public Map<String, Integer>  getAllHeights() throws PlayerException {
        return atpPlayerService.findGroupedHeights();
    }

    @GetMapping("/player-height-age-hand-clusters")
    public List<Map<String, Object>> getPlayerClusters(@RequestParam int numClusters) throws PlayerException {
        return atpPlayerService.playerHeightAgeHandClusters(numClusters);
    }

}

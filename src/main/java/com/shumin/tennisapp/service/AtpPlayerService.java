package com.shumin.tennisapp.service;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.repository.AtpPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtpPlayerService {

    @Autowired
    private AtpPlayerRepository atpPlayerRepository;

    public List<AtpPlayerDto> getAllAtpPlayers() {
        List<AtpPlayer> atpPlayers = atpPlayerRepository.findAll();
        List<AtpPlayerDto> atpPlayerDtos = new ArrayList<>();
        for (AtpPlayer atpPlayer : atpPlayers) {
            atpPlayerDtos.add(getAtpPlayerDto(atpPlayer));
        }
        return atpPlayerDtos;
    }

    private AtpPlayerDto getAtpPlayerDto(AtpPlayer atpPlayer) {
        AtpPlayerDto atpPlayerDto = new AtpPlayerDto();
        atpPlayerDto.setPlayerId(atpPlayer.getPlayerId());
        atpPlayerDto.setNameFirst(atpPlayer.getNameFirst());
        atpPlayerDto.setNameLast(atpPlayer.getNameLast());
        atpPlayerDto.setHand(atpPlayer.getHand());
        atpPlayerDto.setDob(atpPlayer.getDob());
        atpPlayerDto.setIoc(atpPlayer.getIoc());
        atpPlayerDto.setHeight(atpPlayer.getHeight());
        return atpPlayerDto;
    }
}

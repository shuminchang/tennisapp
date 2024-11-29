package com.shumin.tennisapp.service;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.repository.AtpPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AtpPlayerService {

    @Autowired
    private AtpPlayerRepository atpPlayerRepository;

    public void setAtpPlayerRepository(AtpPlayerRepository atpPlayerRepository) {
        this.atpPlayerRepository = atpPlayerRepository;
    }

    public List<AtpPlayerDto> getAllAtpPlayers() {
        List<AtpPlayer> atpPlayers = atpPlayerRepository.findAll();
        List<AtpPlayerDto> atpPlayerDtos = new ArrayList<>();
        for (AtpPlayer atpPlayer : atpPlayers) {
            if (atpPlayer.getHeight() != null) {
                atpPlayerDtos.add(getAtpPlayerDto(atpPlayer));
            }
        }
        return atpPlayerDtos;
    }

    public List<Object[]> getPlayersByHand() {
        return atpPlayerRepository.countPlayersByHand();
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
        atpPlayerDto.setAge(atpPlayerDto.getAge());
        return atpPlayerDto;
    }

    public List<Object[]> findHeightVsAge() {
        return atpPlayerRepository.findHeightVsAge();
    }

    public Map<String, Integer> findGroupedHeights() {
        List<Integer> heights = atpPlayerRepository.findAllHeights();

        // Define bins
        int[] bins = {145, 150, 155, 160, 165, 170, 175, 180, 185, 190, 195, 200, 205, 210};
        Map<String, Integer> groupedData = new LinkedHashMap<>();

        // Initialize bins
        for (int i = 0; i < bins.length - 1; i++) {
            String range = bins[i] + "-" + bins[i + 1];
            groupedData.put(range, 0);
        }
        groupedData.put(bins[bins.length - 1] + "+", 0);

        // Group heights into bins
        for (int height : heights) {
            boolean added = false;
            for (int i = 0; i < bins.length - 1; i++) {
                if (height >= bins[i] && height < bins[i + 1]) {
                    String range = bins[i] + "-" + bins[i + 1];
                    groupedData.put(range, groupedData.get(range) + 1);
                    added = true;
                    break;
                }
            }
            if (!added) {
                String lastRange = bins[bins.length - 1] + "+";
                groupedData.put(lastRange, groupedData.get(lastRange) + 1);
            }
        }

        return groupedData;
    }
}

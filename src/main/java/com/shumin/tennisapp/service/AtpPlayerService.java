package com.shumin.tennisapp.service;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.repository.AtpPlayerRepository;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
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

    public List<Map<String, Object>> playerHeightAgeHandClusters(int numClusters) {
        List<AtpPlayer> players = atpPlayerRepository.findAll();
        List<DoublePoint> points = new ArrayList<>();

        // Prepare data points for clustering
        for (AtpPlayer player : players) {
            if (player.getHeight() != null && player.getAge() != null && player.getHand() != null) {
                double handValue = "R".equals(player.getHand()) ? 1.0 : ("L".equals(player.getHand()) ? -1.0 : 0.0);
                points.add(new DoublePoint(new double[]{player.getHeight(), player.getAge(), handValue}));
            }
        }

        // Perform K-Means clustering
        KMeansPlusPlusClusterer<DoublePoint> clusterer = new KMeansPlusPlusClusterer<>(numClusters);
        List<CentroidCluster<DoublePoint>> clusters = clusterer.cluster(points);

        // Prepare cluster results
        List<Map<String, Object>> results = new ArrayList<>();
        for (int i = 0; i < clusters.size(); i++) {
            Cluster<DoublePoint> cluster = clusters.get(i);
            for (DoublePoint point : cluster.getPoints()) {
                results.add(Map.of(
                        "cluster", i,
                        "height", point.getPoint()[0],
                        "age", point.getPoint()[1],
                        "hand", point.getPoint()[2]
                ));
            }
        }

        return results;
    }
}

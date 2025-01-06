package com.shumin.tennisapp.service;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.exceptions.PlayerException;
import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.model.AtpPlayerOM;
import com.shumin.tennisapp.repository.AtpPlayerRepository;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AtpPlayerServiceTest {

    private AtpPlayerRepository atpPlayerRepositoryMock;

    private AtpPlayerService atpPlayerService;

    @BeforeEach
    void setUp() {
        atpPlayerRepositoryMock = EasyMock.createMock(AtpPlayerRepository.class);
        atpPlayerService = new AtpPlayerService();
        atpPlayerService.setAtpPlayerRepository(atpPlayerRepositoryMock); // Setter injection for EasyMock
    }

    @Test
    public void testGetAllAtpPlayers() throws Exception {
        AtpPlayer player = AtpPlayerOM.newAtpPlayerByFirstNameLastName("Roger", "Federer");

        EasyMock.expect(atpPlayerRepositoryMock.findAll()).andReturn(Arrays.asList(player));
        EasyMock.replay(atpPlayerRepositoryMock);

        List<AtpPlayerDto> result = atpPlayerService.getAllAtpPlayers();
        assertEquals(1, result.size());
        assertEquals("Roger", result.get(0).getNameFirst());
        assertEquals("Federer", result.get(0).getNameLast());

        EasyMock.verify(atpPlayerRepositoryMock);
    }

    @Test
    public void testGetAllAtpPlayers_noPlayersFound() throws Exception {
        EasyMock.expect(atpPlayerRepositoryMock.findAll()).andReturn(Collections.emptyList());
        EasyMock.replay(atpPlayerRepositoryMock);

        PlayerException exception = assertThrows(PlayerException.class,
                () -> atpPlayerService.getAllAtpPlayers());
        assertEquals("No players found in the database.", exception.getMessage());

        EasyMock.verify(atpPlayerRepositoryMock);
    }


    @Test
    public void testGetPlayersByHand() throws Exception {
        EasyMock.expect(atpPlayerRepositoryMock.countPlayersByHand())
                .andReturn(List.of(new Object[]{"R", 100}, new Object[]{"L", 20}));
        EasyMock.replay(atpPlayerRepositoryMock);

        List<Object[]> result = atpPlayerService.getPlayersByHand();
        assertEquals(2, result.size());
        assertEquals("R", result.get(0)[0]);
        assertEquals(100, result.get(0)[1]);

        EasyMock.verify(atpPlayerRepositoryMock);
    }

    @Test
    public void testFindHeightVsAge() throws Exception {
        List<Object[]> mockData = List.of(new Object[]{180, 25}, new Object[]{190, 30});
        EasyMock.expect(atpPlayerRepositoryMock.findHeightVsAge()).andReturn(mockData);

        EasyMock.replay(atpPlayerRepositoryMock);

        List<Object[]> result = atpPlayerService.findHeightVsAge();
        EasyMock.verify(atpPlayerRepositoryMock);

        assertEquals(2, result.size());
        assertEquals(180, result.get(0)[0]);
        assertEquals(25, result.get(0)[1]);

        EasyMock.reset(atpPlayerRepositoryMock);
    }

    @Test
    public void testFindGroupedHeights() throws Exception {
        List<Integer> mockData = List.of(180, 190);
        EasyMock.expect(atpPlayerRepositoryMock.findAllHeights()).andReturn(mockData);

        EasyMock.replay(atpPlayerRepositoryMock);

        Map<String, Integer> result = atpPlayerService.findGroupedHeights();

        EasyMock.verify(atpPlayerRepositoryMock);

        assertEquals(14, result.size());
        assertEquals(1, result.get("180-185"));
        assertEquals(1, result.get("190-195"));

        EasyMock.reset(atpPlayerRepositoryMock);
    }

    @Test
    public void testClusterPlayers() throws Exception {
        // Mock data for players
        List<AtpPlayer> mockPlayers = List.of(
                AtpPlayerOM.newAtpPlayerByHeightAgeHand(180, 25, "R"),
                AtpPlayerOM.newAtpPlayerByHeightAgeHand(175, 30, "L"),
                AtpPlayerOM.newAtpPlayerByHeightAgeHand(185, 24, "R"),
                AtpPlayerOM.newAtpPlayerByHeightAgeHand(170, 35, "L")
        );

        // Mock the repository call
        EasyMock.expect(atpPlayerRepositoryMock.findAll()).andReturn(mockPlayers);
        EasyMock.replay(atpPlayerRepositoryMock);

        // Inject the mock repository into the service
        atpPlayerService.setAtpPlayerRepository(atpPlayerRepositoryMock);

        // Perform clustering with 2 clusters
        int numClusters = 2;
        List<Map<String, Object>> clusters = atpPlayerService.playerHeightAgeHandClusters(numClusters);

        // Validate the results
        assertEquals(4, clusters.size(), "Number of clustered points should match the input");

        // Check that cluster IDs are assigned correctly
        clusters.forEach(cluster -> {
            assertEquals(4, cluster.size(), "Each cluster point should have 3 attributes (height, age, hand)");
            assertEquals(true, cluster.containsKey("cluster"), "Each cluster should have a cluster ID");
            assertEquals(true, cluster.containsKey("height"), "Each cluster should include height");
            assertEquals(true, cluster.containsKey("age"), "Each cluster should include age");
            assertEquals(true, cluster.containsKey("hand"), "Each cluster should include hand");
        });

        EasyMock.verify(atpPlayerRepositoryMock);
    }
}

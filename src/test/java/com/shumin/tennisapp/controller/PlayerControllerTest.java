package com.shumin.tennisapp.controller;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.dto.AtpPlayerDtoOM;
import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.model.AtpPlayerOM;
import com.shumin.tennisapp.service.AtpPlayerService;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerControllerTest {

    private MockMvc mockMvc;

    private AtpPlayerService atpPlayerServiceMock;

    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        atpPlayerServiceMock = EasyMock.createMock(AtpPlayerService.class); // This line triggers the CGLIB proxy creation, need easymock 5.2.0
        playerController = new PlayerController();
        playerController.setAtpPlayerService(atpPlayerServiceMock); // Setter injection for EasyMock
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }

    @Test
    void testGetAllAtpPlayers() throws Exception {
        AtpPlayer player = AtpPlayerOM.newAtpPlayerByFirstNameLastName("Roger", "Federer");
        AtpPlayerDto dto = AtpPlayerDtoOM.newAtpPlayerDto(player);
        // new dto not model
        List<AtpPlayerDto> players = Arrays.asList(dto);

        // Define EasyMock behavior
        EasyMock.expect(atpPlayerServiceMock.getAllAtpPlayers()).andReturn(players);
        EasyMock.replay(atpPlayerServiceMock);

        // Perform the test
        mockMvc.perform(get("/api/players").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nameFirst").value("Roger"))
                .andExpect(jsonPath("$[0].nameLast").value("Federer"));

        // Verify interaction
        EasyMock.verify(atpPlayerServiceMock);
    }

    @Test
    void testGetPlayersByHand() throws Exception {
        // Define behavior
        EasyMock.expect(atpPlayerServiceMock.getPlayersByHand()).andReturn(
                List.of(new Object[]{"R", 100}, new Object[]{"L", 20})
        );
        EasyMock.replay(atpPlayerServiceMock);

        // Perform the test
        mockMvc.perform(get("/api/player-by-hand").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].hand").value("R"))
                .andExpect(jsonPath("$[0].count").value(100))
                .andExpect(jsonPath("$[1].hand").value("L"))
                .andExpect(jsonPath("$[1].count").value(20));

        // Verify interaction
        EasyMock.verify(atpPlayerServiceMock);
    }

    @Test
    void testGetHeightVsAge() throws Exception {

        List<Object[]> mockData = List.of(new Object[]{180, 25}, new Object[]{190, 30});
        EasyMock.expect(atpPlayerServiceMock.findHeightVsAge()).andReturn(mockData);

        EasyMock.replay(atpPlayerServiceMock);

        mockMvc.perform(get("/api/height-vs-age").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].height").value(180))
                        .andExpect(jsonPath("$[0].age").value(25));

        EasyMock.verify(atpPlayerServiceMock);
        EasyMock.reset(atpPlayerServiceMock);
    }

    @Test
    void testGetAllHeights() throws Exception {

        Map<String, Integer> mockData = Map.of(
                "150", 1,
                "160", 2
        );
        EasyMock.expect(atpPlayerServiceMock.findGroupedHeights()).andReturn(mockData);

        EasyMock.replay(atpPlayerServiceMock);

        mockMvc.perform(get("/api/height-distribution").accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.150").value(1))
                        .andExpect(jsonPath("$.160").value(2));

        EasyMock.verify(atpPlayerServiceMock);
        EasyMock.reset(atpPlayerServiceMock);
    }
}

package com.shumin.tennisapp.service;

import com.shumin.tennisapp.dto.AtpPlayerDto;
import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.repository.AtpPlayerRepository;
import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void testGetAllAtpPlayers() {
        AtpPlayer player = new AtpPlayer();
        player.setPlayerId(1);
        player.setNameFirst("Roger");
        player.setNameLast("Federer");

        EasyMock.expect(atpPlayerRepositoryMock.findAll()).andReturn(Arrays.asList(player));
        EasyMock.replay(atpPlayerRepositoryMock);

        List<AtpPlayerDto> result = atpPlayerService.getAllAtpPlayers();
        assertEquals(1, result.size());
        assertEquals("Roger", result.get(0).getNameFirst());
        assertEquals("Federer", result.get(0).getNameLast());

        EasyMock.verify(atpPlayerRepositoryMock);
    }

    @Test
    void testGetPlayersByHand() {
        EasyMock.expect(atpPlayerRepositoryMock.countPlayersByHand())
                .andReturn(List.of(new Object[]{"R", 100}, new Object[]{"L", 20}));
        EasyMock.replay(atpPlayerRepositoryMock);

        List<Object[]> result = atpPlayerService.getPlayersByHand();
        assertEquals(2, result.size());
        assertEquals("R", result.get(0)[0]);
        assertEquals(100, result.get(0)[1]);

        EasyMock.verify(atpPlayerRepositoryMock);
    }
}

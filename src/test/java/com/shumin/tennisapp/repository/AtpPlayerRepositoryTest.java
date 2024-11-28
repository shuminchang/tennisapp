package com.shumin.tennisapp.repository;

import com.shumin.tennisapp.model.AtpPlayer;
import com.shumin.tennisapp.model.AtpPlayerOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
public class AtpPlayerRepositoryTest {

    @Autowired
    private AtpPlayerRepository atpPlayerRepository;
    @Autowired
    private DataSource dataSource;

    @Test
    public void verifyDatabaseUrl() throws Exception {
        System.out.println("Database URL: " + dataSource.getConnection().getMetaData().getURL());
    }
    @BeforeEach
    void clearDatabase() {
        atpPlayerRepository.deleteAll();
    }

    private AtpPlayer createPlayer(String hand) {
        AtpPlayer player = new AtpPlayer();
        player.setHand(hand);
        return player;
    }
    @Test
    void testCountPlayersByHand() {
        // Arrange
        AtpPlayer player1 = AtpPlayerOM.newAtpPlayerByHand("R");
        atpPlayerRepository.save(player1);

        AtpPlayer player2 = AtpPlayerOM.newAtpPlayerByHand("L");
        atpPlayerRepository.save(player2);

        // Act
        List<Object[]> results = atpPlayerRepository.countPlayersByHand();

        // Assert
        assertEquals(2, results.size());
        assertEquals("L", results.get(0)[0]); // Hand
        assertEquals(1L, results.get(0)[1]); // Count
        assertEquals("R", results.get(1)[0]); // Hand
        assertEquals(1L, results.get(1)[1]);
    }

}

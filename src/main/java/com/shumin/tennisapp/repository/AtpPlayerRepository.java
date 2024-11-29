package com.shumin.tennisapp.repository;

import com.shumin.tennisapp.model.AtpPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtpPlayerRepository extends JpaRepository<AtpPlayer, Integer> {

    @Query("SELECT hand, COUNT(hand) FROM AtpPlayer WHERE hand IS NOT NULL GROUP BY hand")
    List<Object[]> countPlayersByHand();

    @Query("SELECT a.height, a.age FROM AtpPlayer a WHERE a.height IS NOT NULL AND a.age IS NOT NULL")
    List<Object[]> findHeightVsAge();
}

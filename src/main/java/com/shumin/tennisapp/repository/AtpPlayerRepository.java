package com.shumin.tennisapp.repository;

import com.shumin.tennisapp.model.AtpPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtpPlayerRepository extends JpaRepository<AtpPlayer, Integer> {
}

package com.shumin.tennisapp.dto;

import com.shumin.tennisapp.model.AtpPlayer;

public class AtpPlayerDtoOM {

    public static AtpPlayerDto newAtpPlayerDto(AtpPlayer atpPlayer) {
        return new AtpPlayerDto(atpPlayer);
    }
}

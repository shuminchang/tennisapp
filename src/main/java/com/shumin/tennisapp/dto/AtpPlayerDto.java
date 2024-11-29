package com.shumin.tennisapp.dto;

import com.shumin.tennisapp.model.AtpPlayer;

public class AtpPlayerDto {
    private int playerId;

    private String nameFirst;

    private String nameLast;

    private String hand;

    private String dob;
    private String ioc;

    private int height;

    private int age;

    public AtpPlayerDto() {
    }

    public AtpPlayerDto(AtpPlayer atpPlayer) {
        this.playerId = atpPlayer.getPlayerId();
        this.nameFirst = atpPlayer.getNameFirst();
        this.nameLast = atpPlayer.getNameLast();
        this.hand = atpPlayer.getHand();
        this.dob = atpPlayer.getDob();
        this.ioc = atpPlayer.getIoc();
        this.height = atpPlayer.getHeight();
        this.age = atpPlayer.getAge();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getNameFirst() {
        return nameFirst;
    }

    public void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    public String getNameLast() {
        return nameLast;
    }

    public void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIoc() {
        return ioc;
    }

    public void setIoc(String ioc) {
        this.ioc = ioc;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

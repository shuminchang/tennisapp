package com.shumin.tennisapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "atp_player")
public class AtpPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "player_id")
    private int playerId;
    @Column(name = "name_first")
    private String nameFirst;
    @Column(name = "name_last")
    private String nameLast;
    @Column(name = "hand")
    private String hand;
    @Column(name = "dob")
    private String dob;
    @Column(name = "ioc")
    private String ioc;
    @Column(name = "height")
    private Integer height;
    @Column(name = "wikidata_id")
    private String wikidataId;

    @Column(name = "age")
    private Integer age;

    public AtpPlayer() {
    }

    public AtpPlayer(int id, int playerId, String nameFirst, String nameLast, String hand,
                     String dob, String ioc, int height, String wikidataId, Integer age) {
        this.id = id;
        this.playerId = playerId;
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.hand = hand;
        this.dob = dob;
        this.ioc = ioc;
        this.height = height;
        this.wikidataId = wikidataId;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getWikidataId() {
        return wikidataId;
    }

    public void setWikidataId(String wikidataId) {
        this.wikidataId = wikidataId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

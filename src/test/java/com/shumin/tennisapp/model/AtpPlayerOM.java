package com.shumin.tennisapp.model;

public class AtpPlayerOM {

    public static AtpPlayer newAtpPlayer(int id, int playerId, String nameFirst, String nameLast,
                                         String hand, String dob, String ioc, int height, String wikidataId, Integer age) {
        return new AtpPlayer(id, playerId, nameFirst, nameLast, hand, dob, ioc, height, wikidataId, age);
    }

    public static AtpPlayer newAtpPlayer() {
        return newAtpPlayer(1, 1, "firstName", "lastName",
                "hand", "dob", "ioc", 180, "wikidataId", 20);
    }

    public static AtpPlayer newAtpPlayerByHand(String hand) {
        AtpPlayer ret = newAtpPlayer();
        ret.setHand(hand);
        return ret;
    }

    public static AtpPlayer newAtpPlayerByFirstNameLastName(String firstName, String lastName) {
        AtpPlayer ret = newAtpPlayer();
        ret.setNameFirst(firstName);
        ret.setNameLast(lastName);
        return ret;
    }

    public static AtpPlayer newAtpPlayerByHeightAge(Integer height, Integer age) {
        AtpPlayer ret = newAtpPlayer();
        ret.setHeight(height);
        ret.setAge(age);
        return ret;
    }

    public static AtpPlayer newAtpPlayerByHeightAgeHand(Integer height, Integer age, String hand) {
        AtpPlayer ret = newAtpPlayerByHeightAge(height, age);
        ret.setHand(hand);
        return ret;
    }
}

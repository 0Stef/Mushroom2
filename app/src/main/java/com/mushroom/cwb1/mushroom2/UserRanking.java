package com.mushroom.cwb1.mushroom2;

public class UserRanking {
    public String name;
    public String points;
    public String nbRank;
    public Boolean ThisUser;

    public UserRanking(String name, String points, String numberRanking, Boolean ThisUser) {
        this.name = name;
        this.points = points;
        this.nbRank = numberRanking;
        this.ThisUser = ThisUser;
    }
}

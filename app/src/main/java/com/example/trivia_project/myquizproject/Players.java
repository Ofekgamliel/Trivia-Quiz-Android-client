package com.example.trivia_project.myquizproject;

import java.io.Serializable;

public class Players implements Serializable{

    private String playerName;
    private String score;

    public Players() {
    }

    public Players(String playerName, String score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}

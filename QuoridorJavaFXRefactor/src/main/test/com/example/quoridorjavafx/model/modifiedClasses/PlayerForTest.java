package com.example.quoridorjavafx.model.modifiedClasses;

import com.example.quoridorjavafx.model.javaComponents.Node;

import java.util.ArrayList;
import java.util.List;

public class PlayerForTest {
    int walls;
    Node positon;
    Node whicheverGoal;
    String color;

    List<Node> allSpecificGoals;
    public List<Boolean> playerScore;
    public PlayerForTest(String color){

        this.color = color;
        walls = 10;
        allSpecificGoals = new ArrayList<>();
        playerScore = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWalls() {
        return walls;
    }

    public void setWalls(int walls) {
        this.walls = walls;
    }


    public Node getPositon() {
        return positon;
    }

    public void setPositon(Node positon) {
        this.positon = positon;
    }

    public Node getGoal() {
        return whicheverGoal;
    }

    public void setGoal(Node goal) {
        this.whicheverGoal = goal;
    }

}

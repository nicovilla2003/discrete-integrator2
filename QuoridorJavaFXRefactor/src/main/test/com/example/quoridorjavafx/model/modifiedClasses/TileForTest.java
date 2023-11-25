package com.example.quoridorjavafx.model.modifiedClasses;

import com.example.quoridorjavafx.model.javaComponents.Node;
import com.example.quoridorjavafx.model.javaComponents.Tile;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class TileForTest {

    public Node position;

    boolean occupied;

    public TileForTest (Node node){
        position = node;
        this.occupied = false;


    }

    public Node getPosition(){

        return position;

    }

    public void setPosition(Node node){

        position = node;

    }
}

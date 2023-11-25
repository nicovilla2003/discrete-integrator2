package com.example.quoridorjavafx.model.javaFXComponents;


import com.example.quoridorjavafx.model.javaComponents.Node;
import com.example.quoridorjavafx.model.javaComponents.Player;
import com.example.quoridorjavafx.model.javaComponents.Tile;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.junit.Test;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static org.junit.Assert.assertEquals;

public class TileAndPlayerTest extends Application{


    @Override
    public void start(Stage stage) throws Exception {
        Node node = new Node(5,5);

        Tile tile = new Tile(node);
        tile.setPrefHeight(100);
        tile.setPrefWidth(100);
        tile.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.5,1.5,1.5,1.5))));
        Color color1 = Color.web("#b1e4b9");
        Color color2 = Color.web("#70a2a3");

        tile.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));


        tile.thickenBorderSide(Tile.Side.BOTTOM);
        tile.thickenBorderSide(Tile.Side.LEFT);
        tile.thickenBorderSide(Tile.Side.RIGHT);
        tile.thickenBorderSide(Tile.Side.TOP);
        Player player = new Player("white");
        tile.getChildren().add(player);
        player.setPositon(node);
        assertEquals(node, player.getPositon());
        assertEquals(node, tile.position);
        Scene scene = new Scene(tile, 400, 300);

        // Setting the scene to the stage and displaying the stage
        stage.setScene(scene);
        stage.setTitle("StackPane Example");
        stage.show();
    }
}
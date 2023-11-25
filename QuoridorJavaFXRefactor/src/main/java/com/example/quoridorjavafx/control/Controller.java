package com.example.quoridorjavafx.control;

import com.example.quoridorjavafx.HelloApplication;
import com.example.quoridorjavafx.model.javaComponents.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class Controller {
    @FXML
    public RadioButton movePawn;
    @FXML
    public RadioButton placeVerticalWall;
    public ToggleGroup Move;
    @FXML
    public RadioButton placeHorizontalWall;
    @FXML
    public Button instructions;
    @FXML
    GridPane chessBoard;

    public static Game game;

    public void initialize(){

        // Themes are Coral, Dusk, Wheat, Marine, Emerald, Sandcastle

        game = new Game(chessBoard);

    }
    @FXML
    public void changeMoveType(ActionEvent actionEvent) {

        if(movePawn.isSelected()){

            game.changeMoveType(0);

        }else if(placeVerticalWall.isSelected()){

            game.changeMoveType(1);

        }else if(placeHorizontalWall.isSelected()){

            game.changeMoveType(2);

        }

    }
    @FXML
    public void openInstructons(ActionEvent actionEvent) {

        HelloApplication.openInstructions();

    }

}

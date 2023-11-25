package com.example.quoridorjavafx.control;

import com.example.quoridorjavafx.HelloApplication;
import com.example.quoridorjavafx.model.javaComponents.Game;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import static com.example.quoridorjavafx.model.javaComponents.Game.currentPlayer;

public class CongratulationScreenController {
    public Label playerName;
    public ImageView leftImage;
    public ImageView rightImage;
    public Label score;

    public void initialize(){

        File file = new File("src/main/java/com/example/quoridorjavafx/model/javaComponents/images/conffettiPopper1.png");
        String absolutePath = file.toURI().toString();
        leftImage.setImage(new Image(absolutePath));
        rightImage.setImage(new Image(absolutePath));

        playerName.setText(Game.currentPlayerColor+" pawn");
        score.setText(getScores());

    }

    public String getScores(){
        int wScore = 0;
        int totalWMoves = Game.currentPlayer.playerScore.size();
        for(Boolean val: Game.currentPlayer.playerScore){

            if(val)
                wScore++;

        }

        int lScore = 0;
        int totalLMoves = Game.qb.getOppositePlayer().playerScore.size();
        for(Boolean val: Game.qb.getOppositePlayer().playerScore){

            if(val)

                lScore++;

        }

        return "Winner Score: "+wScore+"/"+totalWMoves+
                "\nLoser Score: "+lScore+"/"+totalLMoves;

    }


}

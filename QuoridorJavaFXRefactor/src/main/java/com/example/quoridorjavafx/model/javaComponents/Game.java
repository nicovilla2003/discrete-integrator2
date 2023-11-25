package com.example.quoridorjavafx.model.javaComponents;

import com.example.quoridorjavafx.HelloApplication;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    public static Player currentPlayer;

    public static String currentPlayerColor;

    public static List<Tile> currentWallTiles;

    public static QuoridorBoard qb;

    private static boolean game;

    public static Integer moveType;

    public Game(GridPane quoridorBoard) {
        qb = new QuoridorBoard(quoridorBoard);
        currentPlayer = null;
        currentPlayerColor = "white";
        Game.game = true;
        Game.moveType = null;
        currentWallTiles = new ArrayList<>();
        addEventHandlers(qb.quoridorBoard);

    }

    private void addEventHandlers(GridPane chessBoard) {
        chessBoard.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (game) {
                    if (moveType != null) {
                        if (moveType == 0) {
                            EventTarget target = event.getTarget();

                            if (target instanceof Tile) {

                                Tile tile = (Tile) target;
                                if (tile.occupied) {
                                    Player newPlayer = (Player) tile.getChildren().get(0);

                                    if (currentPlayer == null) {
                                        currentPlayer = newPlayer;

                                        if (!currentPlayer.getColor().equals(currentPlayerColor)) {
                                            currentPlayer = null;
                                            return;
                                        }
                                        selectPiece(game);
                                    }

                                } else {
                                    dropPiece(tile);
                                }

                            } else if (target instanceof Player) {

                                Player newPlayer = (Player) target;
                                Tile tile = (Tile) newPlayer.getParent();
                                // Selecting a new piece
                                if (currentPlayer == null) {
                                    currentPlayer = newPlayer;
                                    if (!currentPlayer.getColor().equals(currentPlayerColor)) {
                                        currentPlayer = null;
                                        return;
                                    }
                                    selectPiece(game);

                                }


                            }

                        } else {

                            EventTarget target = event.getTarget();

                            if (target instanceof Tile) {

                                Tile tile = (Tile) target;

                                selectChoice(tile);


                            } else if (target instanceof Player) {

                                Player newPlayer = (Player) target;
                                Tile tile = (Tile) newPlayer.getParent();
                                // Selecting a new piece
                                selectChoice(tile);


                            }

                        }

                    }
                }
            }
        });
    }

    private void selectChoice(Tile tile) {
        if(game) {
            if (currentWallTiles.size() < 3 && !currentWallTiles.contains(tile)) {
                currentWallTiles.add(tile);
                tile.highlightThis();

            } else if (currentWallTiles.size() == 3 && !currentWallTiles.contains(tile)) {

                currentWallTiles.add(tile);
                tile.highlightThis();
                placeWall();

            }
        }
    }

    private void selectPiece(boolean game){
        if(!game){
            currentPlayer = null;
            return;
        }

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.BLACK);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        currentPlayer.setEffect(borderGlow);
        currentPlayer.showAllPossibleMoves(true);
    }

    private void deselectPiece(boolean changePlayer){
        currentPlayer.setEffect(null);
        currentPlayer.showAllPossibleMoves(false);
        currentPlayer = null;
        if(changePlayer) currentPlayerColor = currentPlayerColor.equals("white") ? "black" : "white";
    }

    private void dropPiece(Tile tile){
        if(currentPlayer != null) {

            if (!currentPlayer.getPossibleMoves().contains(tile.position)){
                deselectPiece(false);
                return;
            }

            currentPlayer.setEffect(null);
            currentPlayer.showAllPossibleMoves(false);
            Tile initialSquare = (Tile) currentPlayer.getParent();
            currentPlayer.playerScore.add(Game.qb.getBestNextMove().equals(tile.position));
            tile.getChildren().add(currentPlayer);
            tile.occupied = true;
            initialSquare.getChildren().removeAll();
            initialSquare.occupied = false;
            currentPlayer.setPositon(tile.position);

            if(currentPlayer.color.equals("white")){
                if(currentPlayer.positon.getY()==0){

                game = false;
                showCongratulationsMessage();
                }
            }else{
                if(currentPlayer.positon.getY()==8){

                    game = false;
                    showCongratulationsMessage();
                }
            }

            deselectPiece(true);

        }
    }

    private void showCongratulationsMessage() {

        HelloApplication.openWon();

    }


    public void changeMoveType(int moveType){

        if(game) {
            Game.moveType = moveType;
            if (Game.moveType != 0) {
                getCurrentPlayer();
                deselectPiece(false);
                getCurrentPlayer();
                currentPlayer.highlightThis();
            } else {
                getCurrentPlayer();
                currentPlayer.deHighlightThis();
                deselectWalls();
                currentPlayer = null;
            }
        }

    }

    public void deselectWalls(){

        if(!currentWallTiles.isEmpty()){

            for(Tile tile : currentWallTiles){

                tile.deHighlightThis();

            }

            currentWallTiles = new ArrayList<>();

        }

    }

    public void placeWall(){

        if(game) {

            List<Node> pair2 = new ArrayList<>();
            List<Node> inOrder = new ArrayList<>();

            for (Tile tile : currentWallTiles) {

                pair2.add(tile.position);

            }

            if (moveType == 1) {

                List<Node> pair1 = new ArrayList<>();
                pair1.add(pair2.get(0));
                pair2.remove(pair1.get(0));
                Node separated = null;
                for (Node node : pair2) {

                    if ((node.getX() > pair1.get(0).getX() || node.getX() < pair1.get(0).getX()) && node.getY() == pair1.get(0).getY()) {

                        separated = node;

                    }

                }

                if (separated != null) {
                    pair1.add(separated);
                    pair2.remove(separated);

                    if (pair1.get(0).getX() > pair1.get(1).getX()) {

                        Collections.swap(pair1, 0, 1);

                    }
                    if (pair2.get(0).getX() > pair2.get(1).getX()) {

                        Collections.swap(pair2, 0, 1);

                    }

                    inOrder.addAll(pair1);
                    inOrder.addAll(pair2);

                    if (qb.placeWall(inOrder)) {

                        Tile tile = null;

                        for (int i = 0; i < inOrder.size(); i++) {

                            tile = getTileByNode(inOrder.get(i));

                            if (i % 2 == 0) {

                                tile.thickenBorderSide(Tile.Side.RIGHT);

                            } else {

                                tile.thickenBorderSide(Tile.Side.LEFT);

                            }

                        }

                    } else {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("WARNING");
                        alert.setHeaderText("Error");
                        alert.setContentText("It seems you you trapped a player!");
                        alert.showAndWait();
                    }

                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Error");
                    alert.setContentText("It seems you selected unconnected tiles");
                    alert.showAndWait();
                }


            } else if (moveType == 2) {

                List<Node> pair1 = new ArrayList<>();
                pair1.add(pair2.get(0));
                pair2.remove(pair1.get(0));
                Node separated = null;
                for (Node node : pair2) {

                    if ((node.getY() > pair1.get(0).getY() || node.getY() < pair1.get(0).getY()) && node.getX() == pair1.get(0).getX()) {

                        separated = node;

                    }

                }

                if (separated != null) {
                    pair1.add(separated);
                    pair2.remove(separated);

                    if (pair1.get(0).getY() > pair1.get(1).getY()) {

                        Collections.swap(pair1, 0, 1);

                    }
                    if (pair2.get(0).getY() > pair2.get(1).getY()) {

                        Collections.swap(pair2, 0, 1);

                    }

                    inOrder.addAll(pair1);
                    inOrder.addAll(pair2);

                    if (qb.placeWall(inOrder)) {

                        Tile tile = null;

                        for (int i = 0; i < inOrder.size(); i++) {

                            tile = getTileByNode(inOrder.get(i));

                            if (i % 2 == 0) {

                                tile.thickenBorderSide(Tile.Side.BOTTOM);

                            } else {

                                tile.thickenBorderSide(Tile.Side.TOP);

                            }

                        }

                    } else {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("WARNING");
                        alert.setHeaderText("Error");
                        alert.setContentText("It seems you you trapped a player!");
                        alert.showAndWait();
                    }

                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("WARNING");
                    alert.setHeaderText("Error");
                    alert.setContentText("It seems you selected unconnected tiles");
                    alert.showAndWait();
                }

            }

            for (Tile tile : currentWallTiles) {

                tile.deHighlightThis();

            }

            currentWallTiles = new ArrayList<>();
            currentPlayerColor = currentPlayerColor.equals("white") ? "black" : "white";

        }

    }

    public void getCurrentPlayer(){

        if(Game.qb.player1.color.equals(currentPlayerColor))currentPlayer = Game.qb.player1;

        else currentPlayer = Game.qb.player2;

    }

    public Tile getTileByNode(Node node){
        for(Tile tile : qb.tiles){
            if(tile.position.equals(node)){
                return tile;
            }
        }

        return null;
    }


    public String getScores(){
        int wScore = 0;
        int totalWMoves = currentPlayer.playerScore.size();
        for(Boolean val: currentPlayer.playerScore){

            if(val)
                wScore++;

        }

        int lScore = 0;
        int totalLMoves = Game.qb.getOppositePlayer().playerScore.size();
        for(Boolean val: Game.qb.getOppositePlayer().playerScore){

            if(val)
                wScore++;

        }

        return "Winner Score: "+wScore+"/"+totalWMoves+
                "\nLoser Score: "+lScore+"/"+totalLMoves;

    }





}

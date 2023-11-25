package com.example.quoridorjavafx.model.javaComponents;

import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player extends ImageView {

    int walls;
    Node positon;
    Node whicheverGoal;
    String color;

    List<Node> allSpecificGoals;
    public List<Boolean> playerScore;
    public Player(String color){

        this.color = color;
        walls = 10;
        allSpecificGoals = new ArrayList<>();
        playerScore = new ArrayList<>();
        addEventHandler();
        setImage();
    }

    public void setPiece(Image image){
        this.setImage(image);
    }

    public void setImage(){
        File file = new File("src/main/java/com/example/quoridorjavafx/model/javaComponents/images/" + this.color + "Pawn.png");
        String absolutePath = file.toURI().toString();
        this.setPiece(new Image(absolutePath));
    }
    private void addEventHandler(){
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getAllPossibleMoves();
            }
        });


    }

    public void getAllPossibleMoves() {}


    public void showAllPossibleMoves(boolean val){
        if(val){
            Glow glow = new Glow();
            glow.setLevel(0.5);
            for(Node node : this.getPossibleMoves()){
                Tile tile = getTileByNode(node);
                tile.setEffect(glow);

            }
        }
        else{
            for(Node node : this.getPossibleMoves()){
                Tile tile = getTileByNode(node);
                tile.setEffect(null);
            }
        }
    }

    public Tile getTileByNode(Node node){
        for(Tile tile : Game.qb.tiles){
            if(tile.position.equals(node)){
                return tile;
            }
        }

        return null;
    }

    public Player getPlayerByNode(Node node){
        for(Tile tile : Game.qb.tiles){
            if(tile.getChildren().size() == 0) continue;
//FIXME: players must be in different nodes for this to work
            if(tile.position.equals(node))
                return (Player) tile.getChildren().get(0);

        }
        return null;
    }

    public List<Node> getPossibleMoves(){

        List<Node> endNodes = new ArrayList<>(Game.qb.board.getEdges(Game.currentPlayer.positon));


        if(!Game.qb.nextMoveHasPlayerAdjecent()) {

            return endNodes;

        }
        endNodes.remove(Game.qb.getOppositePlayer().positon);
        endNodes.addAll(Game.qb.getOppositePlayerAdjecencyList());
        return endNodes;
    }

//    public void restoreNodes(){
//
//        List<Node> endNodes = Game.qb.board.getEdges(this.positon);
//        endNodes.removeAll(Game.restorator.delete);
//        endNodes.addAll(Game.restorator.add);
//
//    }

    public void highlightThis(){

        Glow glow = new Glow();
        glow.setLevel(0.5);

        Tile tile = getTileByNode(this.positon);
        tile.setEffect(glow);


    }

    public void deHighlightThis(){

        Tile tile = getTileByNode(this.positon);
        tile.setEffect(null);

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

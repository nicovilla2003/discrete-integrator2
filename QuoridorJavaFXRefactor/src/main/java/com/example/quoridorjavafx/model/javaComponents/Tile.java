package com.example.quoridorjavafx.model.javaComponents;

import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane {

    public Node position;
    boolean occupied;

 public Tile (Node node){
     position = node;
     this.occupied = false;


 }



//    public void showAllPossibleMoves(boolean val){
//
//            Glow glow = new Glow();
//            glow.setLevel(0.5);
//
//            for(Node node : this.getPossibleMoves()){
//
//                this.setEffect(null);
//                this.setBorder(new Border(new BorderStroke(Color.BLACK,
//                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
//
//            }
//
//    }




    public void highlightThis(){

        Glow glow = new Glow();
        glow.setLevel(0.5);
        this.setEffect(glow);


    }

    public void deHighlightThis(){

        this.setEffect(null);

    }

    public void thickenBorderSide(Side side){

        double v = this.getBorder().getStrokes().get(0).getWidths().getTop();
        double v1 = this.getBorder().getStrokes().get(0).getWidths().getRight();
        double v2 = this.getBorder().getStrokes().get(0).getWidths().getBottom();
        double v3 = this.getBorder().getStrokes().get(0).getWidths().getLeft();

        switch (side) {
            case TOP:
                this.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5,v1,v2,v3))));
                break;
            case BOTTOM:
                this.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(v,v1,5,v3))));
                break;
            case LEFT:
                this.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(v,v1,v2,5))));
                break;
            case RIGHT:
                this.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(v,5,v2,v3))));
                break;
        }

    }

    public enum Side {
        TOP, BOTTOM, LEFT, RIGHT
        // Add more sides if needed
    }

    public Node getPosition(){

     return position;

    }

    public void setPosition(Node node){

     position = node;

    }



}

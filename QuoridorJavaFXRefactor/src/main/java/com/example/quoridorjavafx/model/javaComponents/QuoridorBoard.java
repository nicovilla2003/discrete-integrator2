package com.example.quoridorjavafx.model.javaComponents;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuoridorBoard {

    public Graph board;

    GridPane quoridorBoard;

    public ArrayList<Tile> tiles = new ArrayList<>();
    public Player player1;
    public Player player2;

    public QuoridorBoard(GridPane quoridorBoard){
        this.quoridorBoard = quoridorBoard;
        player1 = new Player("white");
        player2 = new Player("black");
        board = createQuoridorBoard(this.quoridorBoard,9,9);
    }




    public Graph createQuoridorBoard(GridPane qBoard,int rows, int cols) {
        Graph board = new Graph();

        // Create nodes
        Node[][] nodes = new Node[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                Node node = new Node(x, y);

                Tile tile = new Tile(node);
                tile.setPrefHeight(100);
                tile.setPrefWidth(100);
                tile.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.5,1.5,1.5,1.5))));
                setTheme(tile, x, y);
                qBoard.add(tile, x, y, 1, 1);
                tiles.add(tile);
                if(y==8) {
                    player2.allSpecificGoals.add(node);
                    if (x == 4) {

                        player1.setPositon(node);
                        player2.setGoal(node);
                        addPlayer(tile, player1);

                    }
                }
                if(y == 0) {
                    player1.allSpecificGoals.add(node);
                    if (x == 4) {

                        player2.setPositon(node);
                        player1.setGoal(node);
                        addPlayer(tile, player2);
                    }
                }
                board.addNode(node);
                nodes[x][y] = node;

            }
        }

        // Create horizontal and vertical edges in both directions
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (x < rows - 1) {
                    board.addEdge(nodes[x][y], nodes[x + 1][y]);
                    board.addEdge(nodes[x + 1][y], nodes[x][y]);
//                    Edge verticalEdge = new Edge(nodes[x][y], nodes[x + 1][y]);
//                    board.addEdge(verticalEdge);
//                    Edge reverseVerticalEdge = new Edge(nodes[x + 1][y], nodes[x][y]);
//                    board.addEdge(reverseVerticalEdge);
                }
                if (y < cols - 1) {
                    board.addEdge(nodes[x][y], nodes[x][y + 1]);
                    board.addEdge(nodes[x][y + 1], nodes[x][y]);
//                    Edge horizontalEdge = new Edge(nodes[x][y], nodes[x][y + 1]);
//                    board.addEdge(horizontalEdge);
//                    Edge reverseHorizontalEdge = new Edge(nodes[x][y + 1], nodes[x][y]);
//                    board.addEdge(reverseHorizontalEdge);
                }
            }
        }

        return board;

    }

    private void setTheme(Tile tile, int x, int y){
              Color color1 = Color.web("#b1e4b9");
              Color color2 = Color.web("#70a2a3");

        if((x+y)%2==0){
            tile.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));


        }else{
            tile.setBackground(new Background(new BackgroundFill(color2, CornerRadii.EMPTY, Insets.EMPTY)));
        }

    }

    private void addPlayer(Tile tile, Player player){
        tile.getChildren().add(player);
        tile.occupied = true;
    }

    public boolean nextMoveHasPlayerAdjecent(){

       return board.areNodesAdjacent(player1.positon, player2.positon);

    }

    public Player getOppositePlayer(){

        if(Game.currentPlayer.equals(player1)){

            return player2;

        }
        return player1;

    }

    public List<Node> getOppositePlayerAdjecencyList(){
        List<Node> edges = new ArrayList<>();
        if(Game.currentPlayer.equals(player1)){

            edges.addAll(board.getEdges(player2.positon));
            edges.remove(player1.positon);

            return edges;

        }

        edges.addAll(board.getEdges(player1.positon));
        edges.remove(player2.positon);
        return edges;

    }

    public boolean placeWall(List<Node> nodes){
        if(Game.currentPlayer.walls>0) {
            Game.currentPlayer.walls--;
            return board.addWall(nodes);
        }

        return false;

    }

    private List<Node> shortestPathPossible(){

        List<List<Node>> listOfPaths= new ArrayList<>();
        for(Node node: Game.currentPlayer.allSpecificGoals){

            listOfPaths.add(board.dijkstraShortestPath(Game.currentPlayer.positon, node));

        }

        listOfPaths.sort(Comparator.comparingInt(List::size));

        return listOfPaths.get(0);

    }

    public Node getBestNextMove(){

        return shortestPathPossible().get(1);

    }



}

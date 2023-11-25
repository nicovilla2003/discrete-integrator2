package com.example.quoridorjavafx.model.modifiedClasses;

import com.example.quoridorjavafx.model.javaComponents.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuoridorBoardForTest {

    public Graph board;



    public ArrayList<Tile> tiles = new ArrayList<>();
    public PlayerForTest player1;
    public PlayerForTest player2;

    public QuoridorBoardForTest(){

        player1 = new PlayerForTest("white");
        player2 = new PlayerForTest("black");
        board = createQuoridorBoard(9,9);
    }




    public Graph createQuoridorBoard(int rows, int cols) {
        Graph board = new Graph();

        // Create nodes
        Node[][] nodes = new Node[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                Node node = new Node(x, y);


                if(y==8) {
                    player2.allSpecificGoals.add(node);
                    if (x == 4) {

                        player1.setPositon(node);
                        player2.setGoal(node);


                    }
                }
                if(y == 0) {
                    player1.allSpecificGoals.add(node);
                    if (x == 4) {

                        player2.setPositon(node);
                        player1.setGoal(node);

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



    public boolean nextMoveHasPlayerAdjecent(){

        return board.areNodesAdjacent(player1.positon, player2.positon);

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



    public List<Node> shortestPathPossible(int player){
        List<List<Node>> listOfPaths= new ArrayList<>();
        if(player == 1) {
            for (Node node : player1.allSpecificGoals) {

                listOfPaths.add(board.dijkstraShortestPath(player1.positon, node));

            }
        }else{

            for (Node node : player2.allSpecificGoals) {

                listOfPaths.add(board.dijkstraShortestPath(player2.positon, node));

            }

        }

        listOfPaths.sort(Comparator.comparingInt(List::size));

        return listOfPaths.get(0);

    }

    public Node getBestNextMove(int player){

        return shortestPathPossible(player).get(1);

    }

    public Node getNodeByXandY(int x, int y){

        for(Node node: board.getNodes()){

            if(node.getY()==y && node.getX() == x){

                return node;

            }

        }
        return null;

    }

    public List<Node> getAdjecencyList(int player){

        if(player==1){

            return board.getEdges(player1.positon);

        }else{

            return board.getEdges(player2.positon);

        }

    }

}

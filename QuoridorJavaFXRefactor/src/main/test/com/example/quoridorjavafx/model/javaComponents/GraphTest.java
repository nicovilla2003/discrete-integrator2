package com.example.quoridorjavafx.model.javaComponents;


import org.junit.Test;

import static org.junit.Assert.*;

public class GraphTest {

    private void setupScenary1() {

    }

    @Test
    public void testGraph() {
        Node goal = null;
        Node starter = null;
        Graph board = new Graph();

        // Create nodes
        Node[][] nodes = new Node[9][9];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                Node node = new Node(x, y);

                if(x==5 && y == 8){

                    goal = node;

                }
                if(x == 5 && y == 0){

                    starter = node;

                }
                board.addNode(node);
                nodes[x][y] = node;

            }
        }

        // Create horizontal and vertical edges in both directions
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                if (x < 9 - 1) {
                    board.addEdge(nodes[x][y], nodes[x + 1][y]);
                    board.addEdge(nodes[x + 1][y], nodes[x][y]);
                }
                if (y < 9 - 1) {
                    board.addEdge(nodes[x][y], nodes[x][y + 1]);
                    board.addEdge(nodes[x][y + 1], nodes[x][y]);
                }
            }
        }

        for(Node node: board.getNodes()){

            assertFalse(board.getEdges(node).isEmpty());

        }
        //Probar con diferentes puntos de partida y metas
            for (Node node : board.dijkstraShortestPath(starter,goal)){

                System.out.println("X: "+node.getX()+" Y: "+node.getY());
    
            }
        System.out.println();
            for (Node node : board.floydWarshall(starter,goal)){

            System.out.println("X: "+node.getX()+" Y: "+node.getY());

        }
        System.out.println();
            for (Node node : board.BFS(starter)){

            System.out.println("X: "+node.getX()+" Y: "+node.getY());

        }
        System.out.println();
            for (Node node : board.primMST(starter)){

            System.out.println("X: "+node.getX()+" Y: "+node.getY());

        } System.out.println();
        for (Node node : board.kruskalMST()){

            System.out.println("X: "+node.getX()+" Y: "+node.getY());

        }



    }



}

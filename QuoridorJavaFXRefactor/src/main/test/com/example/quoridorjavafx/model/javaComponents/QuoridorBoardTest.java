package com.example.quoridorjavafx.model.javaComponents;


import com.example.quoridorjavafx.model.modifiedClasses.QuoridorBoardForTest;
import javafx.scene.layout.GridPane;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuoridorBoardTest {


    @Test
    public void testQuorridorBoard() {

        QuoridorBoardForTest qb = new QuoridorBoardForTest();
        Node node1 = qb.getNodeByXandY(4,8);
        Node node2 = qb.getNodeByXandY(4,0);
        assertEquals(qb.player1.getPositon(),node1);
        assertEquals(qb.player2.getPositon(),node2);
        assertEquals(3, qb.getAdjecencyList(1).size());
        assertEquals(3, qb.getAdjecencyList(2).size());
    }


}
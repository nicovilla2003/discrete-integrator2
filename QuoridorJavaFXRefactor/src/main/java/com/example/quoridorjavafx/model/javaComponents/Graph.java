package com.example.quoridorjavafx.model.javaComponents;


import java.util.*;


public class Graph {
    private List<Node> nodes;


    private Map<Node, List<Node>> newEdges;

    public Graph() {
        nodes = new ArrayList<>();
        newEdges = new HashMap<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Node firstNode, Node secondNode) {
        if (!newEdges.containsKey(firstNode)) {

            newEdges.put(firstNode, new ArrayList<>());

        }
        newEdges.get(firstNode).add(secondNode);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Node> getEdges(Node node) {
        return newEdges.get(node);
    }


    public void removeEdge(Node node1, Node node2) {
        // Remove the nodes from each other's adjacency lists
        newEdges.get(node1).remove(node2);
        newEdges.get(node2).remove(node1);
    }

    private boolean addWall(Node node1, Node node2, Node node3, Node node4) {
        // Check if the nodes are adjacent
        if (!areNodesAdjacent(node1, node2) && !areNodesAdjacent(node3, node4)) {
            return false; // Nodes are not adjacent, cannot place a wall
        }

        // Remove edges between the nodes to represent the wall
        removeEdge(node1, node2);
        removeEdge(node2, node1);
        removeEdge(node3, node4);
        removeEdge(node4, node3);

        // Check if both players have a valid path after adding the wall
        if (!hasValidPath(Game.qb.player1.positon, Game.qb.player1.whicheverGoal) || !hasValidPath(Game.qb.player2.positon, Game.qb.player2.whicheverGoal)) {
            // If not, undo the wall by adding the edges back
            addEdge(node1, node2);
            addEdge(node2, node1);
            addEdge(node3, node4);
            addEdge(node4, node3);
            return false;
        }

        return true;
    }

    public boolean addWall(List<Node> nodes) {

        if (addWall(nodes.get(0), nodes.get(1), nodes.get(2), nodes.get(3))) {

            return true;

        }

        return false;

    }

    // Method to check if a player has a valid path to their goal
    private boolean hasValidPath(Node player, Node goal) {
        Set<Node> visited = new HashSet<>();
        dfs(player, goal, visited);
        return visited.contains(goal);
    }

    private void dfs(Node current, Node goal, Set<Node> visited) {
        visited.add(current);
        for (Node neighbor : newEdges.get(current)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, goal, visited);
            }
        }
    }



    // Helper method to check if nodes are adjacent
    public boolean areNodesAdjacent(Node node1, Node node2) {
        return newEdges.get(node1).contains(node2) && newEdges.get(node2).contains(node1);
    }

    public List<Node> dijkstraShortestPath(Node start, Node end) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> previousNodes = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Node node : nodes) {
            distances.put(node, Integer.MAX_VALUE);
            previousNodes.put(node, null);
        }

        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(end)) {
                break; // Reached the end node, exit the loop
            }

            for (Node neighbor : newEdges.get(current)) {
                int newDistance = distances.get(current) + 1; // Considering unweighted edges

                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previousNodes.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        // Reconstruct the shortest path using the previousNodes map
        LinkedList<Node> shortestPath = new LinkedList<>();
        Node current = end;
        while (current != null) {
            shortestPath.addFirst(current);
            current = previousNodes.get(current);
        }

        return shortestPath;

    }

    public List<Node> BFS(Node start) {
        List<Node> visited = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> seen = new HashSet<>();

        queue.add(start);
        seen.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            visited.add(current);

            for (Node neighbor : newEdges.get(current)) {
                if (!seen.contains(neighbor)) {
                    queue.add(neighbor);
                    seen.add(neighbor);
                }
            }
        }

        return visited;
    }

    public List<Node> primMST(Node start) {
        List<Node> mst = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> newEdges.get(n).size()));

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            mst.add(current);

            for (Node neighbor : newEdges.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return mst;
    }

    public List<Node> kruskalMST() {
        List<Node> mst = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(n -> newEdges.get(n).size()));

        for (Node node : nodes) {
            queue.add(node);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (!visited.contains(current)) {
                mst.add(current);
                visited.add(current);
            }
            for (Node neighbor : newEdges.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }

        return mst;
    }

    public List<Node> floydWarshall(Node start, Node end) {
        int size = nodes.size();
        boolean[][] connected = new boolean[size][size];
        Node[][] predecessor = new Node[size][size]; // To store predecessors

        // Initializing the connected matrix based on edges
        for (int i = 0; i < size; i++) {
            List<Node> edges = newEdges.get(nodes.get(i));
            for (int j = 0; j < size; j++) {
                connected[i][j] = edges != null && edges.contains(nodes.get(j));
                if (connected[i][j]) {
                    predecessor[i][j] = nodes.get(i); // Store predecessor
                }
            }
            connected[i][i] = true; // Node is connected to itself
        }

        // Applying Floyd-Warshall algorithm
        for (int k = 0; k < size; k++) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (!connected[i][j] && connected[i][k] && connected[k][j]) {
                        connected[i][j] = true;
                        predecessor[i][j] = nodes.get(k); // Update predecessor
                    }
                }
            }
        }

        // Reconstruct the shortest path
        List<Node> shortestPath = new ArrayList<>();
        if (connected[nodes.indexOf(start)][nodes.indexOf(end)]) {
            Node current = end;
            while (current != start) {
                shortestPath.add(0, current);
                current = predecessor[nodes.indexOf(start)][nodes.indexOf(current)];
            }
            shortestPath.add(0, start);
        }

        return shortestPath;
    }



}






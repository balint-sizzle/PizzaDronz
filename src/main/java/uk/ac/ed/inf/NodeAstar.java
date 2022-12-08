package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.List;
///https://stackabuse.com/graphs-in-java-a-star-algorithm/

public class NodeAstar implements Comparable<NodeAstar> {
    private Direction nextMove;
    private LngLat point;
    // Parent in the path
    private NodeAstar parent = null;

    private List<NodeAstar> neighbors;

    // Evaluation functions
    public double f = Double.MAX_VALUE;
    private double g = Double.MAX_VALUE;
    // Hardcoded heuristic

    NodeAstar(LngLat p, Direction nextMove) {
        this.point = p;
        this.nextMove = nextMove;
        this.neighbors = new ArrayList<>();
    }

    @Override
    public int compareTo(NodeAstar n) {
        return Double.compare(this.f, n.f);
    }

//    public static class Edge {
//        Edge(Direction d, Node node) {
//            this.node = node;
//        }
//
//        public int weight;
//        public Node node;
//    }

    public void addNeighbor(NodeAstar n){
        this.neighbors.add(n);
    }

    public double calculateCost(NodeAstar start, NodeAstar target){
        this.g = this.point.distanceTo(parent.point)+this.parent.getG();
        this.f = this.point.distanceTo(target.point)+this.g;
        return this.f;
    }

    public double getG() {
        return g;
    }

    public double getF(){
        return f;
    }

    public LngLat getPoint() {
        return point;
    }

    public void setG(double g) {
        this.g = g;
    }

    public Direction getNextMove(){
        return nextMove;
    }

    public List<NodeAstar> getNeighbors() {
        return neighbors;
    }

    public void setParent(NodeAstar parent) {
        this.parent = parent;
    }

    public NodeAstar getParent() {
        return parent;
    }

    public void setF(double f) {
        this.f = f;
    }
}
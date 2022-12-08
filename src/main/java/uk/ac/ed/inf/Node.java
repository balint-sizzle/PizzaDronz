package uk.ac.ed.inf;

import java.util.ArrayList;

public class Node {
    private Node prev;
    private Node next = null;
    private Node nextSegment;

    private LngLat val;


    public Node(LngLat next, LngLat val) {
        this.val = val;
        this.next = new Node(next);
    }

    public Node(LngLat val) {
        this.val = val;
    }

    private LngLat pickPointNearCorner(LngLat corner, LngLat[] poly) {
        for (Direction d : Direction.values()) {
            LngLat point = corner.nextPosition(d);
            if (!GeometryUtil.isInside(poly, poly.length, point)) {
                return point;
            }
        }
        return null;
    }

    public void optimise(LngLat target, ArrayList<LngLat[]> noFlyZones) {
        // if this.val -> this.next.val intersects any bounding boxes
        // find point near corner of bounding box
        // newPoint.next = this.next
        // this.next = newPoint
            for (LngLat[] poly : noFlyZones) {
                for (int i = 0; i < poly.length - 1; i++) {
                    if (GeometryUtil.doIntersect(this.val, this.next.val, poly[i], poly[i + 1])) {
                        System.out.println("intersection");
                        double closestDst = Double.MAX_VALUE;
                        LngLat closestCorner = new LngLat();
                        for (LngLat corner : poly) {
                            if (corner.distanceTo(this.val) + corner.distanceTo(target) < closestDst) {
                                closestDst = corner.distanceTo(this.val) + corner.distanceTo(target);
                                closestCorner = corner;
                            }
                        }
                        LngLat interNodePoint = pickPointNearCorner(closestCorner, poly);
                        Node interNode = new Node(interNodePoint);
                        interNode.next = this.next;
                        interNode.optimise(target, noFlyZones);
                        this.next = interNode;
                        this.optimise(target, noFlyZones);
                    }
                }
            }
        }


    private boolean valid(ArrayList<LngLat[]> noFlyZones) {
        boolean valid = true;
        Node currentNode = this;
        while (this.next != null) {
            for (LngLat[] poly : noFlyZones) {
                for (int i = 0; i < poly.length - 1; i++) {
                    if (GeometryUtil.doIntersect(currentNode.val, currentNode.next.val, poly[i], poly[i + 1])) {
                        valid = false;
                    }
                }
            }
        }
        return valid;
    }

    public void avoidNoFlyZones(LngLat target, ArrayList<LngLat[]> noFlyZones) {

    }

    public Node getNext() {
        return this.next;
    }

    public LngLat getVal() {
        return val;
    }
}

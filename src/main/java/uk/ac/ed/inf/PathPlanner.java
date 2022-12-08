package uk.ac.ed.inf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.mapbox.geojson.*;

public class PathPlanner {

    private final int MAX_STEPS = 2000;
    private final String NO_FLY_ZONES_PATH = "no-fly-zones.geojson";

    private ArrayList<Direction> backTrack(NodeAstar n, NodeAstar start) {
        ArrayList<Direction> flightPath = new ArrayList<>();

        while (!n.getPoint().closeTo(start.getPoint())) {
            n = n.getParent();
            flightPath.add(n.getNextMove());
            System.out.println(n.getNextMove());
        }
        flightPath.add(n.getNextMove());
        return flightPath;
    }

    public NodeAstar aStar(NodeAstar start, NodeAstar target, ArrayList<LngLat[]> NoFlyZones) {
        PriorityQueue<NodeAstar> closedList = new PriorityQueue<>();
        PriorityQueue<NodeAstar> openList = new PriorityQueue<>();
        int steps = 0;

        start.setG(0);
        start.setF(start.getPoint().distanceTo(target.getPoint()));
        openList.add(start);
        double fov = 90;

        while (!openList.isEmpty()) {
            double best_f = Double.MAX_VALUE;
            NodeAstar best_n = null;
            for (NodeAstar n : openList) {
                if (n.getF() < best_f) {
                    best_n = n;
                    best_f = n.getF();
                }
            }
            NodeAstar n = best_n;

            if (n.getPoint().closeTo(target.getPoint())) {
                System.out.println("Success");
                return n; // goal node

            }
            for (Direction d : Direction.values()) {
                LngLat step = n.getPoint().nextPosition(d);
                if (validStep(step, NoFlyZones) && !(d == Direction.Hover) && fov > Math.abs(d.angle() - n.getNextMove().angle())) {
                    n.addNeighbor(new NodeAstar(step, d));
                }

            }
            for (NodeAstar edge : n.getNeighbors()) {
                double tentativeGScore = n.getG() + n.getPoint().distanceTo(edge.getPoint());

                if (tentativeGScore < edge.getG()) {
                    edge.setParent(n);
                    edge.setG(tentativeGScore);
                    edge.setF(tentativeGScore + edge.getPoint().distanceTo(target.getPoint()));
                    if (!openList.contains(edge)) {
                        openList.add(edge);
                    }
                }

            }

            openList.remove(n);
            System.out.println(n.getF());

            //steps++;
            //System.out.println(n.getG());

        }
        return null;
    }


    public ArrayList noFlyZonesFromFile() throws IOException {
        ArrayList<LngLat[]> nfZones = new ArrayList<>();
        // geojson is stored as a feature collection of geometries
        FeatureCollection rawFC = FeatureCollection.fromJson(IOUtil.readGeoJson(NO_FLY_ZONES_PATH));

        for (Feature f : rawFC.features()) { // for each no fly zone
            // Polygon object is recognised as a generic Geometry. No good since the superclass Geometry has no .coordinates()
            Polygon MapBoxPolygon = ((Polygon) f.geometry());
            // get(0) is needed since coordinates is structured like [[Point p1 ... Point]]
            List MapBoxList = MapBoxPolygon.coordinates().get(0);
            LngLat[] poly = new LngLat[MapBoxList.size()];
            for (int i = 0; i < MapBoxList.size(); i++) {
                Point p = (Point) MapBoxList.get(i);
                List pCoord = p.coordinates();
                poly[i] = new LngLat((double) pCoord.get(0), (double) pCoord.get(1));

            }
            nfZones.add(poly);

        }
        return nfZones;
    }

    private boolean validStep(LngLat p, ArrayList<LngLat[]> noFlyZones) {
        for (LngLat[] poly : noFlyZones) {
            for (LngLat corner : poly) {
                if (corner.closeTo(p)) {
                    return false;
                }
            }
            if (GeometryUtil.isInside(poly, poly.length, p)) {
                return false;
            }
        }
        return true;
    }

    /* plan for flight path:
        draw line to goal, see if it intersects any no fly zones.
        if yes, find the nearest corner of the nearest no fly zone and bee line to that
        store that coordinate as a taken corner
        keep repeating until there's a straight line to the goal
     */

    private LngLat findNearestCornerOfIntersectingNoFlyZone(LngLat head, LngLat target, ArrayList<LngLat[]> noFlyZones) {

        return null;
    }





    public Feature generateFlightPath(LngLat start, LngLat target) throws IOException {

        List flightPath = new ArrayList<Point>();
        ArrayList<LngLat> landmarks = new ArrayList<>();
        ArrayList<LngLat[]> noFlyZones = noFlyZonesFromFile();

        Node init = new Node(target, start);

        init.optimise(target, noFlyZones);

        Node currentNode = init;
        while (!(currentNode.getNext()==null)) {
            landmarks.add(currentNode.getVal());
            landmarks.add(currentNode.getNext().getVal());
            currentNode = currentNode.getNext();
        }

        LngLat dronePos = start;
        flightPath.add(Point.fromLngLat(dronePos.lng, dronePos.lat));
        for (LngLat l : landmarks){
            while(!dronePos.closeTo(l)){
                double bestStep = Double.MAX_VALUE;
                Direction bestD = Direction.North;
                for (Direction d : Direction.values()) {
                    LngLat nextPos = dronePos.nextPosition(d);

                    if (!validStep(nextPos, noFlyZones) || d == Direction.Hover) {
                        continue;
                    }
                    if (nextPos.distanceTo(target) < bestStep) {
                        bestStep = nextPos.distanceTo(target);
                        bestD = d;
                    }
                }
                dronePos = dronePos.nextPosition(bestD);
                flightPath.add(Point.fromLngLat(dronePos.lng, dronePos.lat));
            }
        }
//        flightPath.add(Point.fromLngLat(lineHead.lng, lineHead.lat));
//
//        lineHead = findNearestCornerOfIntersectingNoFlyZone(lineHead, target, noFlyZones);
//
//        while (!(lineHead==null)) {
//            flightPath.add(Point.fromLngLat(lineHead.lng, lineHead.lat));
//            lineHead = findNearestCornerOfIntersectingNoFlyZone(lineHead, target, noFlyZones);
//        }

        LineString lineString = LineString.fromLngLats(flightPath);
        Feature feature = Feature.fromGeometry(lineString);
        return feature;
    }

//    public Feature generateFlightPath(LngLat start, LngLat goal) throws IOException {
//        List flightPath = new ArrayList<Point>();
//        ArrayList<LngLat[]> noFlyZones = noFlyZonesFromFile();
//        Node targetN = new Node(goal, Direction.North);
//        Node startN = new Node(start, Direction.North);
//        Node n = aStar(startN, targetN, noFlyZones);
//        ArrayList<Direction> movesArray = backTrack(n, startN);
//        flightPath.add(Point.fromLngLat(start.lng, start.lat));
//        LngLat currentStep = start;
//        for (int i= movesArray.size()-2;i>=0;i--){
//            LngLat step = currentStep.nextPosition(movesArray.get(i));
//            flightPath.add(Point.fromLngLat(step.lng, step.lat));
//            currentStep = step;
//        }
//
//        LineString lineString = LineString.fromLngLats(flightPath);
//        Feature feature = Feature.fromGeometry(lineString);
//        String featureStr = FeatureCollection.fromFeature(feature).toJson();
//        return feature;
//
//    }

//    public Feature generateFlightPath(LngLat fromCords, LngLat toCords) throws IOException {
//        List flightPath = new ArrayList<Point>();
//        ArrayList<LngLat[]> noFlyZones = noFlyZonesFromFile();
//
//        LngLat currentPos = new LngLat(fromCords.lng, fromCords.lat);
//        for (int i = 0; i <= MAX_STEPS; i++) {
//            System.out.println(i);
//            flightPath.add(Point.fromLngLat(currentPos.lng, currentPos.lat));
//            if (currentPos.closeTo(toCords)) {
//                System.out.println("success");
//                break;
//            }
//            double dstToTarget = currentPos.distanceTo(toCords);
//            double bestStep = dstToTarget * 2;
//            Direction bestD = Direction.Hover;
//
//            for (Direction d : Direction.values()) {
//                LngLat nextPos = currentPos.nextPosition(d);
//
//                if (!validStep(nextPos, noFlyZones) || d == Direction.Hover) {
//                    continue;
//                }
//                if (nextPos.distanceTo(toCords) < bestStep) {
//                    bestStep = nextPos.distanceTo(toCords);
//                    bestD = d;
//                }
//            }
//            currentPos = currentPos.nextPosition(bestD);
//
//        }
//        LineString lineString = LineString.fromLngLats(flightPath);
//        Feature feature = Feature.fromGeometry(lineString);
//        return feature;
//    }
}

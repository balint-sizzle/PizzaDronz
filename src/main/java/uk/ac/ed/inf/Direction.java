package uk.ac.ed.inf;

public enum Direction {
    Hover(null),
    North(0.0),
    NorthNorthEast(22.5),
    NorthEast(45.0),
    EastNorthEast(67.5),
    East(90.0),
    EastSouthEast(112.5),
    SouthEast(135.0),
    SouthSouthEast(157.5),
    South(180.0),
    SouthSouthWest(202.5),
    SouthWest(225.0),
    WestSouthWest(247.5),
    West(270.0),
    WestNorthWest(295.5),
    NorthWest(315.0),
    NorthNorthWest(337.5);
    public final Double angle;

    Direction() {
        this(0.0);
    }

    Direction(Double angle) {
        this.angle = angle;
    }

    public Double angle() {

        return this.angle;
    }
}

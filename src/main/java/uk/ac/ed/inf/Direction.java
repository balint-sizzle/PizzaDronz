package uk.ac.ed.inf;

public enum Direction {
    North(),
    NorthNorthEast(22.5),
    NorthEast(45.0),
    EastNorthEast(67.5),
    East(90),
    EastSouthEast(112.5),
    SouthEast(135.0),
    SouthSouthEast(157.5),
    South(180),
    SouthSouthWest(-157.5),
    SouthWest(-135.0),
    WestSouthWest(-112.5),
    West(-90),
    WestNorthWest(-67.5),
    NorthWest(-45),
    NorthNorthWest(-22.5);
    public final double angle;

    Direction() {
        this(0.0);
    }

    Direction(double angle) {
        this.angle = angle;
    }

    public double angle() {
        return this.angle;
    }
}

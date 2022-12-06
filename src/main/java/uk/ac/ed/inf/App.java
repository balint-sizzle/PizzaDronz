package uk.ac.ed.inf;

public class App {
    public static void main(String args[]){
        LngLat zero = new LngLat(0,0);
        System.out.println(zero.nextPosition(Direction.East).lng);
        System.out.println(zero.nextPosition(Direction.West).lng);
    }
}

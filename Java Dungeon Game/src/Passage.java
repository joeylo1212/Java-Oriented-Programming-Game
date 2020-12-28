package src;

import java.util.ArrayList;

public class Passage extends Structure{
    Room rmX;
    Room rmY;
    ArrayList<Integer> listX = new ArrayList<>();
    ArrayList<Integer> listY = new ArrayList<>();

    Passage( ){
        rmX = new Room("1");
        rmY = new Room("2");
    }

    void setID(int rmX, int rmY){
        this.rmX.setId(rmX);
        this.rmY.setId(rmY);
    }

    void addX(int PosX) {
        listX.add(PosX);
    }
    void addY(int PosY) {
        listY.add(PosY);
    }

    public ArrayList<Integer> getX( ) {
        return listX;
    }
    public ArrayList<Integer> getY( ) {
        return listY;
    }
}
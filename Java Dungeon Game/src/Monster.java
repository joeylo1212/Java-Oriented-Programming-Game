package src;

public class Monster extends Creature{


    int serial;
    int maxHit;

    public Monster(String name, int room, int serial){
        this.name = name;
        this.room = room;
        this.serial = serial;
    }

    void setMaxHit(int maxHit){
        this.maxHit = maxHit;
    }

    int getMaxHit(){
        return maxHit;
    }

    int getID(){
        return serial;
    }
}
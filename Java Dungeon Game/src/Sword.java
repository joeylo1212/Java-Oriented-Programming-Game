package src;

public class Sword extends Item{

    int room;
    char type;

    public Sword(String name){
        this.name = name;
        type = ')';
    }

    void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
        System.out.println("Sword room: " + this.room + " Sword Serial: " + this.serial);
    }

    int getRoom(){
        return room;
    }
    char getType(){
        return type;
    }
}
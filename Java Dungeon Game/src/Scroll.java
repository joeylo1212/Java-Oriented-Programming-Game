package src;

public class Scroll extends Item{

    int room;
    char type;

    public Scroll(String name){
        this.name = name;
        type = '?';
    }

    void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
    }

    int getRoom(){
        return room;
    }

    char getType(){
        return type;
    }
}
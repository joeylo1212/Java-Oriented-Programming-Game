package src;

public class Room extends Structure{
    int id;
    Room(String s){
        System.out.println("Room: " + s);
        id = Integer.parseInt(s);
    }

    void setId(int room){
        System.out.println("Room ID: " + room);
        id = room;
    }

    void setCreature(Creature monster){
    }

    int getId(){
        return id;
    }
}
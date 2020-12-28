package src;

public class Armor extends Item{
    int room;
    char type;

    public Armor(String name){
        this.name = name;
        type = ']';
        System.out.println("Armor name:" + name);
    }

    void setName(String name_set){
        name = name_set;
        System.out.println("Armor new name: " + name_set);
    }

    void setID(int room, int serial){
        this.room = room;
        this.serial = serial;
        System.out.println("Armor Room:" + room + "Armor Serial: " + serial);
    }

    //getters
    int getRoom(){
        return room;
    }
    char getType(){
        return type;
    }
}
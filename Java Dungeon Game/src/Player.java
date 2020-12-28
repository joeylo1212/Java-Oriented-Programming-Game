package src;

public class Player extends Creature{
    int serial;
    int maxHit;
    Item weapon;
    Item armor;

    public Player(String name, int room, int serial){
        this.name = name;
        this.room = room;
        this.serial = serial;
    }

    void setWeapon(Item sword){
        weapon = sword;
    }

    void setArmor(Item armor){
        this.armor = armor;
    }

    Item getWeapon( ){
        return weapon;
    }
    Item getArmor( ){
        return armor;
    }

    void setMaxHit(int maxHit){
        this.maxHit = maxHit;
    }
    int getMaxHit(){
        return maxHit;
    }
}
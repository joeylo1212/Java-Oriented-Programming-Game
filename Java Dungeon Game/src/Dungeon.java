package src;

public class Dungeon{

    String name;
    int width;
    int gameHeight;
    int bottomHeight;
    int topHeight;

    public Dungeon(String name, int width, int gameHeight, int topHeight, int bottomHeight){
        this.name = name;
        this.width = width;
        this.gameHeight = gameHeight;
        this.bottomHeight = bottomHeight;
        this.topHeight = topHeight;
    }

    int getWidth( ) {
        return width;
    }
    int getGameHeight( ) {
        return gameHeight;
    }
    int getTopHeight( ) {
        return topHeight;
    }
    int getBottomHeight( ) {
        return bottomHeight;
    }

    void getDungeon(String name, int width, int gameHeight){
    }

    void addRoom(Room r){
    }

    void addCreature(Creature c){
    }

    void addPassage(Passage passage){
    }

    void addItem(Item item){
    }

}
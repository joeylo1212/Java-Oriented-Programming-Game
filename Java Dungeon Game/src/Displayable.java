package src;

public class Displayable{
    int tempPosX;
    int tempPosY;
    int posX;
    int posY;
    int width;
    int height;
    char Type;
    int intVal;
    String name;

    Displayable(){
    }

    void setInvisible(){
    }

    void setVisible(){
    }

    void setMaxHit(int maxHit){
        System.out.println("maxHit: " + maxHit);
    }

    void setHpMove(int hpMoves){
        System.out.println("hpMoves: " + hpMoves);
    }

    void setHp(int Hp){
        System.out.println("setHp: " + Hp);
    }

    void setType(char t){
        Type = t;
        System.out.println("Type: " + t);
    }

    void setIntValue(int v){
        intVal = v;
    }
    int getIntValue( ) {
        return intVal;
    }

    void SetPosX(int x){
        posX = x;
        System.out.println("PosX: " + x);
    }

    void setPosY(int y){
        posY = y;
        System.out.println("PosY: " + y);
    }

    void SetWidth(int x){
        width = x;
        System.out.println("Width: " + x);
    }

    void setHeight(int y){
        height = y;
        System.out.println("Height: " + y);
    }

    String getName( ) {
        return name;
    }

    int getPosX( ){
        return posX;
    }

    int getPosY( ){
        return posY;
    }

    int getWidth( ){
        return width;
    }

    int getHeight( ){
        return height;
    }

    char getType( ) {
        return Type;
    }

    void setTempPosX(int x) {
        tempPosX = x;
    }

    void setTempPosY(int y) {
        tempPosY = y;
    }
}
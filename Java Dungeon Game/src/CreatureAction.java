package src;

import java.util.Random;

public class CreatureAction extends Action{
    String action;
    Creature creature;
    CreatureAction(Creature owner){
        creature = owner;
    }
    void setAction(String action){
        this.action = action;
    }
    String getAction(){
        return action;
    }

    void Remove(String name, Creature owner){
        System.out.println("Remove CA type: " + owner.getType());
        int rmX = 0;
        int rmY = 0;

        Monster monster = (Monster) owner;
        int j = 0;
        while (j < Rogue.roomList.size()) {
            if (Rogue.roomList.get(j).getId() == monster.getRoom()){
                rmX = Rogue.roomList.get(j).getPosX();
                rmY = Rogue.roomList.get(j).getPosY();
            }
            j++;
        }
        Rogue.monsterDeath(monster, rmX, rmY);
        Rogue.infoUpdate(message);
    }

    void YouWin(String name, Creature owner){
        System.out.println("YouWin CA name: " + name);
        Rogue.infoUpdate(message);
    }

    void UpdateDisplay(String name, Creature owner){
        System.out.println("UpdateDisplay CA Name: " + name);
        if(owner.getType() == '@') {
            Rogue.updateTopDisplay();
        }
        if (message != null)
            Rogue.infoUpdate(message);
    }

    void Teleport(String name, Creature owner){
        System.out.println("Teleport CA name: " + name);
        Random rand = new Random();
        int X = rand.nextInt(Rogue.getW());
        int Y = rand.nextInt(Rogue.getGH());
        int rmX = 0;
        int rmY = 0;
        int i = 0;
        while (i < Rogue.roomList.size()) {
            if (Rogue.roomList.get(i).getId() == owner.getRoom()){
                rmX = Rogue.roomList.get(i).getPosX();
                rmY = Rogue.roomList.get(i).getPosY();
            }
            i++;
        }
        Char temp = Rogue.getDisplayGrid().topTop( rmX + owner.getPosX(), rmY + owner.getPosY() + Rogue.getTopHeight() );
        Rogue.getDisplayGrid().removeObject( rmX + owner.getPosX(), rmY + owner.getPosY() + Rogue.getTopHeight() );

        while(true) {
            if (Rogue.getDisplayGrid().topTop(X, Y + Rogue.getTopHeight()).getChar() == '#') {
                if ((X != (rmX + owner.getPosX())) || (Y != (rmY + owner.getPosY()))) {
                    owner.setTempPosX(X);
                    owner.setTempPosY(Y + Rogue.getTopHeight());
                    owner.SetPosX(X - rmX);
                    owner.setPosY(Y - rmY);
                    Rogue.getDisplayGrid().addObject(temp, X, Y + Rogue.getTopHeight());
                    break;
                } else {
                    X = rand.nextInt(Rogue.getW());
                    Y = rand.nextInt(Rogue.getGH());
                }
            } else if (Rogue.getDisplayGrid().topTop(X, Y + Rogue.getTopHeight()).getChar() == '.') {
                if ((X != (rmX + owner.getPosX())) || (Y != (rmY + owner.getPosY()))) {
                    owner.setTempPosX(X);
                    owner.setTempPosY(Y + Rogue.getTopHeight());
                    owner.SetPosX(X - rmX);
                    owner.setPosY(Y - rmY);
                    Rogue.getDisplayGrid().addObject(temp, X, Y + Rogue.getTopHeight());
                    break;
                } else {
                    X = rand.nextInt(Rogue.getW());
                    Y = rand.nextInt(Rogue.getGH());
                }
            } else {
                X = rand.nextInt(Rogue.getW());
                Y = rand.nextInt(Rogue.getGH());
            }
        }
        Rogue.infoUpdate(message);
    }

    void ChangedDisplayedType(String name, Creature owner){
        System.out.println("ChangedDisplayedType CA name: " + name);
        ObjectDisplayGrid displayGrid = Rogue.getDisplayGrid();
        if(owner.getType() == '@') {
            displayGrid.removeObject(displayGrid.getPlayerX(), displayGrid.getPlayerY());
            displayGrid.addObject(new Char(chVal), displayGrid.getPlayerX(), displayGrid.getPlayerY());
        }else{
            int rmX = 0;
            int rmY = 0;
            int i;
            for(i = 0; i < Rogue.roomList.size(); i++){
                if (Rogue.roomList.get(i).getId() == owner.getRoom()){
                    rmX = Rogue.roomList.get(i).getPosX();
                    rmY = Rogue.roomList.get(i).getPosY();
                }
            }
            displayGrid.removeObject(owner.getPosX() + rmX, owner.getPosY() + rmY);
            displayGrid.addObject(new Char(chVal), owner.getPosX() + rmX, owner.getPosY() + rmY);
        }
        Rogue.infoUpdate(message);
    }

    void EndGame(String name, Creature owner){
        System.out.println("EndGame CA name: " + name);
        KeyStrokePrinter.setDead();
        Rogue.infoUpdate(message);
    }

    void DropPack(String name, Creature owner){
        System.out.println("DropPack CA name: " + name);
        Rogue.drop(0);
        Rogue.infoUpdate(message);
    }

    void EmptyPack(String name, Creature owner){
        System.out.println("Empty pack name: " + name);
        Rogue.removeAll();
        Rogue.infoUpdate(message);
    }
}
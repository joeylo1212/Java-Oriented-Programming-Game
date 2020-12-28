package src;

import java.util.ArrayList;

public class Creature extends Displayable{

    int Hp;
    int HP_move;
    int room;
    ArrayList<CreatureAction> deathActionList = new ArrayList<CreatureAction>();
    ArrayList<CreatureAction> hitActionList = new ArrayList<CreatureAction>();

    public Creature(){
    }

    void setHp(int h){
        Hp = h;
        System.out.println("Creature HP: " + Hp);
    }

    void setHpMove(int hpm){
        HP_move = hpm;
        System.out.println("Creature HP_Move: " + hpm);
    }

    int getHpMoves(){
        return HP_move;
    }

    void setDeathAction(CreatureAction da){
        deathActionList.add(da);
    }

    void setHitAction(CreatureAction ha){
        hitActionList.add(ha);
    }

    //getters
    int getHp(){
        return Hp;
    }
    int getRoom(){
        return room;
    }

    void doHitAction() {
        if (hitActionList.isEmpty())
            return;

        for (CreatureAction creatureAction : hitActionList) {
            switch (creatureAction.getAction()) {
                case "ChangeDisplayType" -> creatureAction.ChangedDisplayedType("", creatureAction.creature);
                case "Remove" -> creatureAction.Remove("", creatureAction.creature);
                case "Teleport" -> creatureAction.Teleport("", creatureAction.creature);
                case "UpdateDisplay" -> creatureAction.UpdateDisplay("", creatureAction.creature);
                case "YouWin" -> creatureAction.YouWin("", creatureAction.creature);
                case "DropPack" -> creatureAction.DropPack("", creatureAction.creature);
                case "EmptyPack" -> creatureAction.EmptyPack("", creatureAction.creature);
                case "EndGame" -> creatureAction.EndGame("", creatureAction.creature);
            }
        }
    }

    void doDeathAction(){
        if (deathActionList.isEmpty())
            return;

        for (CreatureAction creatureAction : deathActionList) {
            switch (creatureAction.getAction()) {
                case "ChangeDisplayedType" -> creatureAction.ChangedDisplayedType("", creatureAction.creature);
                case "Remove" -> creatureAction.Remove("", creatureAction.creature);
                case "Teleport" -> creatureAction.Teleport("", creatureAction.creature);
                case "UpdateDisplay" -> creatureAction.UpdateDisplay("", creatureAction.creature);
                case "YouWin" -> creatureAction.YouWin("", creatureAction.creature);
                case "DropPack" -> creatureAction.DropPack("", creatureAction.creature);
                case "EmptyPack" -> creatureAction.EmptyPack("", creatureAction.creature);
                case "EndGame" -> creatureAction.EndGame("", creatureAction.creature);
            }

        }
    }
}
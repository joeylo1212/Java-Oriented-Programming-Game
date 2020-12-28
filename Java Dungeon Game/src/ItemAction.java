package src;

public class ItemAction extends Action{
    Item owner;
    String action;

    public ItemAction(Item owner){
        this.owner = owner;
    }

    void setAction(String action){
        this.action = action;
    }

    void BlessCurseOwner(Creature owner){
        Player currPlayer;
        currPlayer = Rogue.getPlayers().get(0);
        switch (chVal) {
            case 'a':
                if (currPlayer.getArmor() == null) {
                    message = "Armor unused error";
                }else{
                    int newVal;
                    newVal = currPlayer.getArmor().getIntValue();
                    newVal += intVal;
                    currPlayer.getArmor().setIntValue(newVal);
                    message = currPlayer.getArmor().getName() + " is cursed " + (intVal) + " taken from its effectiveness";
                }
                break;
            case 'w':
                if (currPlayer.getWeapon() != null) {
                    int newVal;
                    newVal = currPlayer.getWeapon().getIntValue();
                    newVal += intVal;
                    currPlayer.getWeapon().setIntValue(newVal);
                    message = currPlayer.getWeapon().getName() + " is cursed " + (intVal) + " taken from its effectiveness";
                } else {
                    message = "Weapon unused error";
                }
                break;
        }
        Rogue.infoUpdate(message);
    }

    void Hallucinate(Creature owner){
        Rogue.infoUpdate("Hallucinating for " + intVal + " moves");
        KeyStrokePrinter.sethMoveCount(intVal);
    }
}
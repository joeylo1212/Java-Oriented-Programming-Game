package src;
import java.util.ArrayList;

public class Item extends Displayable{

    int serial;
    ArrayList<ItemAction> itemActionList = new ArrayList<ItemAction>();
    Creature owner;

    void setOwner(Creature owner){
        System.out.println("C Owner: " + owner);
        this.owner = owner;
    }

    void addItemAction(ItemAction itemAction){
        itemActionList.add(itemAction);
    }

    void doItemAction(){
        if (itemActionList.isEmpty())
            return;
        for (ItemAction itemAction : itemActionList) {
            if (itemAction.action.equals("Hallucinate"))
                itemAction.Hallucinate(owner);
            else
                itemAction.BlessCurseOwner(owner);
        }
    }

}
package src;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

public class DungeonXMLHandler extends DefaultHandler {

    private StringBuilder data = null;

    ArrayList<Room> roomList = new ArrayList<Room>();
    ArrayList<Monster> monsterList = new ArrayList<Monster>();
    ArrayList<Dungeon> dungeonList = new ArrayList<Dungeon>();
    ArrayList<Item> itemList = new ArrayList<Item>();
    ArrayList<Player> playerList = new ArrayList<Player>();
    ArrayList<Passage> passageList = new ArrayList<Passage>();
    ArrayList<Scroll> scrollList = new ArrayList<Scroll>();
    ArrayList<Armor> armorList = new ArrayList<Armor>();
    ArrayList<Sword> swordList = new ArrayList<Sword>();

    private Dungeon dungeonBeingParsed = null;
    private Room roomBeingParsed = null;
    private Monster monsterBeingParsed = null;
    private Player playerBeingParsed = null;
    private CreatureAction CABeingParsed = null;
    private Item itemBeingParsed = null;
    private ItemAction IABeingParsed = null;
    private Passage passageBeingParsed = null;

    private boolean bPosX = false;
    private boolean bPosY = false;
    private boolean bType = false;
    private boolean bHp = false;
    private boolean bMaxHit = false;
    private boolean bVisible = false;
    private boolean bInvisible = false;
    private boolean bHpMoves = false;
    private boolean bWidth = false;
    private boolean bHeight = false;
    private boolean bActionCharVal = false;
    private boolean bActionMessage = false;
    private boolean bActionIntVal = false;
    private boolean bItemIntVal = false;

    //getters
    public ArrayList<Room> getRoomList() {
        return roomList;
    }
    public ArrayList<Monster> getMonsterList() {
        return monsterList;
    }
    public ArrayList<Dungeon> getDungeonList(){
        return dungeonList;
    }
    public ArrayList<Item> getItemList(){
        return itemList;
    }
    public ArrayList<Player> getPlayerList(){
        return playerList;
    }
    public ArrayList<Passage> getPassageList() {
        return passageList;
    }
    public ArrayList<Scroll> getScrollList() {
        return scrollList;
    }
    public ArrayList<Armor> getArmorList(){
        return armorList;
    }
    public ArrayList<Sword> getSwordList(){
        return swordList;
    }

    public DungeonXMLHandler() {
    }

    Dungeon newDungeon;
    Room newRoom;
    Monster newMonster;
    Player newPlayer;
    CreatureAction newCAction;
    Scroll newScroll;
    Armor newArmor;
    Sword newSword;
    ItemAction newIAction;
    Passage newPassage;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Dungeon")) {
            newDungeon = new Dungeon(attributes.getValue("name"), Integer.parseInt(attributes.getValue("width")), Integer.parseInt(attributes.getValue("gameHeight")), Integer.parseInt(attributes.getValue("topHeight")), Integer.parseInt(attributes.getValue("bottomHeight")));
            dungeonBeingParsed = newDungeon;
            dungeonList.add(newDungeon);
        }else if(qName.equalsIgnoreCase("Room")) {
            newRoom = new Room(attributes.getValue("room"));
            roomBeingParsed = newRoom;
            roomList.add(newRoom);
        }else if(qName.equalsIgnoreCase("Monster")) {
            newMonster = new Monster(attributes.getValue("name"), Integer.parseInt(attributes.getValue("room")), Integer.parseInt(attributes.getValue("serial")));
            monsterBeingParsed = newMonster;
            monsterList.add(newMonster);
        }else if(qName.equalsIgnoreCase("Player")) {
            newPlayer = new Player(attributes.getValue("name"), Integer.parseInt(attributes.getValue("room")), Integer.parseInt(attributes.getValue("serial")));
            newPlayer.setType('@');
            playerBeingParsed = newPlayer;
            playerList.add(newPlayer);
        }else if(qName.equalsIgnoreCase("CreatureAction")) {
            if(monsterBeingParsed != null) {
                newCAction = new CreatureAction(monsterBeingParsed);
                newCAction.setAction(attributes.getValue("name"));
                if(attributes.getValue("type").equals("death")) {
                    monsterBeingParsed.setDeathAction(newCAction);
                }else if(attributes.getValue("type").equals("hit")) {
                    monsterBeingParsed.setHitAction(newCAction);
                }
                CABeingParsed = newCAction;
            }else if(playerBeingParsed != null) {
                newCAction = new CreatureAction(playerBeingParsed);
                newCAction.setAction(attributes.getValue("name"));
                if(attributes.getValue("type").equals("death")) {
                    playerBeingParsed.setDeathAction(newCAction);
                }else if(attributes.getValue("type").equals("hit")) {
                    playerBeingParsed.setHitAction(newCAction);
                }
                CABeingParsed = newCAction;
            }
        }else if(qName.equalsIgnoreCase("Scroll")) {
            newScroll = new Scroll(attributes.getValue("name"));
            newScroll.setID(Integer.parseInt(attributes.getValue("room")), Integer.parseInt(attributes.getValue("serial")));
            itemBeingParsed = newScroll;
            scrollList.add(newScroll);
        }else if(qName.equalsIgnoreCase("Armor")) {
            newArmor = new Armor(attributes.getValue("name"));
            newArmor.setID(Integer.parseInt(attributes.getValue("room")), Integer.parseInt(attributes.getValue("serial")));
            if(playerBeingParsed != null) {
                //playerBeingParsed.setArmor(newArmor);
                newArmor.setOwner(playerBeingParsed);
                Rogue.getPackList().add(newArmor);
            }
            else {
                armorList.add(newArmor);
            }
            itemBeingParsed = newArmor;
        }else if(qName.equalsIgnoreCase("Sword")) {
            newSword = new Sword(attributes.getValue("name"));
            newSword.setID(Integer.parseInt(attributes.getValue("room")), Integer.parseInt(attributes.getValue("serial")));
            if(playerBeingParsed != null) {
                //playerBeingParsed.setWeapon(newSword);
                newSword.setOwner(playerBeingParsed);
                Rogue.getPackList().add(newSword);
            }
            else {
                swordList.add(newSword);
            }
            itemBeingParsed = newSword;
        }else if(qName.equalsIgnoreCase("ItemAction")) {
            newIAction = new ItemAction(itemBeingParsed);
            newIAction.setAction(attributes.getValue("name"));
            itemBeingParsed.addItemAction(newIAction);
            IABeingParsed = newIAction;
        }else if(qName.equalsIgnoreCase("Passage")) {
            newPassage = new Passage();
            newPassage.setID(Integer.parseInt(attributes.getValue("room1")), Integer.parseInt(attributes.getValue("room2")));
            passageList.add(newPassage);
            passageBeingParsed = newPassage;
        }else if(qName.equalsIgnoreCase("posX")) {
            bPosX = true;
        }else if(qName.equalsIgnoreCase("posY")) {
            bPosY = true;
        }else if(qName.equalsIgnoreCase("type")) {
            bType = true;
        }else if(qName.equalsIgnoreCase("visible")) {
            bVisible = true;
        }else if(qName.equalsIgnoreCase("hp")) {
            bHp = true;
        }else if(qName.equalsIgnoreCase("hpMoves")) {
            bHpMoves = true;
        }else if(qName.equalsIgnoreCase("maxhit")) {
            bMaxHit = true;
        }else if(qName.equalsIgnoreCase("width")) {
            bWidth = true;
        }else if(qName.equalsIgnoreCase("height")) {
            bHeight = true;
        }else if(qName.equalsIgnoreCase("ItemIntValue")) {
            bItemIntVal = true;
        }else if(qName.equalsIgnoreCase("actionCharValue")) {
            bActionCharVal = true;
        }else if(qName.equalsIgnoreCase("actionIntValue")){
            bActionIntVal = true;
        }else if(qName.equalsIgnoreCase("actionMessage")) {
            bActionMessage = true;
        }
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bPosX){
            if (itemBeingParsed != null){
                itemBeingParsed.SetPosX(Integer.parseInt(data.toString()));
                itemBeingParsed.setTempPosX(Integer.parseInt(data.toString()) + roomBeingParsed.getPosX());
                bPosX = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.SetPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            }else if (playerBeingParsed != null){
                playerBeingParsed.SetPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            }else if (roomBeingParsed != null) {
                roomBeingParsed.SetPosX(Integer.parseInt(data.toString()));
                bPosX = false;
            }else if (passageBeingParsed != null) {
                passageBeingParsed.addX(Integer.parseInt(data.toString()));
                bPosX = false;
            }
        }else if (bPosY){
            if (itemBeingParsed != null){
                itemBeingParsed.setPosY(Integer.parseInt(data.toString()));
                itemBeingParsed.setTempPosY(Integer.parseInt(data.toString()) + roomBeingParsed.getPosY() + dungeonBeingParsed.getTopHeight());
                bPosY = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            }else if (playerBeingParsed != null){
                playerBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            }else if (roomBeingParsed != null) {
                roomBeingParsed.setPosY(Integer.parseInt(data.toString()));
                bPosY = false;
            }else if (passageBeingParsed != null) {
                passageBeingParsed.addY(Integer.parseInt(data.toString()));
                bPosY = false;
            }
        }
        else if (bType){
            if (itemBeingParsed != null){
                String str = data.toString();
                itemBeingParsed.setType(str.charAt(0));
                bType = false;
            }else if (playerBeingParsed != null){
                String str = data.toString();
                playerBeingParsed.setType(str.charAt(0));
                bType = false;
            }else if (monsterBeingParsed != null){
                String str = data.toString();
                monsterBeingParsed.setType(str.charAt(0));
                bType = false;
            }else if (roomBeingParsed != null){
                String str = data.toString();
                roomBeingParsed.setType(str.charAt(0));
                bType = false;
            }
        }
        else if (bHp){
            if (playerBeingParsed != null){
                playerBeingParsed.setHp(Integer.parseInt(data.toString()));
                bHp = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.setHp(Integer.parseInt(data.toString()));
                bHp = false;
            }

        }
        else if (bMaxHit){
            if (playerBeingParsed != null){
                playerBeingParsed.setMaxHit(Integer.parseInt(data.toString()));
                bMaxHit = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.setMaxHit(Integer.parseInt(data.toString()));
                bMaxHit = false;
            }

        }
        else if (bVisible){
            if (itemBeingParsed != null){
                itemBeingParsed.setVisible();
                bVisible = false;
            }else if (itemBeingParsed != null){
                playerBeingParsed.setVisible();
                bVisible = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.setVisible();
                bVisible = false;
            }else if (roomBeingParsed != null){
                roomBeingParsed.setVisible();
                bVisible = false;
            }else if (passageBeingParsed != null) {
                passageBeingParsed.setVisible();
                bVisible = false;
            }
        }
        else if (bInvisible){
            if (itemBeingParsed != null){
                itemBeingParsed.setInvisible();
                bInvisible = false;
            }else if (itemBeingParsed != null){
                playerBeingParsed.setInvisible();
                bInvisible = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.setInvisible();
                bInvisible = false;
            }else if (roomBeingParsed != null){
                roomBeingParsed.setInvisible();
                bInvisible = false;
            }
        }
        else if (bHpMoves){
            if (itemBeingParsed != null){
                itemBeingParsed.setHpMove(Integer.parseInt(data.toString()));
                bHpMoves = false;
            }else if (playerBeingParsed != null){
                playerBeingParsed.setHpMove(Integer.parseInt(data.toString()));
                bHpMoves = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.setHpMove(Integer.parseInt(data.toString()));
                bHpMoves = false;
            }else if (roomBeingParsed != null){
                roomBeingParsed.setHpMove(Integer.parseInt(data.toString()));
                bHpMoves = false;
            }
        }
        else if (bWidth){
            if (itemBeingParsed != null){
                itemBeingParsed.SetWidth(Integer.parseInt(data.toString()));
                bWidth = false;
            }else if (playerBeingParsed != null){
                playerBeingParsed.SetWidth(Integer.parseInt(data.toString()));
                bWidth = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.SetWidth(Integer.parseInt(data.toString()));
                bWidth = false;
            }else if (roomBeingParsed != null){
                roomBeingParsed.SetWidth(Integer.parseInt(data.toString()));
                bWidth = false;
            }
        }
        else if (bHeight){
            if (itemBeingParsed != null){
                itemBeingParsed.setHeight(Integer.parseInt(data.toString()));
                bHeight = false;
            }else if (playerBeingParsed != null){
                playerBeingParsed.setHeight(Integer.parseInt(data.toString()));
                bHeight = false;
            }else if (monsterBeingParsed != null){
                monsterBeingParsed.setHeight(Integer.parseInt(data.toString()));
                bHeight = false;
            }else if (roomBeingParsed != null){
                roomBeingParsed.setHeight(Integer.parseInt(data.toString()));
                bHeight = false;
            }
        }
        else if (bActionCharVal){
            if (IABeingParsed != null){
                String str = data.toString();
                IABeingParsed.setCharValue(str.charAt(0));
                bActionCharVal = false;
            }else if (CABeingParsed != null){
                String str = data.toString();
                CABeingParsed.setCharValue(str.charAt(0));
                bActionCharVal = false;
            }
        }
        else if (bActionMessage){
            if (IABeingParsed != null){
                IABeingParsed.setMessage(data.toString());
                bActionMessage = false;
            }else if (CABeingParsed != null){
                CABeingParsed.setMessage(data.toString());
                bActionMessage = false;
            }
        }
        else if (bActionIntVal){
            if (IABeingParsed != null){
                IABeingParsed.setIntValue(Integer.parseInt(data.toString()));
                bActionIntVal = false;
            }else if (CABeingParsed != null){
                CABeingParsed.setIntValue(Integer.parseInt(data.toString()));
                bActionIntVal = false;
            }
        }
        else if (bItemIntVal){
            if (itemBeingParsed != null){
                itemBeingParsed.setIntValue(Integer.parseInt(data.toString()));
                bItemIntVal = false;
            }
        }

        if (qName.equalsIgnoreCase("Room")) {
            roomBeingParsed = null;
        }else if (qName.equalsIgnoreCase("Monster")) {
            monsterBeingParsed = null;
        }else if (qName.equalsIgnoreCase("Player")) {
            playerBeingParsed = null;
        }else if (qName.equalsIgnoreCase("Item")){
            itemBeingParsed = null;
        }else if (qName.equalsIgnoreCase("ItemAction")){
            IABeingParsed = null;
        }else if (qName.equalsIgnoreCase("CreatureAction")){
            CABeingParsed = null;
        }else if (qName.equalsIgnoreCase("Scroll") || qName.equalsIgnoreCase("Sword") || qName.equalsIgnoreCase("Armor")){
            itemBeingParsed = null;
        }
    }
    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}




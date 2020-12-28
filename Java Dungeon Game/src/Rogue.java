package src;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.stream.IntStream;
public class Rogue implements Runnable{

    private static final int DEBUG = 0;
    private boolean isRunning;
    public static final int FRAMESPERSECOND = 60;
    public static final int TIMEPERLOOP = 1000000000 / FRAMESPERSECOND;
    private static ObjectDisplayGrid displayGrid = null;
    private Thread keyStrokePrinter;

    static int width;
    static int height;
    static int topHeight;
    static int bottomHeight;
    static int msgSizeLast = 0;

    static SAXParserFactory saxParserFactory;
    static SAXParser saxParser;
    static DungeonXMLHandler handler;

    static ArrayList<Room> roomList;
    static ArrayList<Dungeon> dungeonList;
    static ArrayList<Item> itemList;
    static ArrayList<Monster> monsterList;
    static ArrayList<Player> playerList;
    static ArrayList<Passage> passageList;
    static ArrayList<Scroll> scrollList;
    static ArrayList<Armor> armorList;
    static ArrayList<Sword> swordList;

    static ArrayList<Item> packList = new ArrayList<Item>();

    static private Stack<Item>[][] itemGrid = null;


    public Rogue(int width, int topHeight, int height, int bottomHeight){
        displayGrid = new ObjectDisplayGrid(width, topHeight, height, bottomHeight);
    }

    //getters and setters
    public static ObjectDisplayGrid getDisplayGrid( ) {
        return displayGrid;
    }
    public static int getTopHeight(){
        return topHeight;
    }
    public static int getGH(){
        return height;
    }
    public static int getW(){
        return width;
    }
    public static ArrayList<Player> getPlayers(){
        return playerList;
    }
    public static ArrayList<Item> getPackList(){
        return packList;
    }
    public void run(){}

    public static void main(String[] args) throws Exception{
        // check if a filename is passed in.  If not, print a usage message.
        // If it is, open the file
        String fileName = null;
        switch (args.length) {
            // note that the relative file path may depend on what IDE you are
            // using.  This worked for NetBeans.
            case 1: fileName = "xmlFiles/" + args[0];
                break;
            default: System.out.println("java Rogue <xmlfilename>");
                return;
        }

        // Create a saxParserFactory, that will allow use to create a parser
        // Use this line unchanged
        saxParserFactory = SAXParserFactory.newInstance();

        try {
            saxParser = saxParserFactory.newSAXParser();
            handler = new DungeonXMLHandler();
            saxParser.parse(new File(fileName), handler);

            roomList = handler.getRoomList();
            dungeonList = handler.getDungeonList();
            itemList = handler.getItemList();
            monsterList = handler.getMonsterList();
            playerList = handler.getPlayerList();
            passageList = handler.getPassageList();
            scrollList = handler.getScrollList();
            armorList = handler.getArmorList();
            swordList = handler.getSwordList();

            width = dungeonList.get(0).getWidth();
            height = dungeonList.get(0).getGameHeight();
            topHeight = dungeonList.get(0).getTopHeight();
            bottomHeight = dungeonList.get(0).getBottomHeight();

        }catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }

        Rogue rogue = new Rogue(width, topHeight, height, bottomHeight);
        displayGrid.initializeDisplay();

        int tempVar = 0;
        Char dash = new Char('X');
        Char pound = new Char('#');
        Char dot = new Char('.');
        Char plus = new Char('+');

        for (Room room : roomList) {
            for (int count1 = room.getPosX(); count1 < (room.getPosX() + room.getWidth() - 1); count1++) {
                displayGrid.addObject(dash, count1, topHeight + room.getPosY());
                displayGrid.addObject(dash, count1, topHeight + room.getPosY() + room.getHeight() - 1);
            }
            for (int count2 = room.getPosY(); count2 <= (room.getPosY() + room.getHeight() - 1); count2++) {
                displayGrid.addObject(dash, room.getPosX(), topHeight + count2);
                displayGrid.addObject(dash, room.getPosX() + room.getWidth() - 1, topHeight + count2);
            }
            for (int count3 = room.getPosX() + 1; count3 < (room.getPosX() + room.getWidth() - 1); count3++) {
                for (int count4 = room.getPosY() + 1; count4 < (room.getPosY() + room.getHeight() - 1); count4++) {
                    displayGrid.addObject(dot, count3, topHeight + count4);
                }
            }
        }

        int roomXcurr;
        roomXcurr = 0;
        int roomYcurr;
        roomYcurr = 0;

        itemGrid = (Stack<Item>[][]) new Stack[displayGrid.getGameWidth()][displayGrid.getGameHeight()];
        for (int i = 0; i < displayGrid.getGameWidth(); i++){
            for (int j = 0; j < displayGrid.getGameHeight(); j++){
                itemGrid[i][j] = new Stack<Item>();
            }
        }

        for (Scroll scroll : scrollList) {
            Char ch = new Char(scroll.getType());
            for (Room room : roomList) {
                if (room.getId() == scroll.getRoom()) {
                    roomXcurr = room.getPosX();
                    roomYcurr = room.getPosY();
                }
            }
            displayGrid.addObject(ch, scroll.getPosX() + roomXcurr, topHeight + scroll.getPosY() + roomYcurr);
            itemGrid[scroll.getPosX() + roomXcurr][topHeight + scroll.getPosY() + roomYcurr].push(scroll);
        }

        for (Armor armor : armorList) {
            Char ch = new Char(armor.getType());
            for (Room room : roomList) {
                if (room.getId() == armor.getRoom()) {
                    roomXcurr = room.getPosX();
                    roomYcurr = room.getPosY();
                }
            }
            displayGrid.addObject(ch, armor.getPosX() + roomXcurr, topHeight + armor.getPosY() + roomYcurr);
            itemGrid[armor.getPosX() + roomXcurr][topHeight + armor.getPosY() + roomYcurr].push(armor);
        }

        for (Sword sword : swordList) {
            Char ch = new Char(sword.getType());
            for (Room room : roomList) {
                if (room.getId() == sword.getRoom()) {
                    roomXcurr = room.getPosX();
                    roomYcurr = room.getPosY();
                }
            }
            displayGrid.addObject(ch, sword.getPosX() + roomXcurr, topHeight + sword.getPosY() + roomYcurr);
            itemGrid[sword.getPosX() + roomXcurr][topHeight + sword.getPosY() + roomYcurr].push(sword);
        }

        for (Monster monster : monsterList) {
            Char ch = new Char(monster.getType());
            for (Room room : roomList) {
                if (room.getId() == monster.getRoom()) {
                    roomXcurr = room.getPosX();
                    roomYcurr = room.getPosY();
                }
            }
            displayGrid.addObject(ch, monster.getPosX() + roomXcurr, topHeight + monster.getPosY() + roomYcurr);
        }

        for (int i = 0; i < passageList.size(); i++) {
            ArrayList<Integer> cornersPosX = passageList.get(i).getX();
            ArrayList<Integer> cornersPosY = passageList.get(i).getY();
            int numCorners = cornersPosX.size();

            displayGrid.addObject(plus, cornersPosX.get(0), topHeight + cornersPosY.get(0)); //prints first corner as plus
            for (int j = 0; j < (numCorners - 1); j++) {
                if (cornersPosX.get(j) < cornersPosX.get(j + 1)) {
                    for (int m = cornersPosX.get(j) + 1; m < cornersPosX.get(j + 1); m++) {
                        displayGrid.addObject(pound, m, topHeight + cornersPosY.get(j));
                    }
                }else if (cornersPosX.get(j) > cornersPosX.get(j + 1)) {
                    for (int m = cornersPosX.get(j) - 1; m > cornersPosX.get(j + 1); m--) {
                        displayGrid.addObject(pound, m, topHeight + cornersPosY.get(j));
                    }
                }else if (cornersPosY.get(j) < cornersPosY.get(j + 1)) {
                    for (int n = cornersPosY.get(j) + 1; n < cornersPosY.get(j + 1); n++) {
                        displayGrid.addObject(pound, cornersPosX.get(j), topHeight + n);
                    }
                }else if (cornersPosY.get(j) > cornersPosY.get(j + 1)) {
                    for (int n = cornersPosY.get(j) - 1; n > cornersPosY.get(j + 1); n--) {
                        displayGrid.addObject(pound, cornersPosX.get(j), topHeight + n);
                    }
                }
                if (j == (numCorners - 2)) {
                    displayGrid.addObject(plus, cornersPosX.get(j + 1), topHeight + cornersPosY.get(j + 1));
                }else {
                    displayGrid.addObject(pound, cornersPosX.get(j + 1), topHeight + cornersPosY.get(j + 1));
                }
            }
            tempVar = i;
        }
        for (Room room : roomList) {
            if (!monsterList.isEmpty()) {
                if (room.getId() == monsterList.get(tempVar).getRoom()) {
                    roomXcurr = room.getPosX();
                    roomYcurr = room.getPosY();
                }
            }
        }
        displayGrid.addObject(new Char('@'), playerList.get(0).getPosX() + roomXcurr, topHeight + playerList.get(0).getPosY() + roomYcurr);
        displayGrid.setPlayerX(playerList.get(0).getPosX() + roomXcurr);
        displayGrid.setPlayerY(topHeight + playerList.get(0).getPosY() + roomYcurr);
        updateTopDisplay();
        infoUpdate("");
        packUpdate("");



        Thread rogueThread = new Thread(rogue);
        rogueThread.start();

        rogue.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
        rogue.keyStrokePrinter.start();

        rogueThread.join();
        rogue.keyStrokePrinter.join();

    }

    public static boolean Wartime(int x, int y){
        int monsterLoc = 0;
        int locx_i;
        int locy_i;
        int roomXtemp = 0;
        int roomYtemp = 0;
        locx_i = 0;
        while (locx_i < monsterList.size()) {
            locy_i = 0;
            while (locy_i < roomList.size()) {
                if (roomList.get(locy_i).getId() == monsterList.get(locx_i).getRoom()){
                    roomXtemp = roomList.get(locy_i).getPosX();
                    roomYtemp = roomList.get(locy_i).getPosY();
                }
                locy_i++;
            }
            if (((monsterList.get(locx_i).getPosX() + roomXtemp) != x) || ((monsterList.get(locx_i).getPosY() + roomYtemp + topHeight) != y)) {
                locx_i++;
            } else {
                monsterLoc = locx_i;
                break;
            }
        }

        if (monsterList.get(monsterLoc).getHp() > 0) {
            int playerDamage = calcDamage(monsterList.get(monsterLoc).getMaxHit());
            if (playerList.get(0).getArmor() != null) {
                playerDamage -= playerList.get(0).getArmor().getIntValue();
                if (playerDamage < 0) {
                    playerDamage = 0;
                }
            }
            int damageToMonster = calcDamage(playerList.get(0).getMaxHit());
            if (playerList.get(0).getWeapon() != null)
                damageToMonster += playerList.get(0).getWeapon().getIntValue();

            playerList.get(0).setHp(playerList.get(0).getHp() - playerDamage); //update player hp
            monsterList.get(monsterLoc).setHp(monsterList.get(monsterLoc).getHp() - damageToMonster); //update monster hp

            updateTopDisplay();
            showDamage(damageToMonster, playerDamage, monsterList.get(monsterLoc).getType());
            playerList.get(0).doHitAction();
            monsterList.get(monsterLoc).doHitAction();

            if (playerList.get(0).getHp() > 0) {
                if (monsterList.get(monsterLoc).getHp() <= 0) {
                    monsterList.get(monsterLoc).doDeathAction();
                }
                return false;
            } else {
                playerList.get(0).doDeathAction();
                return true;
            }
        } else {
            return false;
        }
    }

    private static int calcDamage(int maxHit){
        Random rand = new Random();
        int temp = rand.nextInt(maxHit + 1);

        return temp;
    }

    public static void updateTopDisplay(){
        String msg = "Player Health: " + playerList.get(0).getHp() + "  Score:  0";

        IntStream.range(0, msg.length()).forEach(i -> displayGrid.addObject(new Char(msg.charAt(i)), i, 0));
    }

    public static void packUpdate(String input){
        String pack;
        pack = new StringBuilder().append("Pack: ").append(input).toString();
        int i;
        i = 0;
        int tempCalc = height + topHeight + bottomHeight - 3;
        while (i < pack.length()) {
            displayGrid.addObject(new Char(pack.charAt(i)), i, tempCalc);
            i++;
        }
        if (!displayGrid.getObjectGridStack()[i][tempCalc].empty()) {
            do {
                displayGrid.clearObjectDisplay(i, tempCalc);
                i++;
            } while (!displayGrid.getObjectGridStack()[i][tempCalc].empty());
        }
    }

    public static void infoUpdate(String input){
        String currT = "Info: " + input;
        int i;
        i = 0;
        int tempCalc = height + topHeight + bottomHeight - 1;
        while (i < currT.length()) {
            displayGrid.addObject(new Char(currT.charAt(i)), i, tempCalc);
            i++;
        }
        i = currT.length();
        if (i < msgSizeLast) {
            do {
                displayGrid.clearObjectDisplay(i, tempCalc);
                i++;
            } while (i < msgSizeLast);
        }
        msgSizeLast = currT.length();
    }

    private static void showDamage(int damage, int damageToPlayer, char type){
        String damageStr = switch (type) {
            case 'S' -> "Snake damage: " + damage;
            case 'T' -> "Troll damage: " + damage;
            default -> "Hobgoblin Damage: " + damage;
        };
        infoUpdate(damageStr);
    }

    public static void monsterDeath(Monster monster, int roomX, int roomY){
        displayGrid.removeObject(monster.getPosX() + roomX, monster.getPosY() + roomY + topHeight);
    }

    public static void pickup(int x, int y){
        if (!itemGrid[x][y].empty()) {
            displayGrid.removeObject(x, y);
            packList.add(itemGrid[x][y].pop());
            displayGrid.removeObject(x, y);
            displayGrid.addObject(new Char('@'), x, y);
        }
    }

    public static void drop(int input){
        if (input >= packList.size()) {
            return;
        }

        displayGrid.removeObject(displayGrid.getPlayerX(), displayGrid.getPlayerY());
        displayGrid.addObject(new Char(packList.get(input).getType()), displayGrid.getPlayerX(), displayGrid.getPlayerY());
        itemGrid[displayGrid.getPlayerX()][displayGrid.getPlayerY()].push(packList.get(input));
        itemGrid[displayGrid.getPlayerX()][displayGrid.getPlayerY()].peek().setTempPosX(displayGrid.getPlayerX());
        itemGrid[displayGrid.getPlayerX()][displayGrid.getPlayerY()].peek().setTempPosY(displayGrid.getPlayerY());

        switch (packList.get(input).getType()) {
            case ')':
                if (playerList.get(0).getWeapon() != null && packList.get(input).getName().equals(playerList.get(0).getWeapon().getName()))
                    playerList.get(0).setWeapon(null);
                break;
            case ']':
                if (playerList.get(0).getArmor() != null && packList.get(input).getName().equals(playerList.get(0).getArmor().getName()))
                    playerList.get(0).setArmor(null);
                break;
        }
        packList.remove(input);
        displayGrid.addObject(new Char('@'),
                displayGrid.getPlayerX(),
                displayGrid.getPlayerY());
    }

    //deletes everything
    public static void removeAll() {
        int i = 0;
        while (i < packList.size()) {
            drop(0);
            i++;
        }
    }

    public static void showAll(){
        String temp;
        temp = "";
        if (packList.size() == 0){
            packUpdate(temp);
            return;
        }

        int i = 0;
        while (i < packList.size() - 1) {
            temp = getString(i, temp);
            temp += ", ";
            i++;
        }

        packUpdate(getString(i, temp));
    }

    private static String getString(int init_num, String input_str) {
        input_str += (char) (init_num+'0') + ":";
        input_str += packList.get(init_num).getName();
        if (playerList.get(0).getWeapon() != null) {
            if (packList.get(init_num).getType() == ')') {
                if (packList.get(init_num).getName().equals(playerList.get(0).getWeapon().getName())) input_str += "(w)";
            } else if (playerList.get(0).getArmor() != null)
                if (packList.get(init_num).getType() == ']') {
                    if (packList.get(init_num).getName().equals(playerList.get(0).getArmor().getName())) input_str += "(a)";
                }
        } else if (playerList.get(0).getArmor() != null)
            if (packList.get(init_num).getType() == ']') {
                if (packList.get(init_num).getName().equals(playerList.get(0).getArmor().getName())) input_str += "(a)";
            }
        if ((packList.get(init_num).getType() == ')') || packList.get(init_num).getType() == ']'){
            input_str += packList.get(init_num).getIntValue();
        }
        return input_str;
    }

    public static void updateHP(){
        playerList.get(0).setHp(playerList.get(0).getHp() + 1);
        //update the top display section with new player HP
        updateTopDisplay();
    }

    public static int HPok(int checkHP){
        if (playerList.get(0).getHpMoves() > checkHP) {
            return checkHP;
        } else {
            updateHP();
            return 0;
        }
    }

    public static void pullWeapon(int input){
        if (input < packList.size() && packList.get(input).getType() == ')')
            playerList.get(0).setWeapon(packList.get(input));
    }

    public static void wearItem(int input){
        if (input < packList.size() && packList.get(input).getType() == ']')
            playerList.get(0).setArmor(packList.get(input));
    }

    public static void setArmorXY(){
        playerList.get(0).setArmor(null);
    }

    public static void readScroll(int input){
        if (input >= packList.size()) {
            return;
        }
        if (packList.get(input).getType() == '?') {
            packList.get(input).doItemAction();
        }
        packList.remove(input);
    }

    public static int checkHallucinate(int input){
        if (input == 0) {
            displayGrid.terminalUpdateR();
            return -1;
        }
        if(input > 0) {
            incrHallucinate();
            return input;
        } else {
            return -1;
        }
    }

    public static void incrHallucinate(){
        Char temp;
        int i = 0;
        int j = 0;
        while (i < width) {
            j = topHeight;
            while (j < (topHeight + height)) {
                if(displayGrid.topTop(i, j).getChar() != ' ') {
                    Random rand = new Random();
                    int randomValue = rand.nextInt(10);
                    temp = switch (randomValue) {
                        case 0 -> new Char('.');
                        case 1 -> new Char('X');
                        case 2 -> new Char('#');
                        case 3 -> new Char('+');
                        case 4 -> new Char(')');
                        case 5 -> new Char(']');
                        case 6 -> new Char('?');
                        case 7 -> new Char('H');
                        case 8 -> new Char('T');
                        case 9 -> new Char('S');
                        default -> new Char('S');
                    };
                    while(true) {
                        if (displayGrid.topTop(i, j).getChar() != temp.getChar()) {
                            break;
                        }
                        //temp = getRandomChar();
                        rand = new Random();
                        randomValue = rand.nextInt(10);
                        temp = switch (randomValue) {
                            case 0 -> new Char('.');
                            case 1 -> new Char('X');
                            case 2 -> new Char('#');
                            case 3 -> new Char('+');
                            case 4 -> new Char(')');
                            case 5 -> new Char(']');
                            case 6 -> new Char('?');
                            case 7 -> new Char('H');
                            case 8 -> new Char('T');
                            case 9 -> new Char('S');
                            default -> new Char('S');
                        };
                    }
                    displayGrid.addObject(temp, i, j);
                    displayGrid.removeComplete(i, j);
                }
                j++;
            }
            i++;
        }
    }
}
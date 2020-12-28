package src;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 0;
    private int HPmoveCount = 0;
    private static int hMoveCount = -1;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private ObjectDisplayGrid displayGrid;
    private static boolean dead;

    public KeyStrokePrinter(ObjectDisplayGrid grid) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
    }

    public static void setDead(){
        dead = true;
    }

    public static  void sethMoveCount(int moves) {
        hMoveCount = moves + 1;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {
        char ch;
        dead = false;
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() == null) {
                processing = false;
            } else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }

                switch (ch) {
                    case 'h':
                        if (!displayGrid.getObjectGridStack()[displayGrid.getPlayerX() - 1][displayGrid.getPlayerY()].empty() && displayGrid.getObjectGridStack()[displayGrid.getPlayerX() - 1][displayGrid.getPlayerY()].peek().getChar() != 'X') {
                            switch (displayGrid.getObjectGridStack()[displayGrid.getPlayerX() - 1][displayGrid.getPlayerY()].peek().getChar()) {
                                case 'S', 'T', 'H' -> dead = Rogue.Wartime(displayGrid.getPlayerX() - 1, displayGrid.getPlayerY());
                                default -> {
                                    displayGrid.removeObject(displayGrid.getPlayerX(), displayGrid.getPlayerY());
                                    displayGrid.addObject(new Char('@'), displayGrid.getPlayerX() - 1, displayGrid.getPlayerY());
                                    displayGrid.setPlayerX(displayGrid.getPlayerX() - 1);
                                    Rogue.getPlayers().get(0).SetPosX(displayGrid.getPlayerX());
                                    HPmoveCount++;
                                    HPmoveCount = Rogue.HPok(HPmoveCount);
                                    hMoveCount--;
                                    hMoveCount = Rogue.checkHallucinate(hMoveCount);
                                }
                            }
                        }
                        break;
                    case 'l':
                        if (!displayGrid.getObjectGridStack()[displayGrid.getPlayerX() + 1][displayGrid.getPlayerY()].empty() && displayGrid.getObjectGridStack()[displayGrid.getPlayerX() + 1][displayGrid.getPlayerY()].peek().getChar() != 'X') {
                            switch (displayGrid.getObjectGridStack()[displayGrid.getPlayerX() + 1][displayGrid.getPlayerY()].peek().getChar()) {
                                case 'S', 'T', 'H' -> dead = Rogue.Wartime(displayGrid.getPlayerX() + 1, displayGrid.getPlayerY());
                                default -> {
                                    displayGrid.removeObject(displayGrid.getPlayerX(), displayGrid.getPlayerY());
                                    displayGrid.addObject(new Char('@'), displayGrid.getPlayerX() + 1, displayGrid.getPlayerY());
                                    displayGrid.setPlayerX(displayGrid.getPlayerX() + 1);
                                    Rogue.getPlayers().get(0).SetPosX(displayGrid.getPlayerX());
                                    HPmoveCount++;
                                    HPmoveCount = Rogue.HPok(HPmoveCount);
                                    hMoveCount--;
                                    hMoveCount = Rogue.checkHallucinate(hMoveCount);
                                }
                            }
                        }
                        break;
                    case 'j':
                        if (!displayGrid.getObjectGridStack()[displayGrid.getPlayerX()][displayGrid.getPlayerY() + 1].empty() && displayGrid.getObjectGridStack()[displayGrid.getPlayerX()][displayGrid.getPlayerY() + 1].peek().getChar() != 'X') {
                            switch (displayGrid.getObjectGridStack()[displayGrid.getPlayerX()][displayGrid.getPlayerY() + 1].peek().getChar()) {
                                case 'S', 'T', 'H' -> dead = Rogue.Wartime(displayGrid.getPlayerX(), displayGrid.getPlayerY() + 1);
                                default -> {
                                    displayGrid.removeObject(displayGrid.getPlayerX(), displayGrid.getPlayerY());
                                    displayGrid.addObject(new Char('@'), displayGrid.getPlayerX(), displayGrid.getPlayerY() + 1);
                                    displayGrid.setPlayerY(displayGrid.getPlayerY() + 1);
                                    Rogue.getPlayers().get(0).setPosY(displayGrid.getPlayerY());
                                    HPmoveCount++;
                                    HPmoveCount = Rogue.HPok(HPmoveCount);
                                    hMoveCount--;
                                    hMoveCount = Rogue.checkHallucinate(hMoveCount);
                                }
                            }
                        }
                        break;
                    case 'k':
                        if (!displayGrid.getObjectGridStack()[displayGrid.getPlayerX()][displayGrid.getPlayerY() - 1].empty() && displayGrid.getObjectGridStack()[displayGrid.getPlayerX()][displayGrid.getPlayerY() - 1].peek().getChar() != 'X') {
                            switch (displayGrid.getObjectGridStack()[displayGrid.getPlayerX()][displayGrid.getPlayerY() - 1].peek().getChar()) {
                                case 'S', 'T', 'H' -> dead = Rogue.Wartime(displayGrid.getPlayerX(), displayGrid.getPlayerY() - 1);
                                default -> {
                                    displayGrid.removeObject(displayGrid.getPlayerX(), displayGrid.getPlayerY());
                                    displayGrid.addObject(new Char('@'), displayGrid.getPlayerX(), displayGrid.getPlayerY() - 1);
                                    displayGrid.setPlayerY(displayGrid.getPlayerY() - 1);
                                    Rogue.getPlayers().get(0).setPosY(displayGrid.getPlayerY());
                                    HPmoveCount++;
                                    HPmoveCount = Rogue.HPok(HPmoveCount);
                                    hMoveCount--;
                                    hMoveCount = Rogue.checkHallucinate(hMoveCount);
                                }
                            }
                        }
                        break;
                    case 'p':
                        Rogue.pickup(displayGrid.getPlayerX(), displayGrid.getPlayerY());
                        break;
                    case 'i':
                        Rogue.showAll();
                        break;
                    case 'd':
                        drop();
                        break;
                    case '?':
                        Rogue.infoUpdate("c d E ? H i p r T w");
                        break;
                    case 'H':
                        help();
                        break;
                    case 'c':
                        Rogue.setArmorXY();
                        break;
                    case 'E':
                        dead = end();
                        break;
                    case 'r':
                        read();
                        break;
                    case 'T':
                        take();
                        break;
                    case 'w':
                        wear();
                        break;
                    default:
                        break;
                }
            }
        }
        return !dead;
    }

    private void drop(){
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() != null) {
                if ((int) inputQueue.peek() < 48) {
                    processing = false;
                } else if ((int) inputQueue.peek() > 57) {
                    processing = false;
                } else {
                    Rogue.drop((int) (inputQueue.poll()) - (int) '0');
                    processing = false;
                }
            }
        }
    }

    private void help(){
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() != null) {
                switch (inputQueue.peek()) {
                    case '?' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Show the different commands in the info section of the display.");
                    }
                    case 'i' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Show or display the inventory");
                    }
                    case 'p' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Pick up an item from the dungeon floor");
                    }
                    case 'r' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Read an item");
                    }
                    case 'T' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Take out a weapon");
                    }
                    case 'w' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Wear item");
                    }
                    case 'c' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Change, or take off armor");
                    }
                    case 'd' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("Drop");
                    }
                    case 'E' -> {
                        processing = false;
                        inputQueue.remove();
                        Rogue.infoUpdate("End game");
                    }
                    default -> processing = false;
                }
            }
        }
    }

    private boolean end(){
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() != null) {
                switch (inputQueue.peek()) {
                    case 'y', 'Y' -> {
                        processing = false;
                        Rogue.infoUpdate("Game ended due to command E");
                        return true;
                    }
                    default -> processing = false;
                }
            }
        }
        return false;
    }

    private void read(){
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() != null) {
                if ((int) inputQueue.peek() < 48) {
                    processing = false;

                } else if ((int) inputQueue.peek() > 57) {
                    processing = false;

                } else {
                    processing = false;
                    Rogue.readScroll((int) (inputQueue.poll()) - (int) '0');
                }
            }
        }
    }

    private void take(){
        boolean dead = false;
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() != null) {
                if ((int) inputQueue.peek() < 48) {
                    processing = false;

                } else if ((int) inputQueue.peek() > 57) {
                    processing = false;

                } else {
                    processing = false;
                    Rogue.pullWeapon((int) (inputQueue.poll()) - (int) '0');
                }
            }
        }
    }

    private void wear(){
        boolean processing = true;
        while (processing) {
            if (inputQueue.peek() != null) {
                if (((int) inputQueue.peek() > 57) || ((int) inputQueue.peek() < 48)){
                    processing = false;

                }else {
                    processing = false;
                    Rogue.wearItem((int)(inputQueue.poll()) - (int)'0');
                }
            }
        }
    }

    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working) {
            rest();
            working = (processInput( ));
        }
    }
}

package src;

import asciiPanel.AsciiPanel;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    int width;
    int height;
    int topHeight;
    int gameHeight;
    int bottomHeight;
    int posX;
    int posY;
    int chkPriority;

    private static AsciiPanel terminal;
    private Stack<Char>[][] objectGridStack = null;
    private List<InputObserver> inputObserverList = null;

    public ObjectDisplayGrid(int width, int topHeight, int gameHeight, int bottomHeight){
        this.width = width;
        this.topHeight = topHeight;
        this.gameHeight = gameHeight;
        this.bottomHeight = bottomHeight;
        height = gameHeight + topHeight + bottomHeight;
        chkPriority = 1;
        terminal = new AsciiPanel(this.width, height);

        objectGridStack = (Stack<Char>[][]) new Stack[this.width][height];

        int i = 0;
        while (i < this.width) {
            int j = 0;
            while (j < height) {
                objectGridStack[i][j] = new Stack<Char>();
                j++;
            }
            i++;
        }

        initializeDisplay();

        super.add(terminal);
        super.setSize(700, 950);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObserverList = new ArrayList<>();
        super.repaint();
    }

    public final void initializeDisplay() {
        terminal.repaint();
    }

    public void addObject(Char tChar, int i, int j) {
        if ((0 <= i) && (i < objectGridStack.length) && (0 <= j) && (j < objectGridStack[0].length)) {
            objectGridStack[i][j].add(tChar);
            writeToTerminal(i, j);
        }
    }

    public void removeObject(int i, int j) {
        if ((0 <= i) && (i < objectGridStack.length) && (0 <= j) && (j < objectGridStack[0].length)) {
            objectGridStack[i][j].pop();
            if (objectGridStack[i][j].empty()) {
                objectGridStack[i][j].add(new Char(' '));
            }
            writeToTerminal(i, j);
        }
    }

    public void clearObjectDisplay(int i, int j) {
        if ((0 <= i) && (i < objectGridStack.length) && (0 <= j) && (j < objectGridStack[0].length)) {
            while (true) {
                if (objectGridStack[i][j].empty()) break;
                objectGridStack[i][j].pop();
            }
            objectGridStack[i][j].add(new Char(' '));
            writeToTerminal(i, j);
        }
    }

    public Char topTop(int i, int j) {
        if ((0 <= i) && (i < objectGridStack.length) && (0 <= j) && (j < objectGridStack[0].length)) {
            if (!objectGridStack[i][j].empty()) {
                return objectGridStack[i][j].peek();
            } else {
                return new Char(' ');
            }
        }
        //endCase
        return null;
    }

    public void removeComplete(int i, int j) {
        if ((0 <= i) && (i < objectGridStack.length) && (0 <= j) && (j < objectGridStack[0].length)) {
            objectGridStack[i][j].pop();
            if (objectGridStack[i][j].empty()) objectGridStack[i][j].add(new Char(' '));
        }
    }

    public void terminalUpdateR() {
        int i = 0;
        while (i < objectGridStack.length) {
            int j = 0;
            while (j < objectGridStack[0].length) {
                if (!objectGridStack[i][j].empty()){
                    writeToTerminal(i, j);
                }
                j++;
            }
            i++;
        }
    }

    private void writeToTerminal(int x, int y) {
        char temp = objectGridStack[x][y].peek().getChar();
        terminal.write(temp, x, y);
        terminal.repaint();
    }

    public Stack<Char>[][] getObjectGridStack(){
        return objectGridStack;
    }

    public void keyPressed(KeyEvent e){
    }

    public void keyReleased(KeyEvent e){
    }

    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObserverList.add(observer);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObserverList) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }

    //extra getters setters - not sure will need
    void setPlayerX(int posX){
        this.posX = posX;
    }

    void setPlayerY(int posY){
        this.posY = posY;
    }

    int getPlayerX(){
        return posX;
    }

    int getPlayerY(){
        return posY;
    }

    int getGameWidth(){
        return width;
    }

    int getGameHeight(){
        return height;
    }

}
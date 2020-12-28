package src;

public class Action {
    int intVal;
    char chVal;
    String message;

    void setMessage(String msg){
        this.message = msg;
        System.out.println("Action message: " + msg);
    }

    void setIntValue(int v){
        System.out.println("Action int Value: " + v);
        intVal = v;
    }

    void setCharValue(char c){
        System.out.println("Action char Value: " + c);
        chVal = c;
    }
}
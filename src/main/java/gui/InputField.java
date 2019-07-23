package gui;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputField {

    private volatile boolean waiting = true;
    private String input;

    public InputField(){

    }

    public InputField(JTextField textField){
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    System.err.println("KEY RELEASED");
                    input = textField.getText();
                    textField.setText("");
                    waiting = false;
                }
                super.keyReleased(e);
            }
        });
    }

    private void invalidInput(){
        System.out.println("Invalid input, try again! ");
        waiting = true;
        input = null;
    }

    public String nextString(){
        nextLine();
        String s = input;
        input = null;
        return s;
    }

    public int nextInt(){
        String s = nextString();
        int i;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e){
            invalidInput();
            i = nextInt();
        }
        return i;
    }

    //Halts until input is detected from TextField.
    public void nextLine(){
        while (waiting);
        waiting = true;
    }
}

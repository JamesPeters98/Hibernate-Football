package gui;

import utils.InputUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;

public class ConsoleGui {

    public ConsoleGui(){
        JFrame frame = new JFrame();
        frame.add( new JLabel(" Output" ), BorderLayout.NORTH );

        JTextArea ta = new JTextArea();
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 14);

        ta.setFont(font);
        ta.setForeground(Color.WHITE);
        ta.setBackground(Color.BLACK);

        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 100 );
        Output.setOutputStream(taos);
        PrintStream ps = new PrintStream(taos);
        System.setOut(ps);
        //System.setErr( ps );

        JTextField textField = new JTextField();
        textField.setFont(font);
        frame.add(textField, BorderLayout.SOUTH);

        ta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textField.grabFocus();
                super.mouseClicked(e);
            }
        });

        ta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                textField.setText(textField.getText()+e.getKeyChar());
                textField.grabFocus();
            }
        });

        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        frame.add( scrollPane );
        frame.pack();
        frame.setVisible( true );
        frame.setSize(1280,720);

        InputUtil.setInputField(new InputField(textField));

    }
}

package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Menu extends JFrame implements ActionListener {
    private JButton jcomp1;
    private JButton jcomp2;
    private JButton jcomp3;
    private JButton jcomp4;
    private JButton jcomp5;


    @Override
    public void setBackground(Color bgColor) {
        super.setBackground(bgColor);
    }

    public Menu() throws FileNotFoundException {

        //construct components
//        jcomp1 = new JButton("SAVE GAME");
        jcomp2 = new JButton("RETURN TO GAME");
        jcomp3 = new JButton("OPTIONS");
        jcomp4 = new JButton("CREDITS");
//        jcomp5 = new JButton("NEW GAME");
        //actions to each button
//        jcomp1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    ChessboardComponent chessboard = new ChessboardComponent(10,10);
//                    //need to find the way to get the "chessboard" from the class ChessboardComponent
//                    chessboard.SavecurentGame();
//                } catch (FileNotFoundException a) {
//                    a.printStackTrace();
//                }
//            }
//        });
        jcomp2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              dispose(); // dispose() removes the JFrame
            }});
        jcomp4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(jcomp4, "Credits to Vithlaithla, Davong and Narun");
            }});
        jcomp3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Options option = new Options();
                option.setSize((int) getWidth() /2, (int) getHeight() /2);
                option.setLocationRelativeTo(null);
                option.setVisible(true);
            }});
//        jcomp5.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                NewGame newGame = new NewGame();
//                newGame.setSize((int) getWidth(), (int) getHeight() /2);
//                newGame.setLocationRelativeTo(null);
//                newGame.setVisible(true);
//            }});

//        jcomp1.setBackground(Color.GRAY);
        jcomp2.setBackground(Color.GRAY);
        jcomp3.setBackground(Color.GRAY);
        jcomp4.setBackground(Color.GRAY);
//        jcomp5.setBackground(Color.GRAY);
        //adjust size and set layout
        setPreferredSize(new Dimension(944, 557));
        setLayout(null);
        // Assigning close button **see jcomp2 : "Return to Game" **
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // remove default window buttons
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        //set Background
        getContentPane().setBackground(new Color(255,255,255));

        //add components
//        add(jcomp1);
        add(jcomp2);
        add(jcomp3);
        add(jcomp4);
//        add(jcomp5);

        //set component bounds (only needed by Absolute Positioning)
//        jcomp1.setBounds(30, 125, 165, 70);
        jcomp2.setBounds(30, 50, 165, 70);
        jcomp3.setBounds(30, 510, 165, 70);
        jcomp4.setBounds(30, 600, 165, 70);
//        jcomp5.setBounds(30, 20, 165, 70);
        //adding listener


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

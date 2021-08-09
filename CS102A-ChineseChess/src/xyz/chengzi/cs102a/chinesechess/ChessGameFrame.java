package xyz.chengzi.cs102a.chinesechess;

import xyz.chengzi.cs102a.chinesechess.chess.GeneralChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.Arrays;

import static xyz.chengzi.cs102a.chinesechess.Music.bMusic;

public class ChessGameFrame extends JFrame {
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private int gameWidth = (int) screenSize.getWidth()*36/80;
    private int gameHeight = (int) screenSize.getHeight()*4/5;
    protected ChessboardComponent chessboard = new ChessboardComponent(gameWidth, gameHeight);
    public ChessGameFrame() throws FileNotFoundException {

        setTitle("XiangQi - Chiness Chess");

        setSize(gameWidth,gameHeight*11/10);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);
        //chessboard.LoadingMoveseq();//add this to a button somewhere
        add(chessboard);

        JLabel statusLabel = new JLabel("Status");
        //statusLabel.setText(ChessboardComponent.getNumberofMove() % 2 == 0 ? "Black" : "Red");
        super.update(this.getGraphics());
        statusLabel.setLocation(gameWidth/15, gameHeight);
        statusLabel.setSize(gameWidth/5, 10);
        statusLabel.setVisible(true);
        add(statusLabel);


        JButton button = new JButton("MENU");
        button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Menu menu = null;
            try {
                menu = new Menu();
                JButton jcomp1 = new JButton("SAVE GAME");
                JButton jcomp5 = new JButton("LOAD GAME");
                JButton jcomp6 = new JButton("NEW GAME");
                JButton jcomp7 = new JButton("LOAD MOVE SEQUENCE");

                jcomp1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            //need to find the way to get the "chessboard" from the class ChessboardComponent
                            chessboard.SavecurentGame();
                        } catch (FileNotFoundException a) {
                            a.printStackTrace();
                        }
                    }
                });
                Menu finalMenu = menu;
                jcomp5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            FileDialog fd = new FileDialog(finalMenu, "Choose a file", FileDialog.LOAD);
                            fd.setDirectory("C:\\");
                            fd.setFile("*.txt");
                            fd.setVisible(true);
                            String filename = fd.getFile();
                            if (filename == null) {
                                System.out.println("File was not selected");
                            }
                            else {
                                chessboard.LoadGame(filename);
                                update(getGraphics());
                            }
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }

                    }
                });

                jcomp6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chessboard.StartnewGame();
                        update(getGraphics());
                    }
                });

                jcomp7.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        chessboard.StartnewGame();
                        FileDialog fd = new FileDialog(finalMenu, "Choose a file", FileDialog.LOAD);
                        fd.setDirectory("C:\\");
                        fd.setFile("*.txt");
                        fd.setVisible(true);
                        String filename = fd.getFile();
                        if (filename == null) {
                            System.out.println("File was not selected");
                        }else {
                            try {
                                chessboard.LoadingMoveseq(filename);
                            } catch (FileNotFoundException ex) {
                                ex.printStackTrace();
                            }
                        }

                    }
                });
                jcomp1.setBackground(Color.GRAY);
                jcomp5.setBackground(Color.GRAY);
                jcomp6.setBackground(Color.GRAY);
                jcomp7.setBackground(Color.GRAY);
                jcomp1.setBounds(30,240, 165, 70);
                jcomp5.setBounds(30, 330, 165, 70);
                jcomp6.setBounds(30, 150, 165, 70);
                jcomp7.setBounds(30, 420, 165, 70);
                menu.add(jcomp1);
                menu.add(jcomp5);
                menu.add(jcomp6);
                menu.add(jcomp7);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            menu.setSize((int) screenSize.getWidth()*12/80, (int) (screenSize.getHeight()*4/5));
            menu.setLocationRelativeTo(null);
            menu.setVisible(true);
        }});
        button.setLocation(gameWidth*11/15,gameHeight*49/50);
        button.setSize(gameWidth/5,40);
        button.setBackground(Color.GRAY);
        add(button);

    }


    public static void main(String[] args) throws FileNotFoundException {

        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = null;
            try {
                mainFrame = new ChessGameFrame();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mainFrame.setVisible(true);
        });
        bMusic();
//        ChessGameFrame mainFrame = new ChessGameFrame();
//        ChessboardComponent chessboard = new ChessboardComponent(50,50);
//        chessboard.StartnewGame();
//        System.out.println(chessboard.getCurrentColor());
//        System.out.println(Arrays.deepToString(chessboard.getChessboard()));
//        System.out.println();
//        chessboard.AIRandomMove();
//        System.out.println(chessboard.AIPiece);
//        System.out.println(chessboard.AIPiece.getPossibleMoves().size());
//        System.out.println(chessboard.AIMove);

    }



}

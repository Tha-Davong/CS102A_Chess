package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static xyz.chengzi.cs102a.chinesechess.Music.moveM;

public class SoldierChessComponent extends ChessComponent {
    ArrayList<ChessComponent> possibleMoves = new ArrayList<>();
    public SoldierChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
        super(chessboardPoint, location, chessColor);
    }

    @Override
    public ArrayList<ChessComponent> getPossibleMoves() {
        return possibleMoves;
    }

    @Override
    public ArrayList<ChessComponent> generatePossibleMoves(ChessComponent[][] chessboard) {
        int x = super.getChessboardPoint().getX();
        int y = super.getChessboardPoint().getY();
        possibleMoves.clear();
        // four moves
        ArrayList<ChessboardPoint> chessboardPoints = new ArrayList<>();
        if (x >= 0 && y+1 >= 0  && x< 10 && y+1< 9)
        chessboardPoints.add(new ChessboardPoint(x,y+1));
        if (x >= 0 && y-1 >= 0  && x< 10 && y-1< 9)
        chessboardPoints.add(new ChessboardPoint(x,y-1));
        if (x-1>= 0 && y >= 0  && x-1< 10 && y< 9)
        chessboardPoints.add(new ChessboardPoint(x-1,y));
        if (x+1>= 0 && y >= 0  && x+1< 10 && y< 9)
        chessboardPoints.add(new ChessboardPoint(x+1,y));
        for (int i = 0; i < chessboardPoints.size(); i++) {
            if (canMoveTo(chessboard,chessboardPoints.get(i))) {
                possibleMoves.add(chessboard[chessboardPoints.get(i).getX()][chessboardPoints.get(i).getY()]);
            }
        }
        return possibleMoves;

    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int rowDisplacement = Math.abs(source.getX() - destination.getX());
        int row = source.getX();
        int columnDisplacement = Math.abs(source.getY() - destination.getY());
        if (getChessColor() == ChessColor.BLACK) { // ChessColor.BLACK
            rowDisplacement =  destination.getX() - source.getX();
                if (row < 5) {   // Before River
                    if(rowDisplacement > 1 ) { // 1 forward movement
                        return false;
                    } else if (rowDisplacement < 0) { // no backward movement
                        return false;
                    } else if (columnDisplacement > 0) { // straight movement
                        return false;
                    }
                } else { // After River
                    if (rowDisplacement > 1) {
                        return false;
                    } else if (rowDisplacement < 0) {
                        return false;
                    } else if (columnDisplacement > 1) { //  left or right movement
                        return false;
                    } else if (rowDisplacement == 1 && columnDisplacement == 1){ // diagonal movement
                        return false;
                    }

                }
            } else { // ChessColor.RED
                rowDisplacement = source.getX() - destination.getX();
                if (row > 4) {
                    if (rowDisplacement > 1) {
                        return false;
                    } else if (rowDisplacement < 0) {
                        return false;
                    }  else if (columnDisplacement > 0) {
                        return false;
                    }
                } else { // when {Pawn} crossed the river
                    if (rowDisplacement > 1) {
                        return false;
                    } else if (rowDisplacement < 0) {
                        return false;
                    }  else if (columnDisplacement > 1) {
                        return false;
                    } else if (rowDisplacement == 1 && columnDisplacement == 1 ) {
                        return false;
                    }
                }
            }
        moveM();
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        highlight(g);

        try {

            //g.setColor(CHESS_COLOR);
            g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(getChessColor().getColor());
            //g.drawOval(2, 2, getWidth() - 5, getHeight() - 5);
            //g.setColor(Color.BLACK);
            //g.drawString("车", 15, 25); // FIXME: Use library to find the correct offset.
            if(g.getColor() == Color.BLACK){
                Graphics2D g2d = (Graphics2D) g.create();
                String path = new File(".").getAbsolutePath();
                BufferedImage myPicture = ImageIO.read(new File("images/pawn.png"));
                TexturePaint pic = new TexturePaint(myPicture,new Rectangle(0,0,getWidth(),getHeight()));
                g2d.setPaint(pic);
                g2d.fillRect(0,0,getWidth(),getHeight());
            }
            else{
                Graphics2D g2d = (Graphics2D) g.create();
                BufferedImage myPicture = ImageIO.read(new File("images/pawn2.png"));
                TexturePaint pic = new TexturePaint(myPicture,new Rectangle(0,0,getWidth(),getHeight()));
                g2d.setPaint(pic);
                g2d.fillRect(0,0,getWidth(),getHeight());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isSelected()) { // Highlights the chess if selected.
            g.setColor(Color.GREEN);
            g.drawLine(0,0,getWidth()/4,0);
            g.drawLine(0,0,0,getHeight()/4);
            g.drawLine(getWidth(),0,getWidth()*3/4,0);
            g.drawLine(getWidth(),0,getWidth(),getHeight()/4);
            g.drawLine(0,getHeight(),0,getHeight()*3/4);
            g.drawLine(0,getHeight(), getWidth()/4,getHeight());
            g.drawLine(getWidth(),getHeight(),getWidth(), getHeight()*3/4);
            g.drawLine(getWidth(),getHeight(),getWidth()*3/4,getHeight());
        }

    }
//    protected void highlight(Graphics g) {
//        g.drawRect(0,0,getWidth(),getHeight());
//    }

}

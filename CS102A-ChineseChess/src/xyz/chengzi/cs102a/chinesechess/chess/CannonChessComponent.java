package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static xyz.chengzi.cs102a.chinesechess.Music.moveM;

public class CannonChessComponent extends ChessComponent {
    ArrayList<ChessComponent> possibleMoves = new ArrayList<>();
    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
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
        // moves on all four directions
        ArrayList<ChessboardPoint> chessboardPoints = new ArrayList<>();
        for (int i = 1; i < 9-y; i++) {
            if (x >= 0 && y+i >= 0  && x < 10 && y+i< 9)
            chessboardPoints.add(new ChessboardPoint(x,y+i));
        }
        for (int i = 1; i < 9-y; i++) {
            if (x >= 0 && y-i >= 0  && x< 10 && y-i< 9)
            chessboardPoints.add(new ChessboardPoint(x,y-i));
        }
        for (int i = 1; i < 10-x ; i++) {
            if (x-i >= 0 && y >= 0  && x-i< 10 && y< 9)
            chessboardPoints.add(new ChessboardPoint(x-i,y));
        }
        for (int i = 1; i < 10-x; i++) {
            if (x+i >= 0 && y >= 0  && x+i< 10 && y< 9)
            chessboardPoints.add(new ChessboardPoint(x+i,y));
        }

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
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            int occupiedSpace = 0;
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    occupiedSpace += 1;
                } else {
//                    possibleMoves.add(chessboard[row][col]);
                }
            }
            if (occupiedSpace == 1) {
                if (!(chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
//                    possibleMoves.add(chessboard[destination.getX()][destination.getY()]);
                    return true;
                } else {
                    return false;
                }
            } else if (occupiedSpace == 0) {
                if (chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
//                    possibleMoves.add(chessboard[destination.getX()][destination.getY()]);
                    moveM();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        else if (source.getY() == destination.getY()) {
            int col = source.getY();
            int occupiedSpace = 0;
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {

                if (!(chessboard[row][col] instanceof EmptySlotComponent)) {
                    occupiedSpace += 1;
                } else {
//                    possibleMoves.add(chessboard[row][col]);
                }
            }
            if (occupiedSpace == 1) {
                if (!(chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
//                    possibleMoves.add(chessboard[destination.getX()][destination.getY()]);
                    return true;
                } else {
                    return false;
                }
            } else if (occupiedSpace == 0) {
                if (chessboard[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
//                    possibleMoves.add(chessboard[destination.getX()][destination.getY()]);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }

        else { // Not on the same row or the same column.
            return false;
        }
    }
//    public boolean isThreatenedSquare(ChessComponent[][] chessboard,ChessboardPoint destination ) {
//        if (canMoveTo(chessboard,destination)) {
//            return true;
//        }
//        return false;
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

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
                BufferedImage myPicture = ImageIO.read(new File("images/Cannon.png"));
                TexturePaint pic = new TexturePaint(myPicture,new Rectangle(0,0,getWidth(),getHeight()));
                g2d.setPaint(pic);
                g2d.fillRect(0,0,getWidth(),getHeight());
            }
            else{
                Graphics2D g2d = (Graphics2D) g.create();
                BufferedImage myPicture = ImageIO.read(new File("images/Cannon2.png"));
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
}


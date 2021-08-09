package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static xyz.chengzi.cs102a.chinesechess.Music.moveM;

public class CounsellorChessComponent extends ChessComponent {
    ArrayList<ChessComponent> possibleMoves = new ArrayList<>();
    boolean status;
    public CounsellorChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
        super(chessboardPoint, location, color);
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
        if (x+1 >= 0 && y+1 >= 0  && x+1< 10 && y+1< 9)
        chessboardPoints.add(new ChessboardPoint(x+1,y+1));
        if (x-1 >= 0 && y+1 >= 0  && x-1< 10 && y+1< 9)
        chessboardPoints.add(new ChessboardPoint(x-1,y+1));
        if (x-1 >= 0 && y-1 >= 0  && x-1< 10 && y-1< 9)
        chessboardPoints.add(new ChessboardPoint(x-1,y-1));
        if (x+1 >= 0 && y-1 >= 0  && x+1< 10 && y-1< 9)
        chessboardPoints.add(new ChessboardPoint(x+1,y-1));
        for (int i = 0; i < chessboardPoints.size(); i++) {
            if (canMoveTo(chessboard,chessboardPoints.get(i))) {
                possibleMoves.add(chessboard[chessboardPoints.get(i).getX()][chessboardPoints.get(i).getY()]);
            }
        }
        return possibleMoves;
    }

    //need a way to confine the counsellor in the palace
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        //prevent counsellor from leaving the palace
        boolean confinement = (destination.getX() >= 0 && destination.getX() <= 2 && destination.getY() >= 3 && destination.getY() <= 5)
                || (destination.getX() >= 7 && destination.getX() <= 9 && destination.getY() >= 3 && destination.getY() <= 5);
        if(!confinement)
            return false;

        if(destination.getX() == source.getX()+1 && destination.getY() == source.getY()+1) {
            moveM();
            return true;
        }else if(destination.getX() == source.getX()-1 && destination.getY() == source.getY()+1){
            moveM();
            return true;
        }else if(destination.getX() == source.getX()-1 && destination.getY() == source.getY()-1){

            moveM();
            return true;
        }
        else if(destination.getX() == source.getX()+1 && destination.getY() == source.getY()-1){
            moveM();
            return true;
        }else{//not one step diagonally
            return false;
        }
    }



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
                BufferedImage myPicture = ImageIO.read(new File("images/counsellor.png"));
                TexturePaint pic = new TexturePaint(myPicture,new Rectangle(0,0,getWidth(),getHeight()));
                g2d.setPaint(pic);
                g2d.fillRect(0,0,getWidth(),getHeight());
            }
            else{
                Graphics2D g2d = (Graphics2D) g.create();
                BufferedImage myPicture = ImageIO.read(new File("images/counsellor2.png"));
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
//            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}

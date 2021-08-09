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

public class ElephantChessComponent extends ChessComponent{
    ArrayList<ChessComponent> possibleMoves = new ArrayList<>();
    public ElephantChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color) {
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
        if (x+2 >= 0 && y+2 >= 0  && x+2< 10 && y+2< 9)
        chessboardPoints.add(new ChessboardPoint(x+2,y+2));
        if (x-2 >= 0 && y+2 >= 0  && x-2< 10 && y+2< 9)
        chessboardPoints.add(new ChessboardPoint(x-2,y+2));
        if (x-2 >= 0 && y-2 >= 0  && x-2< 10 && y-2< 9)
        chessboardPoints.add(new ChessboardPoint(x-2,y-2));
        if (x+2 >= 0 && y-2 >= 0  && x+2< 10 && y-2< 9)
        chessboardPoints.add(new ChessboardPoint(x+2,y-2));
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
        //river crossing
        boolean confinement = false;
        if(source.getX() <= 4 && destination.getX() > 4)
            confinement = true;
        else if(source.getX() >= 5 && destination.getX() < 5)
            confinement = true;
        if(confinement)
            return false;

        //four movement case
        if(destination.getX() == source.getX()+2 && destination.getY() == source.getY()+2){
            for(int i = 1; i < 2; ++i){
                if(!(chessboard[source.getX()+i][source.getY()+i] instanceof EmptySlotComponent))
                     return false;
            }
        }else if(destination.getX() == source.getX()-2 && destination.getY() == source.getY()-2){
            for(int i = 1; i < 2; ++i){
                if(!(chessboard[source.getX()-i][source.getY()-i] instanceof EmptySlotComponent))
                    return false;
            }
        } else if(destination.getX() == source.getX()+2 && destination.getY() == source.getY()-2){
            for(int i = 1; i < 2; ++i){
                if(!(chessboard[source.getX()+i][source.getY()-i] instanceof EmptySlotComponent))
                    return false;
            }
        }else if(destination.getX() == source.getX()-2 && destination.getY() == source.getY()+2){
            for(int i = 1; i < 2; ++i){
                if(!(chessboard[source.getX()-i][source.getY()+i] instanceof EmptySlotComponent))
                    return false;
            }
        }else {//not two step diagonal
//            System.out.println("here");
//            System.out.println(destination.getX() + " " + destination.getY());
//            System.out.println(source.getX() + " " + source.getY());
            return false;
        }
        moveM();
        return true;
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
                BufferedImage myPicture = ImageIO.read(new File("images/Elephant.png"));
                TexturePaint pic = new TexturePaint(myPicture,new Rectangle(0,0,getWidth(),getHeight()));
                g2d.setPaint(pic);
                g2d.fillRect(0,0,getWidth(),getHeight());
            }
            else{
                Graphics2D g2d = (Graphics2D) g.create();
                BufferedImage myPicture = ImageIO.read(new File("images/Elephant2.png"));
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

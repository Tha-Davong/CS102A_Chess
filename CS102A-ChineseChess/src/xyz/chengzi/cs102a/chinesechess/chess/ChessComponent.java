package xyz.chengzi.cs102a.chinesechess.chess;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardPoint;
import xyz.chengzi.cs102a.chinesechess.listener.AIListener;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public abstract class ChessComponent extends JComponent {
    public final static Dimension CHESS_SIZE = new Dimension(50, 50);
//    public final static Color CHESS_COLOR = new Color(254, 218, 164);
    public final static Color CHESS_COLOR = Color.WHITE;

    private static List<ChessListener> listenerList = new ArrayList<>();
    private static AIListener aiListener = new AIListener();
    private ChessboardPoint chessboardPoint;
    private ChessColor chessColor;
    private boolean selected;

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor) {
            enableEvents(AWTEvent.MOUSE_EVENT_MASK);
            setLocation(location);
            setSize(CHESS_SIZE);

            this.chessboardPoint = chessboardPoint;
            this.chessColor = chessColor;
            this.selected = false;


    }
    // la added it here
//    protected void AIMovePiece() {
//        if(!(isHuman_turn())) {
//            ()
//
//        }
//    }
    // I made black, a human
    protected boolean isHuman_turn() {
        if (!(getChessColor().equals(ChessColor.BLACK))) {
            return true;
        } else {
            return false;
        }
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void swapLocation(ChessComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {

        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            for (ChessListener listener : listenerList) {
                try {
                    listener.onClick(this);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public abstract ArrayList<ChessComponent> getPossibleMoves();
    public abstract ArrayList<ChessComponent> generatePossibleMoves(ChessComponent[][] chessboard);
    public abstract boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination);

    public static boolean registerListener(ChessListener listener) {
        return listenerList.add(listener);
    }

    public static boolean unregisterListener(ChessListener listener) {
        return listenerList.remove(listener);
    }

    //return character for output to file
    //new
    //add new piece later
    //Davong add this here
    public static Character getComponentCharacter(ChessComponent ComponentType){
        //System.out.println(ComponentType.getChessColor());
        if(ComponentType instanceof ChariotChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'C' : 'c';
        else if(ComponentType instanceof CounsellorChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'A' : 'a';
        else if(ComponentType instanceof ElephantChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'E' : 'e';
        else if(ComponentType instanceof SoldierChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'S' : 's';
        else if(ComponentType instanceof CannonChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'N' : 'n';
        else if(ComponentType instanceof CannonChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'N' : 'n';
        else if(ComponentType instanceof GeneralChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'G' : 'g';
        else if(ComponentType instanceof HorseChessComponent)
            return ComponentType.getChessColor() == ChessColor.BLACK ? 'H' : 'h';
        //need to add general and horse return character
        return '.';
    }
    @Override
    public String toString() {
        return chessboardPoint.getX() + "" + chessboardPoint.getY() + "" + chessColor.getName();
    }
    protected static void highlightSquare(Graphics g) {
        g.drawRoundRect(0,0,30,30,10,10);
    }
}

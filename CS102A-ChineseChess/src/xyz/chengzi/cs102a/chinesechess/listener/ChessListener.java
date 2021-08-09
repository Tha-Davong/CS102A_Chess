package xyz.chengzi.cs102a.chinesechess.listener;

import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import java.io.FileNotFoundException;

public abstract class ChessListener {
    protected ChessboardComponent chessboardComponent;
    protected ChessComponent first;
    protected static int NumberofMove = 0;
    protected boolean human_turn = true;

    public void onClick(ChessComponent chessComponent) throws FileNotFoundException {
        // Do absolutely nothing.
    }
    public void AIMove() throws FileNotFoundException {

    }
}

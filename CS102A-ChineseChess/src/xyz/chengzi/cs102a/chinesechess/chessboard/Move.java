package xyz.chengzi.cs102a.chinesechess.chessboard;

import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;

import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import java.sql.Time;
import java.time.LocalTime;

public class Move {
    ChessComponent chess1;
    ChessComponent chess2;
    public Move(ChessComponent chess1, ChessComponent chess2) {
        this.chess1 = chess1;
        this.chess2 = chess2;
        chess1.swapLocation(chess2);
    }

}

package xyz.chengzi.cs102a.chinesechess.listener;

import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;
import xyz.chengzi.cs102a.chinesechess.chessboard.ChessboardComponent;

import java.io.FileNotFoundException;

public class AIListener extends ChessListener {

    public AIListener() {
    }
    @Override
    public void AIMove() throws FileNotFoundException {
        chessboardComponent.AIRandomMove();
        chessboardComponent.swapChessComponents(chessboardComponent.AIPiece,chessboardComponent.AIMove);
        chessboardComponent.swapColor();
    }
}

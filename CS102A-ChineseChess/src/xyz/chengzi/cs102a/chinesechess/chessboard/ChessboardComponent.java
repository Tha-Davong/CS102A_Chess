package xyz.chengzi.cs102a.chinesechess.chessboard;



import xyz.chengzi.cs102a.chinesechess.chess.*;
import xyz.chengzi.cs102a.chinesechess.listener.ChessListener;
import xyz.chengzi.cs102a.chinesechess.listener.ChessboardChessListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ChessboardComponent extends JComponent {
    private ChessListener chessListener = new ChessboardChessListener(this);
    private ChessComponent[][] chessboard = new ChessComponent[10][9];
    private ChessColor currentColor = ChessColor.BLACK;
    private boolean humanTurn;
    public ChessComponent AIPiece;
    public ChessComponent AIMove;
    public int k = 0;
    private boolean endgame_status = false;
    public boolean getendgame_status(){return endgame_status;}

    private ArrayList<String> Movesequence = new ArrayList<String>();
    private static int NumberofMove = 0;
    public static int getNumberofMove() {
        return NumberofMove;
    }



    private ArrayList <Move> moves = new ArrayList<Move>();
    private ArrayList <ChessComponent> chessComponents = new ArrayList<ChessComponent>();

    public ChessboardComponent(int width, int height) throws FileNotFoundException {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        ChessComponent.registerListener(chessListener);
        for (int i = 0; i < chessboard.length; i++) {
            for (int j = 0; j < chessboard[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j)));
            }
        }

        // FIXME: Initialize chessboard for testing only.
        boolean newGame = true;//temporary for starting new game
        //newGame = true
        if(newGame)
            StartnewGame();
        else {
            //LoadGame();
        }

    }

    //Davong put this here
    public void StartnewGame(){
        // FIXME: Initialize chessboard for testing only.
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                emptySpace(i,j);
            }
        }

        addChariot(0, 0, ChessColor.BLACK);
        addChariot(0, 8, ChessColor.BLACK);
        addChariot(9, 0, ChessColor.RED);
        addChariot(9, 8, ChessColor.RED);

        addSoldier(3,0,ChessColor.BLACK);
        addSoldier(3,2,ChessColor.BLACK);
        addSoldier(3,4,ChessColor.BLACK);
        addSoldier(3,6,ChessColor.BLACK);
        addSoldier(3,8,ChessColor.BLACK);
        addSoldier(6,0,ChessColor.RED);
        addSoldier(6,2,ChessColor.RED);
        addSoldier(6,4,ChessColor.RED);
        addSoldier(6,6,ChessColor.RED);
        addSoldier(6,8,ChessColor.RED);

        addCannon(2,1,ChessColor.BLACK);
        addCannon(2,7,ChessColor.BLACK);
        addCannon(7,1,ChessColor.RED);
        addCannon(7,7,ChessColor.RED);

        addCounsellor(0,3,ChessColor.BLACK);
        addCounsellor(0,5,ChessColor.BLACK);
        addCounsellor(9,3,ChessColor.RED);
        addCounsellor(9,5,ChessColor.RED);

        addElephant(0,2,ChessColor.BLACK);
        addElephant(0,6,ChessColor.BLACK);
        addElephant(9,2,ChessColor.RED);
        addElephant(9,6,ChessColor.RED);
        addHorse(0,1,ChessColor.BLACK);
        addHorse(0,7,ChessColor.BLACK);
        addHorse(9,1,ChessColor.RED);
        addHorse(9,7,ChessColor.RED);
        addGeneral(0,4,ChessColor.BLACK);
        addGeneral(9,4,ChessColor.RED);
    }

    public void LoadGame(String loadgame) throws FileNotFoundException {
        File load = new File(loadgame);
        Scanner input = new Scanner(load);
        String findHead = new String();
        String OnelineBoard = new String();
        while(!findHead.equals("@@")){
            findHead = input.nextLine();
        }
        input.nextLine();
        //System.out.println(findHead);
        ArrayList<String> boardStringrArray = new ArrayList<String>();
        while(input.hasNextLine()){
            String tmp = new String();
            tmp = input.nextLine();
            boardStringrArray.add(tmp);
            OnelineBoard = OnelineBoard.concat(tmp);
        }

        boolean wrongDimension = false;
        if(boardStringrArray.size() != 11){
            //what about location of wrong width
            System.out.println("Wrong width/vertical");
            wrongDimension = true;
        }
        for(int i = 0; i < boardStringrArray.size(); ++i){
            if(boardStringrArray.get(i).length() == 0)
                continue;
            if(boardStringrArray.get(i).length() != 9) {
                if(i < 5 )
                    System.out.println("row:" + (i+1) + "\nWrong length/horizontal");
                if(i > 5)
                    System.out.println("row:" + i + "\nWrong length/horizontal");
                wrongDimension = true;
            }
        }

        if(wrongDimension) {
            System.out.println(load.getName() + "\n");
            JOptionPane.showMessageDialog(null,"Load game aborted","Loading game", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        //check for river only when dimension is correct
        //System.out.println(boardStringrArray.size());
        if(!OnelineBoard.contains("---------")){
            System.out.println("river missing");
            System.out.println(load.getName() + "\n");
            return;
        }else{
            //OnelineBoard still got river
            boardStringrArray.remove("---------");
        }
        //System.out.println(OnelineBoard);

        //from this point correct dimension and contain river
        //check each team pieces (add new piece later)
        boolean WrongPieceAmount = false;
        int Pieceamount = countOccurrences(OnelineBoard,'N');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wronng cannon on black");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'n');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wronng cannon on red");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'S');
        if(Pieceamount > 5 || Pieceamount < 0){
            System.out.println("Wrong soldier on black");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'s');
        if(Pieceamount > 5 || Pieceamount < 0){
            System.out.println("Wrong soldiers on red");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'A');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong advisor on black");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'a');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong advisor on red");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'E');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong Elephant on black");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'e');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong Elepant on red");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'C');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong Chariot on black");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'c');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong chariot on red");
            WrongPieceAmount = true;
        }
        Pieceamount = countOccurrences(OnelineBoard,'G');
        if(Pieceamount != 1){
            System.out.println("Wrong general on black");
            WrongPieceAmount = true;
        }

        Pieceamount = countOccurrences(OnelineBoard,'g');
        if(Pieceamount != 1){
            System.out.println("Wrong general on red");
            WrongPieceAmount = true;
        }

        Pieceamount = countOccurrences(OnelineBoard,'H');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong horse on black");
            WrongPieceAmount = true;
        }

        Pieceamount = countOccurrences(OnelineBoard,'h');
        if(Pieceamount > 2 || Pieceamount < 0){
            System.out.println("Wrong horse on red");
            WrongPieceAmount = true;
        }


        if (WrongPieceAmount) {
            System.out.println(load.getName() +"\n");
            JOptionPane.showMessageDialog(null,"Load game aborted","Loading game", JOptionPane.PLAIN_MESSAGE);
            return;
        }
        //what is space missing ?
        if(countpieceOccurrences(OnelineBoard) + countOccurrences(OnelineBoard,'.') < 90) {
            System.out.println("Missing space\n");
            JOptionPane.showMessageDialog(null,"Load game aborted","Loading game", JOptionPane.PLAIN_MESSAGE);
            return;
        }

        for(String s : boardStringrArray)
            System.out.println(s);


        for(int i = 0; i < 10; ++i) {
            for (int j = 0; j < 9; ++j) {
                putChessOnBoard(getComponentObject(boardStringrArray.get(i).charAt(j), i, j));
            }
        }
        JOptionPane.showMessageDialog(null,"Load game successful","Loading game", JOptionPane.PLAIN_MESSAGE);
    }

    public ChessComponent[][] getChessboard() {
        return chessboard;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessboard[row][col] != null) {
            remove(chessboard[row][col]);
        }
        add(chessboard[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) throws FileNotFoundException {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        ++NumberofMove;
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation()));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessboard[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessboard[row2][col2] = chess2;

        if(endGame()){
            endgame_status = true;
            System.out.println("Someone win");
            JOptionPane.showMessageDialog(null,getNumberofMove()%2 == 0 ? "Red win" : "Black win", "Winner",JOptionPane.PLAIN_MESSAGE);
            StartnewGame();
        }

        outputMoveseq(row1,row2,col1,col2);
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK;
    }

    private void addSoldier(int row, int col, ChessColor color){
        ChessComponent chessComponent = new SoldierChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);

        chessComponents.add(chessComponent);
    }
    private void addChariot(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new ChariotChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);

        chessComponents.add(chessComponent);
    }
    private void addCannon(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new CannonChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);

        chessComponents.add(chessComponent);
    }
    private void addCounsellor(int row, int col, ChessColor color) {
        //need to add my piece here
        //this is where you set the location of the pieces on the board
        ChessComponent chessComponent = new CounsellorChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        //then you get that piece and put it on the board
        putChessOnBoard(chessComponent);

        chessComponents.add(chessComponent);
    }
    private void addElephant(int row, int col, ChessColor color) {
        //need to add my piece here
        //this is where you set the location of the pieces on the board
        ChessComponent chessComponent = new ElephantChessComponent(new ChessboardPoint(row, col),
                calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        //then you get that piece and put it on the board
        putChessOnBoard(chessComponent);

        chessComponents.add(chessComponent);
    }
    private void addGeneral(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new GeneralChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessboard[row][col] = chessComponent;

        chessComponents.add(chessComponent);
    }
    private void addHorse(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new HorseChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);

        chessComponents.add(chessComponent);
    }
    private void emptySpace(int row, int col) {
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row,col), calculatePoint(row,col));
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    public boolean endGame() {
        int numberOfGenerals = 0;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 9; col++) {
                if ((chessboard[row][col] instanceof GeneralChessComponent)) {
                    numberOfGenerals += 1;
                }
            }
        }
        return numberOfGenerals == 1;
    }
    protected void canMovePiece(ChessComponent chess1, ChessComponent chess2) throws FileNotFoundException {
        if (chess1.canMoveTo(chessboard, chess2.getChessboardPoint())) {
            swapChessComponents(chess1,chess2);
            moves.add(new Move(chess1,chess2));
        }
    }
    protected void allMoves() {
        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 9; j++) {
                for (ChessComponent chessComponent : chessComponents) {
                    if (chessComponent.canMoveTo(chessboard, chessboard[i][j].getChessboardPoint())) {
                        moves.add(new Move(chessComponent, chessboard[i][j]));
                    }

                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        paintBoardLine(g, 0, 0, 9, 0);
        paintBoardLine(g, 0, 8, 9, 8);
        paintHalfBoard(g, 0);
        paintHalfBoard(g, 5);
        ((Graphics2D) g).setStroke(new BasicStroke(3));
        paintKingSquare(g, 1, 4);
        paintKingSquare(g, 8, 4);
    }

    private void paintHalfBoard(Graphics g, int startRow) {
        g.setColor(Color.BLACK);
        for (int row = startRow; row < startRow + 5; row++) {
            paintBoardLine(g, row, 0, row, 8);
        }
        for (int col = 0; col < 9; col++) {
            paintBoardLine(g, startRow, col, startRow + 4, col);
        }
    }

    private void paintKingSquare(Graphics g, int centerRow, int centerCol) {
        g.setColor(Color.BLACK);
        paintBoardLine(g, centerRow - 1, centerCol - 1, centerRow + 1, centerCol + 1);
        paintBoardLine(g, centerRow - 1, centerCol + 1, centerRow + 1, centerCol - 1);
    }

    private void paintBoardLine(Graphics g, int rowFrom, int colFrom, int rowTo, int colTo) {
        g = (Graphics2D) g;
        g.setColor(Color.BLACK);

        int offsetX = ChessComponent.CHESS_SIZE.width / 2, offsetY = ChessComponent.CHESS_SIZE.height / 2;
        Point p1 = calculatePoint(rowFrom, colFrom), p2 = calculatePoint(rowTo, colTo);
        g.drawLine(p1.x + offsetX, p1.y + offsetY, p2.x + offsetX, p2.y + offsetY);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * getWidth() / 9, row * getHeight() / 10);
    }

    //Davong put this here
    public void SavecurentGame() throws FileNotFoundException {
        String filename = JOptionPane.showInputDialog("Name this file");
        Formatter saveGame = new Formatter(filename + ".txt");
        Character chessTypeCharacter;
        if(getNumberofMove() % 2 == 1)
            saveGame.format("@LAST_MOVER=BLACK%n");
        else if(getNumberofMove() % 2 == 0)
            saveGame.format("@LAST_MOVER=RED%n");
        saveGame.format("@@%n%n");

        for(int i = 0; i < 10; ++i){
            for(int j = 0; j < 9; ++j){
                chessTypeCharacter = ChessComponent.getComponentCharacter(chessboard[i][j]);
                saveGame.format("%s",chessTypeCharacter);
            }
            if(i == 4){
                saveGame.format("%n");
                saveGame.format("---------");
            }
            saveGame.format("%n");
        }
        saveGame.close();
    }
    //add new piece later
    public ChessComponent getComponentObject(Character ComponentCharacter, int row, int col) {
        if (ComponentCharacter == 'C' || ComponentCharacter == 'c') {
            //need the row and column from textfile to construct chesscomponenet object
            if(Character.isLowerCase(ComponentCharacter))
                return new ChariotChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.RED);
            else
                return new ChariotChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.BLACK);
        }else if (ComponentCharacter == 'N' || ComponentCharacter == 'n') {
            //need the row and column from textfile to construct chesscomponenet object
            if(Character.isLowerCase(ComponentCharacter))
                return new CannonChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.RED);
            else
                return new CannonChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.BLACK);
        }else if (ComponentCharacter == 'E' || ComponentCharacter == 'e') {
            //need the row and column from textfile to construct chesscomponenet object
            if(Character.isLowerCase(ComponentCharacter))
                return new ElephantChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.RED);
            else
                return new ElephantChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.BLACK);
        }else if (ComponentCharacter == 'A' || ComponentCharacter == 'a') {
            //need the row and column from textfile to construct chesscomponenet object
            if(Character.isLowerCase(ComponentCharacter))
                return new CounsellorChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.RED);
            else
                return new CounsellorChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.BLACK);
        }else if (ComponentCharacter == 'S' || ComponentCharacter == 's') {
            //need the row and column from textfile to construct chesscomponenet object
            if(Character.isLowerCase(ComponentCharacter))
                return new SoldierChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.RED);
            else
                return new SoldierChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.BLACK);
        }else if(ComponentCharacter == 'G' || ComponentCharacter == 'g'){
            if(Character.isLowerCase(ComponentCharacter))
                return new GeneralChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.RED);
            else
                return new GeneralChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.BLACK);
        }else if(ComponentCharacter == 'H' || ComponentCharacter == 'h'){
            if(Character.isLowerCase(ComponentCharacter))
                return new HorseChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.RED);
            else
                return new HorseChessComponent(new ChessboardPoint(row,col), calculatePoint(row, col),ChessColor.BLACK);
        }
        return new EmptySlotComponent(new ChessboardPoint(row,col), calculatePoint(row, col));
    }

    public static int countOccurrences(String OneLineboard, char piece)
    {
        int count = 0;
        for (int i=0; i < OneLineboard.length(); i++) {
            if (OneLineboard.charAt(i) == piece)
                ++count;
        }
        return count;
    }

    public static int countpieceOccurrences(String OneLineboard)
    {
        int count = 0;
        for (int i=0; i < OneLineboard.length(); i++) {
            if (Character.isAlphabetic(OneLineboard.charAt(i)))
                ++count;
        }
        //System.out.println(count);
        return count;
    }

    //Davong add this here

    public void LoadingMoveseq(String moveseq) throws FileNotFoundException{
        File chessmoveseq = new File(moveseq);
        Scanner input = new Scanner(chessmoveseq);
        String filehead = new String();
        while(!filehead.equals("@@")){
            filehead = input.nextLine();
        }
        input.nextLine();
        int row1 = 0, col1 = 0; //first position
        int row2 = 0, col2 = 0; // second position
        ArrayList<String> chessMoveseqArray = new ArrayList<String>();
        while(input.hasNextLine()){
            chessMoveseqArray.add(input.nextLine());
        }
        for(String s : chessMoveseqArray) {
            String[] SingleMoveSequence = s.split(" ");
            col1 = Integer.parseInt(SingleMoveSequence[0]);
            row1 = Integer.parseInt(SingleMoveSequence[1]);
            col2 = Integer.parseInt(SingleMoveSequence[2]);
            row2 = Integer.parseInt(SingleMoveSequence[3]);
            System.out.println(getNumberofMove()%2 == 0 ? "black move" : "red move");
            System.out.println(col1 + " " + row1 + " " + col2 + " " + row2);

            //add condition here in the if else
            if(getNumberofMove() % 2 == 0){ //even for black and odd for red
//                System.out.println(col1-1);
//                System.out.println(row1-1);
                if(ValidMoveseq(col1-1,row1-1,col2-1,row2-1)) {
                    swapChessComponents(chessboard[row1 - 1][col1 - 1], chessboard[row2 - 1][col2 - 1]);
                    swapColor();
                    System.out.println("\n");
                }
            }else{
                if(ValidMoveseq(9-col1,10-row1,9-col2,10-row2)) {
                    swapChessComponents(chessboard[10 - row1][9 - col1], chessboard[10 - row2][9 - col2]);
                    swapColor();
                    System.out.println("\n");
                }
            }
        }
    }

    public boolean ValidMoveseq(int col1, int row1, int col2, int row2){//pass usable index (row1 - 1 )
        if(col1 > 8 || col2 > 8 || row1 > 9 || row2 > 9){
            System.out.println("Position out of range\n");
            return false;
        }else if(col1 < 0 || row1 < 0 || col2 < 0 || row2 < 0){
            System.out.println("Position out of range negative\n");
            return false;
        }
        //Invalid from position
        if(chessboard[row1][col1] instanceof EmptySlotComponent){
            System.out.println("start Position is empty\n");
            return false;
        }
        //no piece of current player
        if(getNumberofMove() % 2 == 0){
            if(!chessboard[row1][col1].getChessColor().equals(ChessColor.BLACK)){
                System.out.println("start position have no player black piece\n");
                return false;
            }
        }else{
            if(!chessboard[row1][col1].getChessColor().equals(ChessColor.RED)){
                System.out.println("Start position have no player red piece\n");
                return false;
            }
        }
        //invalid destination
        if(getNumberofMove() % 2 == 0){
            if(chessboard[row2][col2].getChessColor().equals(ChessColor.BLACK)){
                System.out.println("destination have player black piece\n");
                return false;
            }
        }else{
            if(chessboard[row2][col2].getChessColor().equals(ChessColor.RED)){
                System.out.println("destination have player red piece\n");
                return false;
            }
        }
        //invalid move pattern
        if(!chessboard[row1][col1].canMoveTo(chessboard,chessboard[row2][col2].getChessboardPoint())){
            System.out.println("Invalid move pattern" + ((getNumberofMove()%2 == 0) ? " black\n" : " red\n"));
            return false;
        }
        return true;
    }
    public void outputMoveseq(int row1, int row2, int col1, int col2) throws FileNotFoundException {
        if(getNumberofMove() % 2 == 0){
            Movesequence.add((9 - col2) + " " + (10 - row2) + " " + (9 - col1) + " " + (10 - row1));

        }else{
            Movesequence.add((col2+1) + " " + (row2+1) + " " + (col1+1) + " " + (row1+1));
        }

        Formatter Movesequence_formatter = new Formatter("gameplay_chessmoveseq.txt");
        Movesequence_formatter.format("@TOTAL_STEP=%d%n@@%n%n",NumberofMove);
        for(String c : Movesequence){
            Movesequence_formatter.format(c+"%n");
        }
        Movesequence_formatter.close();
    }


    public void AIRandomMove() {
        Random rand = new Random();
        boolean isRightPiece = false;
        int rowRand = 0;
        int colRand = 0;
        while(!(isRightPiece)) {
            rowRand = rand.nextInt(10);
            colRand = rand.nextInt(9);
            if (!(chessboard[rowRand][colRand] instanceof EmptySlotComponent) && chessboard[rowRand][colRand].getChessColor().equals(ChessColor.BLACK)) {
                isRightPiece = true;
            }
        }

        AIPiece = getChessboard()[rowRand][colRand];
        int i = rand.nextInt(Math.abs(AIPiece.generatePossibleMoves(getChessboard()).size()));
        AIMove = getChessboard()[rowRand][colRand].generatePossibleMoves(getChessboard()).get(i);
    }
    public void AIMove() throws FileNotFoundException {
        if (currentColor.equals(ChessColor.BLACK))
        AIRandomMove();
       swapChessComponents(AIPiece,AIMove);
       swapColor();
    }

}

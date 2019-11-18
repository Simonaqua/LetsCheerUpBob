import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static class GameStatus {
        boolean bobsTurn;
        Vector<MyMoves> myMoves;
        char[][] mat;
        int movNum;
        public GameStatus(){
            mat = new char[3][3];
            myMoves = new Vector<MyMoves>();

        }

        public GameStatus(GameStatus gs){
            myMoves = new Vector<MyMoves>();
            for (int i = 0 ; i<gs.myMoves.size() ; i++){
                this.myMoves.add(gs.getMyMoves().elementAt(i));
            }
            mat = new char[3][3];
            for(int i = 0 ; i<3 ; i++){
                for(int j = 0 ; j <3 ; j++){
                    mat[i][j] = gs.getMat()[i][j];
                }
            }
            this.bobsTurn = gs.isBobsTurn();
            this.movNum = gs.getMovNum();
        }

        public char[][] getMat() {
            return mat;
        }

        public void setMat(char[][] mat) {
            this.mat = mat;
        }

        public int getMovNum() {
            return movNum;
        }

        public void setMovNum(int movNum) {
            this.movNum = movNum;
        }

        public boolean isBobsTurn() {
            return bobsTurn;
        }

        public void setBobsTurn(boolean bobsTurn) {
            this.bobsTurn = bobsTurn;
        }

        public Vector<MyMoves> getMyMoves() {
            return myMoves;
        }

        public void setMyMoves(Vector<MyMoves> myMoves) {
            this.myMoves = myMoves;
        }
        public void addMoves(int x , int y){
            this.myMoves.add(new MyMoves(x,y));
        }
    }

    public static class MyMoves {
        public int x;
        public int y;

        public MyMoves(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }




    public static int whoWins(GameStatus curr){


        for(int i = 0 ; i<3 ; i++){
            int xCount = 0;
            int oCount = 0;
            for( int j = 0; j<3 ; j++){
                if(curr.mat[i][j] == 'X'){
                    xCount++;
                }
                if(curr.mat[i][j] == 'O'){
                    oCount++;
                }
            }
            if(xCount == 3){
                return 1;
            }
            if(oCount ==3){
                return 0;
            }
        }
        for(int  j = 0 ; j<3 ; j++){
            int xCount = 0;
            int oCount = 0;
            for(int  i = 0 ; i<3 ; i++){
                if(curr.mat[i][j] == 'X'){
                    xCount++;
                }
                if(curr.mat[i][j] == 'O'){
                    oCount++;
                }
            }
            if(xCount == 3){
                return 1;
            }
            if(oCount == 3){
                return 0;
            }
        }

        int leftDiagO =0;
        int leftDiagX = 0;
        int rightDiagX = 0;
        int rightDiagO = 0;
        for( int i = 0 ;i<3 ; i++){
            if(curr.getMat()[i][i] == 'X'){
                 leftDiagX++;
            }
            if(curr.getMat()[i][i] == 'O'){
                leftDiagO++;
            }
            if(curr.getMat()[i][2-i] == 'X'){
                rightDiagX++;
            }
            if(curr.getMat()[i][2-i] == 'O'){
                rightDiagO++;
            }
        }
        if(leftDiagX == 3 || rightDiagX == 3){
            return 1;
        }
        if( leftDiagO ==3 || rightDiagO == 3){
            return 0;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int[] bobsCols = new int[9];
        int[] bobsRows = new int[9];
        int i;
        int j;
        for(i = 0 ; i<9 ; i++){
            bobsRows[i] = s.nextInt();
            bobsCols[i] = s.nextInt();
            bobsRows[i]--;
            bobsCols[i]--;
        }
        Queue<GameStatus> turnsQueue = new LinkedBlockingQueue<GameStatus>();
        GameStatus firstTurn = new GameStatus();

        for(i = 0 ; i<3 ; i++){
            for(j = 0 ; j<3 ; j++){
                firstTurn.getMat()[i][j] = '-';
            }
        }

        firstTurn.setMovNum(0);
        firstTurn.setBobsTurn(true);
        turnsQueue.offer(firstTurn);

        while (!turnsQueue.isEmpty()){
            GameStatus temp = turnsQueue.peek();
            turnsQueue.poll();

            int winner = whoWins(temp);
            if(winner == 0)
                continue;
            if(winner == 1){
                for( i = 0 ; i<temp.myMoves.size() ; i++){
                    System.out.println((temp.myMoves.get(i).x+1) + " " + (temp.myMoves.get(i).y+1));
                }
                break;
            }
            if(temp.bobsTurn){
                int lastMove  = temp.movNum;
                boolean possibleMove = false;

                for(i = lastMove ; i< 9 & !possibleMove ; i++){
                    int col = bobsCols[i];
                    int row = bobsRows[i];

                    if(temp.mat[row][col] == '-'){
                        temp.mat[row][col] = 'X';
                        temp.movNum = i+1;
                        temp.bobsTurn = false;
                        possibleMove = true;
                        turnsQueue.offer(temp);
                    }
                }
            }
            else {
                for( i = 0 ; i<3 ; i++){
                    for( j = 0 ; j<3 ; j++){
                        if(temp.mat[i][j] != '-')
                            continue;

                        GameStatus newStatus = new GameStatus(temp);
                        newStatus.mat[i][j] = 'O';
                        newStatus.setBobsTurn(true);
                        newStatus.addMoves(i,j);
                        turnsQueue.offer(newStatus);
                    }
                }
            }
        }
    }
}

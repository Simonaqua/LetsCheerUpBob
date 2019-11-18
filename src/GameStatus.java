import java.util.ArrayList;
import java.util.Vector;

public class GameStatus {
    public GameStatus(){

    };
    public char[][] mat = new char[3][3];
    public int movNum;
    public boolean bobsTurn;
    public ArrayList<MyMoves> myMoves = new ArrayList<>();
}

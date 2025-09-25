package g62588.dev3.oxono.model.event;

import g62588.dev3.oxono.model.Color;
import g62588.dev3.oxono.model.Pawn;
import g62588.dev3.oxono.model.Position;
import g62588.dev3.oxono.model.Token;
import javafx.geometry.Pos;

import java.util.Stack;

public class StartGameEvent implements Event{
    private Position totemO;
    private Position totemX;
    private int size;
    private Color color;
    private int emptyCase;
    private int pawnPinkO;
    private int pawnPinkX;
    private int pawnBlackO;
    private int pawnBlackX;


    public StartGameEvent(Position totemO, Position totemX, int size, Color color, int emptyCase, int pawnPinkO, int pawnPinkX, int pawnBlackO, int pawnBlackX){
        this.totemO = totemO;
        this.totemX = totemX;
        this.size = size;
        this.color = color;
        this.emptyCase = emptyCase;
        this.pawnPinkO = pawnPinkO;
        this.pawnPinkX = pawnPinkX;
        this.pawnBlackO = pawnBlackO;
        this.pawnBlackX = pawnBlackX;

    }

    public Position getTotemO() {
        return totemO;
    }

    public Position getTotemX() {
        return totemX;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public int getPawnPinkO() {
        return pawnPinkO;
    }

    public int getPawnPinkX() {
        return pawnPinkX;
    }

    public int getPawnBlackO() {
        return pawnBlackO;
    }

    public int getPawnBlackX() {
        return pawnBlackX;
    }

    public int getEmptyCase() {
        return emptyCase;
    }
}
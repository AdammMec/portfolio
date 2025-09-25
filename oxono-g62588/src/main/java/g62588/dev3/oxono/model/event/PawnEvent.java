package g62588.dev3.oxono.model.event;

import g62588.dev3.oxono.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PawnEvent implements Event {
    private Position pos;
    private Color roundPlayerColor;
    private Color pawnColor;
    private Symbole symbole;
    private ArrayList<Position> validInsertPawn;
    private int emptyCase;
    private int pawnO;
    private int pawnX;

    public PawnEvent(Position pos, Color color, Color pawnColor, Symbole symbole, ArrayList<Position> validInsertPawn, int emptyCase, int pawnPinkO, int pawnPinkX){
        this.pos = pos;
        this.roundPlayerColor = color;
        this.symbole = symbole;
        this.validInsertPawn = validInsertPawn;
        this.emptyCase = emptyCase;
        this.pawnO = pawnPinkO;
        this.pawnX = pawnPinkX;
        this.pawnColor = pawnColor;

    }

    public Color getPawnColor() {
        return pawnColor;
    }

    public Position getPos() {
        return pos;
    }

    public Color getColor() {
        return roundPlayerColor;
    }

    public Symbole getSymbole() {
        return symbole;
    }

    public ArrayList<Position> getValidInsertPawn() {
        return validInsertPawn;
    }

    public int getEmptyCase() {
        return emptyCase;
    }

    public int getPawnO() {
        return pawnO;
    }

    public int getPawnX() {
        return pawnX;
    }
}

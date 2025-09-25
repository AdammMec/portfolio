package g62588.dev3.oxono.model.event;

import g62588.dev3.oxono.model.Color;
import g62588.dev3.oxono.model.Position;
import g62588.dev3.oxono.model.Symbole;
import g62588.dev3.oxono.model.Token;

import java.util.ArrayList;

public class TotemEvent implements Event{
    private Position init;
    private Position end;
    ArrayList<Position> validInsertPawn;
    Symbole symbole;


    public TotemEvent(Position init, Position end, ArrayList<Position> validInsertPawn, Symbole symbole){
        this.init = init;
        this.end = end;
        this.validInsertPawn = validInsertPawn;
        this.symbole = symbole;
    }

    public Position getInit() {
        return init;
    }

    public Position getEnd() {
        return end;
    }

    public Symbole getSymbole() {
        return symbole;
    }

    public ArrayList<Position> getValidInsertPawn() {
        return validInsertPawn;
    }
}

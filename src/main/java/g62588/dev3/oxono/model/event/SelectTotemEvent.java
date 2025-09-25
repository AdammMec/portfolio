package g62588.dev3.oxono.model.event;

import g62588.dev3.oxono.model.Position;

import java.util.ArrayList;

public class SelectTotemEvent implements Event{
    ArrayList<Position> validTotemMove;

    public SelectTotemEvent(ArrayList<Position> validTotemMove){
        this.validTotemMove = validTotemMove;
    }

    public ArrayList<Position> getValidTotemMove() {
        return validTotemMove;
    }
}

package g62588.dev3.oxono.model.event;

import g62588.dev3.oxono.model.Color;

public class EndGameEvent implements Event{
    private boolean win;
    private Color color;
    public EndGameEvent(boolean win, Color color){
        this.win = win;
        this.color = color;
    }

    public boolean isWin() {
        return win;
    }

    public Color getColor() {
        return color;
    }
}

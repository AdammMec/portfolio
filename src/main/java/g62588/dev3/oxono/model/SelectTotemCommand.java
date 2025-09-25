package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.commands.Command;

/**
 * Facade call this class to select the totem and the valid position to move it
 */
class SelectTotemCommand implements Command {
    private Game game;
    private Position pos;
    private Symbole newSymbole;

    /**
     * init the select totem command with the information that we need to select totem
     * @param game facade
     * @param pos position of totem
     * @param newSymbole symbole of the last move
     */
    public SelectTotemCommand(Game game, Position pos, Symbole newSymbole){
        this.game = game;
        this.pos = pos;
        this.newSymbole = newSymbole;
    }

    /**
     * execute the command and create an event
     */
    @Override
    public void execute() {
        game.selectTotemEvent(pos, newSymbole);
    }

    /**
     * unexecute the command and create an event
     */
    @Override
    public void unexecute() {
        game.selectTotemEvent(pos, newSymbole);
    }
}

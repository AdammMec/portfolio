package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.commands.Command;

/**
 * Class use by the facade to move the totem by commincating with the rest of the model
 */
class MoveTotemCommand implements Command {
    private Position init;
    private Position end;
    private Game game;
    private Symbole symbole;

    /**
     * Move totem initializer
     * @param game facade of the game
     * @param symbole symbole of the totem
     * @param end finale position of the totem (the place where we have to move it)
     */
    public MoveTotemCommand(Game game, Symbole symbole, Position init , Position end){
        this.end = end;
        this.game = game;
        this.symbole = symbole;
        this.init = init;
    }

    /**
     * Move the totem to the right case, edit the facade and share the event
     */
    @Override
    public void execute() {
        game.editSymbole(symbole);
        game.moveTotemInBoard(end, this.symbole, GameState.INSERT);
    }
    /**
     * Move the totem to the old case, edit the facade and share the event
     */
    @Override
    public void unexecute() {
        game.moveTotemInBoard(init, this.symbole, GameState.MOVE);
    }
}

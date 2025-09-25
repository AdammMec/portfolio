package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.commands.Command;

/**
 * The facade use this command to communicate with the other class of the model to insert a pawn in the board
 */
class InsertPawnCommand implements Command {
    private Position pos;
    private Pawn pawn;
    private Game game;

    private Color color;

    /**
     * Init the command
     * @param game facade
     * @param pawn pawn we play
     * @param pos position in the board where we have to insert pawn
     * @param color color of the player
     */
    public InsertPawnCommand(Game game, Position pos, Pawn pawn, Color color){
        this.pos = pos;
        this.game = game;
        this.pawn = pawn;
        this.color = color;
    }

    /**
     * Method we use to insert pawn command, to edit the facade state like gamestate or symbole and to share the event
     */
    @Override
    public void execute(){
        this.pawn = this.game.usePawn(this.pawn.getSymbole());
        this.game.insertPawnInBoard(pos, pawn, pawn.getColor(), pawn.getSymbole(), GameState.MOVE);
        this.game.switchPlayers();
    }

    /**
     * Method we use to UNinsert pawn command, to edit the facade state like gamestate or symbole and to share the event
     */
    @Override
    public void unexecute() {
        this.game.endGameState(false, null, GameState.INSERT);
        this.game.returnPawn(pos, pawn);
        this.game.switchPlayers();
        this.game.insertPawnInBoard(pos, null, color, pawn.getSymbole(), GameState.INSERT);
    }
}

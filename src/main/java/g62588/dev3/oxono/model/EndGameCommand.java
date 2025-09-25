package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.commands.Command;

class EndGameCommand implements Command {
    Game game;
    boolean win;
    Color color;
    GameState gameState;

    public EndGameCommand(Game game, boolean win, Color color, GameState gameState){
        this.game = game;
        this.win = win;
        this.gameState = gameState;
        this.color = color;
    }

    @Override
    public void execute(){
        game.endGameState(win, color, GameState.FINISHED);

    }

    @Override
    public void unexecute(){
        game.endGameState(false, color, gameState);
    }
}

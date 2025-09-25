package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.event.*;

public class EventBuilder {
    private final Game game;
    EventBuilder(Game game){
        this.game = game;
    }

    SelectTotemEvent selectTotemEvent(){
        return new SelectTotemEvent(game.getValidMove());
    }

    TotemEvent totemEvent(Position init, Position end){
        return new TotemEvent(init, end, game.getValidMove(), game.getSymbole());
    }

    PawnEvent pawnEvent(Position pos){
        return new PawnEvent(pos,
                this.game.getPlayerRound(),
                this.game.getCurrentColor(),
                this.game.getSymbole(),
                this.game.getValidMove(),
                this.game.getEmptyCase(),
                this.game.getCurrentPawnO(),
                this.game.getCurrentPawnX());
    }

    EndGameEvent endGameEvent(){
        return new EndGameEvent(this.game.isWin(), this.game.getPlayerRound());
    }

    StartGameEvent startGameEvent(Position totemO, Position totemX){
        return new StartGameEvent(
                totemO,
                totemX,
                this.game.getBoardSize(),
                this.game.getPlayerRound(),
                this.game.getEmptyCase(),
                this.game.getCurrentPawnO(),
                this.game.getCurrentPawnX(),
                this.game.getPlayer2PawnO(),
                this.game.getPlayer2PawnX());
    }
}

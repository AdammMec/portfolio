package g62588.dev3.oxono.model;

import java.util.ArrayList;
import java.util.Random;

public class BotMove {

    Position randomMove(ArrayList<Position> potentialMove){
        Random random = new Random();
        int move = random.nextInt(potentialMove.size());
        return potentialMove.get(move);
    }

    ArrayList<Position> totemChecker(Player current, Board board){
        ArrayList<Position> totemlist = new ArrayList<>();
        if (!current.isEmptyO()){
            totemlist.add(board.getPosTotem(Symbole.O));
        }
        if(!current.isEmptyX()){
            totemlist.add(board.getPosTotem(Symbole.X));
        }
        return totemlist;
    }

    void playbot(Player current, Board board, Game game){
        ArrayList<Position> totemlist = totemChecker(current, board);
        if(!totemlist.isEmpty()){
            Position totem = randomMove(totemlist);
            Symbole totemSymbole = board.getSymbole(totem);

            game.play(totem);
            game.play(randomMove(board.totemValidMove(totemSymbole)));
            game.play(randomMove(board.validInsertPawn(totemSymbole)));
        }
    }
}

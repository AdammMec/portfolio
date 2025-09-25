package g62588.dev3.oxono.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Describle a player of the game, with a list of pawn of symbole O and a list of pawn of symbole X, and a color
 */
public class Player {
    private Stack<Pawn> pawnO = new Stack<>();
    private Stack<Pawn> pawnX = new Stack<>();
    private Color color;

    /**
     * Initialisation of the player with 8 pawn of each symbole
     * @param color Color of the player, and so of the pawn
     */
    public Player(Color color, int size){
        this.color = color;
        int pawn = 0;
        if (size == 6){
            pawn = 8;
        }else{
            pawn = (size*size)/4-size/2;
        }
        for (int i = 0; i < pawn; i++) {
            pawnO.add(new Pawn(color, Symbole.O));
            pawnX.add(new Pawn(color, Symbole.X));
        }
    }

    /**
     * If both of the pawn list is empty
     * @return empty or not
     */
    public boolean isEmpty(){
        return pawnO.isEmpty() && pawnX.isEmpty();
    }

    /**
     * if the X stack is empty
     * @return empty or not
     */
    public boolean isEmptyX(){
        return pawnX.isEmpty();
    }

    /**
     * If the O stack is empty
     * @return empty or not
     */
    public boolean isEmptyO(){
        return pawnO.isEmpty();
    }

    /**
     * Use a pawn of the symbole
     * @param symbole symbole of the pawn that we have to use
     * @return the pawn that we have to use
     */
    Pawn usePawn(Symbole symbole){
        Pawn pawn;
        if (symbole == Symbole.O){
            pawn = pawnO.pop();
        }else{
            pawn = pawnX.pop();
        }
        return pawn;
    }

    /**
     * Add a pawn to the stack
     * @param pawn pawn we have to add
     */
    void addPawn(Pawn pawn){
        Symbole symbole = pawn.getSymbole();
        if (symbole == Symbole.O){
            pawnO.add(pawn);
        }else{
            pawnX.add(pawn);
        }
    }

    /**
     * get the size of the pawn O stack
     * @return pawn size
     */
    public int getPawnOsize(){
        return this.pawnO.size();
    }

    /**
     * get the size of the pawn X stack
     * @return pawn size
     */
    public int getPawnXsize(){
        return this.pawnX.size();
    }

    /**
     * get the color of the player
     * @return color
     */
    public Color getColor() {
        return color;
    }
}

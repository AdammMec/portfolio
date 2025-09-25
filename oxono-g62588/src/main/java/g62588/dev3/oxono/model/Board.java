package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.*;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Random;

/**
 * define the game board and the logic behind each movement
 */
public class Board {
    private Token[][] board;
    private Totem totemO;
    private Totem totemX;
    private int size;

    /**
     * Init the board at the good size, place the two totems
     * @param size size of the totem
     */
    public Board(int size){
        this.size = size;
        this.board = new Token[size][size];
        this.totemO = new Totem(Symbole.O);
        this.totemX = new Totem(Symbole.X);
        Random newrandom = new Random();
        Position[] totempos = new Position[2];
        // Génère les position aléatoire des totems
        if (size%2 == 0){
            totempos[0] = new Position(size/2, size/2);
            totempos[1] = new Position((size-1)/2, (size-1)/2);
        }else{
            totempos[0] = new Position(((size-1)/2) -1, ((size-1)/2) -1);
            totempos[1] = new Position(((size-1)/2) +1, ((size-1)/2) +1);
        }
        if (newrandom.nextBoolean()){
            this.setTotem(this.totemO, totempos[0]);
            this.setTotem(this.totemX, totempos[1]);
        }else {
            this.setTotem(this.totemX, totempos[0]);
            this.setTotem(this.totemO, totempos[1]);
        }
    }

    /**
     * Put the on the right case
     * @param totem Totem
     * @param pos new position of the totem
     */
    private void setTotem(Totem totem, Position pos){
        this.board[pos.y()][pos.x()] = totem; //Place le totem dans le board
        totem.setPos(pos);
    }

    /**
     * Move a totem fram a case to another
     * @param symboletotem Symbole of the first totem
     * @param lastPos position final of the totem
     * @return Symbole of the totem to prevent the symbole of the pawn
     */
    Symbole moveTotem(Symbole symboletotem, Position lastPos){
        Position initPos = getPosTotem(symboletotem);
        Symbole symbolepawn = null;
        //Bouge le totem, aussi bien dans le tableau que ses coordonées interne
        if (initPos.x() == this.totemX.getPos().x() && initPos.y() == this.totemX.getPos().y()){
            this.totemX.setPos(lastPos);
            symbolepawn = this.totemX.getSymbole();
        }
        if (initPos.x() == this.totemO.getPos().x() && initPos.y() == this.totemO.getPos().y()){
            this.totemO.setPos(lastPos);
            symbolepawn = this.totemO.getSymbole();
        }
        if (this.board[initPos.y()][initPos.x()] instanceof Totem){
            this.board[lastPos.y()][lastPos.x()] = this.board[initPos.y()][initPos.x()];
            this.board[initPos.y()][initPos.x()] = null;
        }
        return symbolepawn;
    }

    /**
     * Insert a pawn inside the board
     * @param pawn The pawn
     * @param pos positon where the pawn is going to be inserted
     * @return verification if the game is won
     */
    boolean insertPawn(Pawn pawn, Position pos){
        this.board[pos.y()][pos.x()] = pawn;
        return isWin(pos);
    }

    /**
     * Verify if the pos is not outside the board
     * @param pos position to be verified
     * @return verification of position
     */
    boolean isPosValid(Position pos){
        if (pos.x() < (this.board[0].length) && pos.x() >= 0){
            return pos.y() < (this.board.length) && pos.y() >= 0;
        }
        return false;
    }

    /**
     * Verify if the 4 case around the totem is occupied
     * @param pos Position to verified
     * @return verification of the position
     */
    boolean isSurrounded(Position pos) {
        return cardinalPosition(pos).isEmpty();
    }

    ArrayList<Position> cardinalPosition(Position pos){
        int[][] directions = {{1,0}, {0,1}, {-1,0}, {0,-1}};
        ArrayList<Position> validPos = new ArrayList<>();
        for (int[] dir : directions) {
            Position next = new Position(pos.x() + dir[0], pos.y() + dir[1]);
            if (isPosValid(next) && board[next.y()][next.x()] == null) {
                validPos.add(next);
            }
        }
        return validPos;
    }


    /**
     * Make a list of valid insert pawn around a totem.
     * @param symbole totem symbole
     * @return list of position of valid insert pawn
     */
    ArrayList<Position> validInsertPawn(Symbole symbole) {
        ArrayList<Position> validPos = new ArrayList<>();
        Totem totem = (symbole == Symbole.O) ? this.totemO : this.totemX;
        Position totemPos = totem.getPos();

        if (this.isSurrounded(totemPos)) {
            // Si le totem est enfermé, alors toutes les cases sont valides
            for (int y = 0; y < this.board.length; y++) {
                for (int x = 0; x < this.board[0].length; x++) {
                    if (this.board[y][x] == null) {
                        validPos.add(new Position(x, y));
                    }
                }
            }
        } else {
            // Verif les 4 directions autours
            validPos.addAll(cardinalPosition(totemPos));
        }

        return validPos;
    }

    /**
     * Make a list of valid position to move the totem
     *
     * We're moving in each directions by beginning with the totems positions, and verify if the case is free or not
     * if the totem is surrounded, we stop adding valid case when we reach the first one, else we just add all case free
     * in the directions and we stop when we reach a case out of the board or already used.
     *
     * @param symbole Symbole of the totem
     * @return The list of valid position of the totem move
     */
    ArrayList<Position> totemValidMove(Symbole symbole){
        Position pos = getPosTotem(symbole);
        ArrayList<Position> validPos = new ArrayList<>();
        //Permet de vérifier si on a atteint le max ou si on a atteint une case vide dépendant de issurounded
        boolean[] verifPos = {false, false, false, false};
        boolean isSurrounded = this.isSurrounded(pos);
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int i = 1; i < this.board[0].length; i++) {
            for (int dir = 0; dir < 4 ; dir++) { // To check all direactions (North south east weast)
                Position testPos = new Position(pos.x()+i*directions[dir][0], pos.y()+i*directions[dir][1]);
                if(isPosValid(testPos)){
                    if (this.board[testPos.y()][testPos.x()] == null){
                        if (isSurrounded && !verifPos[dir]){
                            //Si le totem est entourré et qu'on a atteint une case vide pour la première fois
                            verifPos[dir] = true; //Permet d'indiquer qu'on a atteint la case vide
                            validPos.add(testPos);
                        }else if (!verifPos[dir])
                            validPos.add(testPos);
                    }else if(!isSurrounded){
                        //On a atteint une case pleine alors que le totem est pas surrounded donc on arrête de prendre des pos
                        verifPos[dir] = true; //Permet d'indiquer qu'on a atteint une case pleine (dans le cas !surrounded)
                    }
                }
            }
        }
        return validPos;
    }

    /**
     * Verify if the last insert pawn is in a win condition
     * @param pos position of the last insert pawn
     * @return true if it's win
     */
    boolean isWin(Position pos){
        // verif à partir de 3 cases avant jusque 3 cases après si y'en a 4 dont fait partie notre case aligné
        // verif couleur et signe en même temps en faisant attention au totem
        // dès que le signe ou la couleur change, remettre compteur à 0
        // faire ça horizontalement et verticalement
        boolean win = false;
        int i = 0;
        while (i < 2 && !win) {
            int x = i%2;
            int y = (i+1)%2;
            int point = 0;
            int symbole = 0;
            int color = 0;
            Symbole symbole1 = null;
            Color color1 = null;
            while (point < 7 && !win) {
                int base = (-3+point);
                if (isPosValid(new Position(pos.x()-base*x, pos.y()-base*y)) && this.board[pos.y()-base*y][pos.x()-base*x] instanceof Pawn){
                    if (symbole1 == ((Pawn) this.board[pos.y()-base*y][pos.x()-base*x]).getSymbole()){
                        symbole += 1;
                    }else {
                        symbole = 1;
                        symbole1 = ((Pawn) this.board[pos.y()-base*y][pos.x()-base*x]).getSymbole();
                    }
                    if (color1 == ((Pawn) this.board[pos.y()-base*y][pos.x()-base*x]).getColor()){
                        color += 1;
                    }else{
                        color = 1;
                        color1 = ((Pawn) this.board[pos.y()-base*y][pos.x()-base*x]).getColor();
                    }
                }else{
                    symbole = 0;
                    color = 0;
                    symbole1 = null;
                    color1 = null;
                }
                if (symbole == 4 || color == 4){
                    win = true;
                }
                point++;
            }
            i++;
        }
        return win;
    }

    /**
     * verify if the case is a totem
     * @param pos position of the potential totem
     * @return verification of the totem
     */
    boolean isTotem(Position pos){
        return this.board[pos.y()][pos.x()] instanceof Totem;
    }

    /**
     * return symbole of the token
     * @param pos position of the token we want to know the symbole
     * @return Symbole
     */
    Symbole getSymbole(Position pos){
        if (totemX.getPos().x() == pos.x() && totemX.getPos().y() == pos.y()){
            return Symbole.X;
        }else{
            return Symbole.O;
        }
    }

    /**
     * Return the position of the totem
     * @param symbole symbole of the totem that we want the position
     * @return position of the totem
     */
    Position getPosTotem(Symbole symbole){
        if (symbole == totemO.getSymbole()){
            return totemO.getPos();
        }else{
            return totemX.getPos();
        }
    }

    /**
     * count the empty case on the board
     * @return the amount of empty case in the board
     */
    int emptyCase(){
        int emptycase = 0;
        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board.length; x++) {
                if (this.board[y][x] == null){
                    emptycase += 1;
                }
            }
        }
        return emptycase;
    }

    /**
     * @return the size of the board
     */
    int getSize() {
        return size;
    }
}
package g62588.dev3.oxono.console;

import g62588.dev3.oxono.model.*;
import g62588.dev3.oxono.model.event.*;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

public class OxonoConsole implements Observer {
    private ArrayList<Position> blackXList = new ArrayList<>();
    private ArrayList<Position> blackOList = new ArrayList<>();
    private ArrayList<Position> pinkXList = new ArrayList<>();
    private ArrayList<Position> pinkOList = new ArrayList<>();
    private int pawnOp1;
    private int pawnXp1;
    private int pawnOp2;
    private int pawnXp2;
    private int emptyCase;
    ArrayList<Position> validMove = new ArrayList<>();
    Color color;
    int size;
    private Position totemX;
    private Position totemO;

    public static void main(String[] args) {
        OxonoConsole console = new OxonoConsole();
        console.run();
    }

    public void run() {
        this.size = 6;
        Game game = new Game(size, false);
        game.registerObserver(this);
        game.startGame();

        String[] elements;
        while (true){
            final Scanner IN = new Scanner(System.in);
            String input = IN.nextLine();
            elements = input.split("\\s+");
            if (Objects.equals(elements[0], "undo")){
                game.undo(false);
            }else if (Objects.equals(elements[0], "redo")){
                game.redo(false);
            }else if (Objects.equals(elements[0], "surrender")){
                game.surrender();
            }else{
                try{
                    Position pos = new Position(Integer.parseInt(elements[0])-1, Integer.parseInt(elements[1])-1);
                    game.play(pos);
                }catch (Exception e){

                }
            }
        }
    }

    private boolean isInside(ArrayList<Position> move, int x, int y){
        boolean valid = false;
        for (int i = 0; i < move.size(); i++) {
            if (move.get(i).y() == y && move.get(i).x() == x){
                valid = true;
            }
        }
        return valid;
    }

    public void show(){
        String RESET = "\033[0m";
        String BLUE = "\033[34m";
        String BLACK = "\033[30m";
        String PINK = "\033[35m";
        String WHITE = "\033[32m";
        String BRIGHT_RED = "\033[91m";
        String BRIGHT_BLUE = "\033[94m";
        String ligne0 = " ";
        String ligne1 = "";
        for (int y = 0; y < this.size; y++) {
            String ligne2 = BRIGHT_BLUE + (y + 1)+ RESET + " |";
            for (int x = 0; x < this.size; x++) {
                if (y == 0){
                    ligne0 += "   "+ BRIGHT_RED + (x + 1) + RESET;
                    ligne1 += "-----";
                }
                if (isInside(validMove, x, y)){
                    ligne2 += WHITE + " V " + RESET;
                }else if (totemO.x() == x && totemO.y() == y) {
                    ligne2 += BLUE + " O " + RESET;
                }else if (totemX.x() == x && totemX.y() == y){
                    ligne2 += BLUE + " X " + RESET;
                } else if (isInside(blackXList, x, y)){
                    ligne2 += BLACK + " X " + RESET;
                }else if (isInside(blackOList, x, y)){
                    ligne2 += BLACK + " O " + RESET;
                }else if (isInside(pinkXList, x, y)){
                    ligne2 += PINK + " X " + RESET;
                }else if (isInside(pinkOList, x, y)){
                    ligne2 += PINK + " O " + RESET;
                }else {
                    ligne2 += "   ";
                }
                ligne2 += "|";
            }
            if(y==0){
                System.out.println(ligne1);
                System.out.println(ligne0);
            }
            System.out.println(ligne2);
        }
        System.out.println(ligne1);
        System.out.println("Case vide : " + this.emptyCase);
        System.out.println("Pion Restant : " + PINK + pawnOp1 + " O / " + pawnXp1 + " X " + RESET + " // " + BLACK + pawnOp2 + " O / " + pawnXp2 + " X " + RESET);
        System.out.println("Commande : ");
        System.out.println("• Jouer une position : " + BRIGHT_RED + "X" + RESET + " " + BRIGHT_BLUE + "Y" + RESET);
        System.out.println("• undo / redo");
        System.out.println("• surrender");
    }

    public void showEnd(Color color){
        if (color == Color.PINK){
            System.out.println("Le joueur Rose a gagné la partie");
        }else{
            System.out.println("Le joueur Noir a gagné la partie");
        }
    }

    private void pawnList(ArrayList<Position> pawnList, Position pos){
        if (pawnList.contains(pos)){
            pawnList.remove(pos);
        }else{
            pawnList.add(pos);
        }
    }

    @Override
    public void update(Event event) {
        if (event instanceof PawnEvent){
            if (((PawnEvent) event).getPawnColor() == Color.BLACK){
                if (((PawnEvent) event).getSymbole() == Symbole.X){
                    pawnList(blackXList, ((PawnEvent) event).getPos());
                }else {
                    pawnList(blackOList, ((PawnEvent) event).getPos());
                }
                this.pawnXp2 = ((PawnEvent) event).getPawnX();
                this.pawnOp2 = ((PawnEvent) event).getPawnO();
            }
            if (((PawnEvent) event).getPawnColor() == Color.PINK){
                if (((PawnEvent) event).getSymbole() == Symbole.X){
                    pawnList(pinkXList, ((PawnEvent) event).getPos());
                }else {
                    pawnList(pinkOList, ((PawnEvent) event).getPos());
                }
                this.pawnXp1 = ((PawnEvent) event).getPawnX();
                this.pawnOp1 = ((PawnEvent) event).getPawnO();
            }
            this.emptyCase = ((PawnEvent) event).getEmptyCase();
            this.validMove = ((PawnEvent) event).getValidInsertPawn();
            show();
        }
        if (event instanceof SelectTotemEvent){
            validMove = ((SelectTotemEvent) event).getValidTotemMove();
            show();
        }
        if (event instanceof StartGameEvent){
            this.totemX = ((StartGameEvent) event).getTotemX();
            this.totemO = ((StartGameEvent) event).getTotemO();

            this.emptyCase = ((StartGameEvent) event).getEmptyCase();
            this.pawnXp2 = ((StartGameEvent) event).getPawnBlackX();
            this.pawnOp2 = ((StartGameEvent) event).getPawnBlackO();
            this.pawnXp1 = ((StartGameEvent) event).getPawnPinkX();
            this.pawnOp1 = ((StartGameEvent) event).getPawnPinkO();
            show();
        }
        if (event instanceof TotemEvent){
            if (((TotemEvent) event).getSymbole() == Symbole.O){
                this.totemO = ((TotemEvent) event).getEnd();
            }if (((TotemEvent) event).getSymbole() == Symbole.X){
                this.totemX = ((TotemEvent) event).getEnd();
            }
            validMove = ((TotemEvent) event).getValidInsertPawn();
            show();
        }
        if (event instanceof EndGameEvent){
            this.color = ((EndGameEvent) event).getColor();
            if (((EndGameEvent) event).isWin()){
                showEnd(((EndGameEvent) event).getColor());
            }else{
                show();
            }
        }
    }
}
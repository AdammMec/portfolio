package g62588.dev3.oxono.model;

import g62588.dev3.oxono.model.commands.CommandManager;
import g62588.dev3.oxono.model.event.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Facade of the model. We use this class to make communication between the controller and the model
 */
public class Game implements Observable{
    private Player current;
    private Player player2;
    private Board board;
    private GameState gameState = GameState.MOVE;
    private List<Observer> observers = new ArrayList<>();
    private CommandManager commandManager = new CommandManager();
    private Symbole symbole = null;
    private boolean bot;
    private EventBuilder eventBuilder = new EventBuilder(this);
    private ArrayList<Position> validMove;
    private boolean win;
    private Color playerRound = Color.PINK;


    /**
     * Init the game and the object that we need to use the model
     *
     * @param size size of the game board
     * @param bot boolean to know if we play with bot or not
     */
    public Game(int size, boolean bot){
        this.bot = bot;
        this.board = new Board(size);
        this.current = new Player(Color.PINK, size);
        this.player2 = new Player(Color.BLACK, size);
    }

    /**
     * Launch the game with an event
     */
    public void startGame(){
        this.notifyObservers(eventBuilder.startGameEvent(board.getPosTotem(Symbole.O), board.getPosTotem(Symbole.X)));
    }

    /**
     * Create event for the selection of totem
     * @param pos selected totem position
     * @param symbole symbole of the selected totem
     */
    void selectTotemEvent(Position pos, Symbole symbole){
        this.editSymbole(symbole);
        if (this.symbole == null){
            this.validMove = new ArrayList<>();
        }else {
            this.validMove = this.board.totemValidMove(this.symbole);
        }
        SelectTotemEvent selectTotemEvent = eventBuilder.selectTotemEvent();
        this.notifyObservers(selectTotemEvent);
    }

    /**
     * Create command for the selection of totem. To use it, had it in "selectTotem" methods, before selectTotemEvent
     * @param pos position of the totem
     * @param selectedTotem symbole of the selectedtotem
     */
    void selectTotemCommand(Position pos, Symbole selectedTotem){
        if (symbole == null){
            SelectTotemCommand selectTotemCommand = new SelectTotemCommand(this, pos, null);
            commandManager.add(selectTotemCommand);
        }else {
            SelectTotemCommand selectTotemCommand = new SelectTotemCommand(this, pos, selectedTotem);
            commandManager.add(selectTotemCommand);
        }
    }

    /**
     * Confirmed the position of selected totem and send an event to share the valid position to move the totem
     * @param pos : position of the selected totem
     * @return boolean : Used to know if the pos was correct
     */
    boolean selectTotem(Position pos){
        if (this.gameState == GameState.MOVE){
            if (!current.isEmptyO() && board.getSymbole(pos) == Symbole.O || !current.isEmptyX() && board.getSymbole(pos) == Symbole.X){
                Symbole selectedTotem = board.getSymbole(pos);
                selectTotemEvent(pos, selectedTotem);
                return true;
            }
        }
        return false;
    }


    /**
     * Move totem in board and set the game to the right state
     * @param pos final position
     * @param symbole symbole of totem that we want to move
     * @param gameState state we want to put the game in
     */
    void moveTotemInBoard(Position pos, Symbole symbole, GameState gameState) {
        Position init = board.getPosTotem(symbole);
        this.board.moveTotem(this.symbole, pos);
        this.editGameState(gameState);
        this.editSymbole(symbole);
        if (gameState == GameState.MOVE){
            this.validMove = new ArrayList<>();
            TotemEvent totemEvent = eventBuilder.totemEvent(init, pos);
            this.notifyObservers(totemEvent);
        }else if (gameState == GameState.INSERT){
            this.validMove = this.board.validInsertPawn(symbole);
            TotemEvent totemEvent = eventBuilder.totemEvent(init, pos);
            this.notifyObservers(totemEvent);
        }
    }

    /**
     * Confirmed the new position of totem and send an event with the information to update the view
     * @param end Position where we want to move the totem
     * @return boolean : Used to know if the pos was correct
     */
    boolean moveTotem(Position end){
        if (!current.isEmptyO() && this.symbole == Symbole.O || !current.isEmptyX() && this.symbole == Symbole.X){
            if (this.gameState == GameState.MOVE && this.board.totemValidMove(this.symbole).contains(end)){
                MoveTotemCommand moveTotem = new MoveTotemCommand(this, this.symbole, board.getPosTotem(this.symbole), end);
                this.commandManager.add(moveTotem);
                moveTotemInBoard(end, this.symbole, GameState.INSERT);
                return true;
            }
        }
        return false;
    }

    /**
     * Create end Game event and set the right gameState
     * @param win true if win, false if not, null if equality
     * @param color color of the winner
     * @param gameState GameState that describe the end event
     */
    void endGameState(boolean win, Color color, GameState gameState){
        this.playerRound = color;
        this.win = win;
        this.notifyObservers(eventBuilder.endGameEvent());
        this.editGameState(gameState);
    }

    /**
     * Insert pawn in board and set the right game state
     * @param pos Position where we want to insert the pawn
     * @param pawn pawn we want to insert
     * @param color the color of the next player turn
     * @param symbole symbole of selected totem
     * @param gameState gamestate of the game that correspond to the insertion
     * @return a boolean that describe the endgame configuration
     */
    boolean insertPawnInBoard(Position pos, Pawn pawn, Color color, Symbole symbole, GameState gameState){
        this.win = this.board.insertPawn(pawn, pos);
        this.editSymbole(symbole);
        this.editGameState(gameState);
        this.playerRound = color;
        if (pawn == null){
            this.validMove = this.board.validInsertPawn(this.symbole);
        }else{
            this.validMove = new ArrayList<>();
        }
        PawnEvent pawnEvent = eventBuilder.pawnEvent(pos);
        notifyObservers(pawnEvent);

        return win;
    }

    /**
     * Confirmed position of pawn insertion and share an event.
     * @param pos Position of the pawn.
     * @return boolean : Used to know if the pos was correct
     */
    boolean insertPawn(Position pos){
        if (this.gameState == GameState.INSERT && this.board.validInsertPawn(this.symbole).contains(pos)){
            Pawn pawn = this.usePawn(symbole);
            InsertPawnCommand insertPawnCommand = new InsertPawnCommand(this, pos, pawn, this.player2.getColor());
            this.commandManager.add(insertPawnCommand);
            this.win = insertPawnInBoard(pos, pawn, pawn.getColor(), this.symbole, GameState.MOVE);
            switchPlayers();

            if(win){
                EndGameCommand endGameCommand = new EndGameCommand(this, win, pawn.getColor(),  GameState.FINISHED);
                this.commandManager.add(endGameCommand);
                endGameState(win, pawn.getColor(), GameState.FINISHED);
            } else if (current.isEmpty() && this.current.isEmpty()) {
                EndGameCommand endGameCommand = new EndGameCommand(this, true, null,  GameState.FINISHED);
                this.commandManager.add(endGameCommand);
                endGameState(true, null, GameState.FINISHED);
            }
            return true;
        }
        return false;
    }

    /**
     * The controller use this method to send a position that will be verified to know which use its has. When we know,
     * we call the right method to play. There is also the bot method if we play with.
     * @param pos position of the movement
     * @return boolean : Used to know if the pos was correct
     */
    public boolean play(Position pos){
        boolean valid = false;
        if (this.gameState == GameState.MOVE){
            if (this.board.isTotem(pos)){
                valid = selectTotem(pos);
            }else{
                valid = moveTotem(pos);
            }
        }else if (this.gameState == GameState.INSERT){
            valid = insertPawn(pos);
            if(bot && current.getColor() == Color.BLACK){
                this.playbot();
            }
        }
        return valid;
    }

    /**
     * Undo method, it will come back in the previous game play
     * @param bot boolean to know if there is a bot player
     */
    public void undo(boolean bot){
        if (this.gameState == GameState.FINISHED){
            commandManager.undo();
        }
        if (bot && this.current.getColor() == Color.PINK && this.gameState == GameState.MOVE){
            commandManager.undo();
            commandManager.undo();
        }
        commandManager.undo();
    }

    /**
     * Redo method, it will come back in the next game play
     * @param bot boolean to know if there is a bot player
     */
    public void redo(boolean bot){
        if (bot && this.current.getColor() == Color.PINK && this.gameState == GameState.INSERT){
            commandManager.redo();
            commandManager.redo();
        }
        commandManager.redo();
    }

    /**
     * Method to surrender the game, it will send an event to the view
     */
    public void surrender(){
        EndGameEvent endGameEvent = new EndGameEvent(true, player2.getColor());
        EndGameCommand endGameCommand = new EndGameCommand(this, true, player2.getColor(), this.gameState);
        this.commandManager.add(endGameCommand);
        this.gameState = GameState.FINISHED;
        notifyObservers(endGameEvent);
    }

    /**
     * The bot game play, it will generate a random move for each bot movement, selecttotem, movetotem and insertpawn and
     * use command to make an event to the view
     */
    private void playbot(){
        BotMove botMove = new BotMove();
        botMove.playbot(current, board, this);
    }

    /**
     * Register the observer, the object that have to be informed that a new event is created.
     * @param o The new observer
     */
    @Override
    public void registerObserver(Observer o) {
        this.observers.add(o);
    }

    /**
     * Remove an observer
     * @param o The deleted observer
     */
    @Override
    public void removeObserver(Observer o) {
        this.observers.remove(o);
    }

    /**
     * Send the event to the registered observer.
     * @param event The event that we send to the observer.
     */
    @Override
    public void notifyObservers(Event event) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(event);
        }
    }

    /**
     * switch players when the current is player2
     */
    void switchPlayers(){
        Player newCurrent = this.player2;
        this.setPlayer2(this.current);
        this.setCurrent(newCurrent);
    }

    /**
     * Method to use a pawn of the current player
     * @param symbole symbole of the pawn we want to use
     * @return the pawn that we use
     */
    Pawn usePawn(Symbole symbole){
        return this.current.usePawn(symbole);
    }

    /**
     * give back a pawn to the player
     * @param pos Position of pawn
     * @param pawn Pawn that we want to give back
     */
    void returnPawn(Position pos, Pawn pawn){
        this.player2.addPawn(pawn);
    }

    /**
     * Set the current used symbole
     * @param symbole symbole of the current used symbole
     */
    void editSymbole(Symbole symbole) {
        this.symbole = symbole;
    }

    /**
     * set the game state, the next move has to follow the new game state
     * @param gameState game state
     */
    private void editGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Current player
     * @param current
     */
    private void setCurrent(Player current) {
        this.current = current;
    }

    /**
     * Second player
     * @param player2
     */
    private void setPlayer2(Player player2) {
        this.player2 = player2;
    }


    int getBoardSize(){
        return this.board.getSize();
    }
    Color getCurrentColor(){
        return current.getColor();
    }
    int getEmptyCase(){
        return board.emptyCase();
    }
    int getCurrentPawnO(){
        return current.getPawnOsize();
    }
    int getCurrentPawnX(){
        return current.getPawnXsize();
    }
    int getPlayer2PawnO(){
        return player2.getPawnOsize();
    }
    int getPlayer2PawnX(){
        return player2.getPawnXsize();
    }
    boolean isWin() {
        return win;
    }
    Color getPlayerRound() {
        return playerRound;
    }
    ArrayList<Position> getValidMove() {
        return new ArrayList<>(validMove);
    }
    Symbole getSymbole() {
        return symbole;
    }

    Position getotemX(){
        return board.getPosTotem(Symbole.X);
    }

    Position getotemO(){
        return board.getPosTotem(Symbole.O);
    }
}
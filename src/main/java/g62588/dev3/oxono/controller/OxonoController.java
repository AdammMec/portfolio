package g62588.dev3.oxono.controller;

import g62588.dev3.oxono.model.Color;
import g62588.dev3.oxono.model.Game;
import g62588.dev3.oxono.model.Observer;
import g62588.dev3.oxono.model.Position;
import g62588.dev3.oxono.model.event.Event;
import g62588.dev3.oxono.model.event.StartGameEvent;
import g62588.dev3.oxono.view.MainView;
import g62588.dev3.oxono.view.MenuView;
import javafx.stage.Stage;

public class OxonoController implements Observer {
    private Game game;
    private Stage stage;
    private MainView mainView;
    boolean bot;
    public OxonoController(Stage stage){
        this.stage = stage;
        this.mainView = new MainView(this.stage, this);
    }

    public void menuAction(int size, boolean bot){
        this.game = new Game(size, bot);
        this.bot = bot;
        this.mainView.setBot(bot);
        game.registerObserver(this);
        game.startGame();
    }
    public void gridInteraction(Position position, Color color){
        this.game.play(position);
    }
    public void replay(){
        stage.close();
        this.stage = new Stage();
        this.mainView = new MainView(this.stage, this);
    }

    @Override
    public void update(Event event) {
        this.mainView.update(event);
    }

    public void surrender(){
        this.game.surrender();
    }
    public void undo(){
        this.game.undo(this.bot);
    }
    public void redo(){
        this.game.redo(this.bot);
    }
}

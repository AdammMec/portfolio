package g62588.dev3.oxono.view;

import g62588.dev3.oxono.controller.OxonoController;
import g62588.dev3.oxono.model.Color;
import g62588.dev3.oxono.model.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class MainView extends BorderPane {
    private OxonoController oxonoController;
    private MenuView menuView;
    private BoardView boardView;
    private Stage stage;
    private HandView handViewBlack;
    private HandView handViewPink;
    private Text player;
    private VBox emptyCase;
    private EndGameView endGameView = new EndGameView();
    private boolean bot;
    private Background background;


    public MainView(Stage stage, OxonoController oxonoController){
        this.stage = stage;
        this.oxonoController = oxonoController;
        this.menuView = new MenuView(stage, oxonoController);
        Image image = new Image(getClass().getResource("/images/background.png").toExternalForm());
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(100, 100, true, true, true, true));
        this.background = new Background(backgroundimage);
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public void update(Event event){
        if (event instanceof StartGameEvent){
            this.boardView = new BoardView((StartGameEvent) event, oxonoController, bot);
            this.boardView.setAlignment(Pos.CENTER);
            this.handViewPink = new HandView((StartGameEvent) event, Color.PINK);
            this.handViewBlack = new HandView((StartGameEvent) event, Color.BLACK);
            this.player = new javafx.scene.text.Text("Tour du joueur rose");
            this.player.setFill(javafx.scene.paint.Color.DEEPPINK);
            this.player.setFont(Font.font("System", FontWeight.BOLD, 20));

            javafx.scene.control.Button surrenderButton = new javafx.scene.control.Button("Abandon !");
            javafx.scene.control.Button undoButton = new javafx.scene.control.Button("↩");
            javafx.scene.control.Button redoButton = new javafx.scene.control.Button("↪");

            surrenderButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
            undoButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
            redoButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");


            VBox leftBox = new VBox(handViewPink);
            leftBox.setAlignment(Pos.CENTER);

            VBox rightBox = new VBox(handViewBlack);
            rightBox.setAlignment(Pos.CENTER);

            Label emptyTilesLabelname = new Label("Case vide : ");
            Label emptyTilesNumber = new Label(String.valueOf(((StartGameEvent) event).getEmptyCase()));
            emptyTilesLabelname.setTextFill(javafx.scene.paint.Color.WHITE);
            emptyTilesLabelname.setFont(Font.font("System", FontWeight.BOLD, 15));
            emptyTilesNumber.setTextFill(javafx.scene.paint.Color.WHITE);
            emptyTilesNumber.setFont(Font.font("System", FontWeight.BOLD, 15));

            this.emptyCase = new VBox();
            emptyCase.setAlignment(Pos.CENTER);
            this.emptyCase.getChildren().addAll(emptyTilesLabelname, emptyTilesNumber);
            VBox upperBox = new VBox(this.player, emptyCase);
            upperBox.setAlignment(Pos.CENTER);

            this.setLeft(leftBox);
            this.setRight(rightBox);

            this.setCenter(this.boardView);
            this.setTop(upperBox);



            this.setBackground(background);
            HBox button = new HBox(5);
            button.setPadding(new Insets(10, 20, 10, 20));
            button.setAlignment(Pos.CENTER);
            button.getChildren().addAll(surrenderButton, undoButton, redoButton);
            this.setBottom(button);
            this.setAlignment(button, Pos.CENTER);
            this.setMargin(button, new javafx.geometry.Insets(5));
            surrenderButton.setOnAction(e -> {
                oxonoController.surrender();
            });
            undoButton.setOnAction(e -> {
                oxonoController.undo();
            });
            redoButton.setOnAction(e -> {
                oxonoController.redo();
            });

            Scene scene = new Scene(this, 1000, 700);
            stage.setScene(scene);
            stage.show();
        }
        if (event instanceof SelectTotemEvent){
            this.boardView.update(event);
        }
        if (event instanceof TotemEvent){
            this.boardView.update(event);
        }
        if (event instanceof PawnEvent){
            this.boardView.update(event);
            if (((PawnEvent) event).getPawnColor() == Color.PINK){
                this.handViewPink.update((PawnEvent) event);
            }
            if (((PawnEvent) event).getPawnColor() == Color.BLACK){
                this.handViewBlack.update((PawnEvent) event);
            }
            if(((PawnEvent) event).getColor() == Color.PINK){
                this.player.setText("Tour du joueur noir");
                this.player.setFill(javafx.scene.paint.Color.BLACK);
            }else {
                this.player.setText("Tour du joueur rose");
                this.player.setFill(javafx.scene.paint.Color.DEEPPINK);
            }
            Label emptyTilesNumber = new Label(String.valueOf(((PawnEvent) event).getEmptyCase()));
            emptyTilesNumber.setTextFill(javafx.scene.paint.Color.WHITE);
            emptyTilesNumber.setFont(Font.font("System", FontWeight.BOLD, 15));
            this.emptyCase.getChildren().removeLast();
            this.emptyCase.getChildren().add(emptyTilesNumber);
        }if (event instanceof EndGameEvent){
            if(((EndGameEvent) event).isWin()){
                endGameView.setAlignment(Pos.CENTER);
                VBox win = new VBox(this.endGameView);
                win.setAlignment(Pos.CENTER);
                this.setTop(win);
                this.endGameView.update((EndGameEvent) event, oxonoController);
            }else {
                VBox upperBox = new VBox(this.player, emptyCase);
                upperBox.setAlignment(Pos.CENTER);
                this.setTop(upperBox);
            }
        }
    }

    public void setOxonoController(OxonoController oxonoController) {
        this.oxonoController = oxonoController;
    }
}

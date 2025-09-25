package g62588.dev3.oxono.view;

import g62588.dev3.oxono.controller.OxonoController;
import g62588.dev3.oxono.model.Color;
import g62588.dev3.oxono.model.event.EndGameEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class EndGameView extends VBox {

    private Text winner;

    public void update(EndGameEvent event, OxonoController oxonoController){
        if (event.isWin() && event.getColor() != null){
            if (event.getColor() == Color.BLACK){
                this.winner = new Text("Le gagnant est NOIR");
                this.winner.setFill(javafx.scene.paint.Color.BLACK);
            }
            if (event.getColor() == Color.PINK){
                this.winner = new Text("Le gagnant est ROSE");
                this.winner.setFill(javafx.scene.paint.Color.DEEPPINK);
            }
        }else if (event.isWin() && event.getColor() == null)
            this.winner = new Text("C'est une égalité ...");
        javafx.scene.control.Button actionButton = new javafx.scene.control.Button("RE-jouer !");
        actionButton.setOnAction(e -> {
            oxonoController.replay();
        });
        actionButton.setStyle("-fx-background-color: #7AA95C ; -fx-text-fill: white; -fx-font-weight: bold;");

        this.winner.setFont(Font.font("System", FontWeight.BOLD, 20));
        this.getChildren().clear();
        this.getChildren().addAll(winner, actionButton);
    }
}
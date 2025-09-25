package g62588.dev3.oxono.view;

import g62588.dev3.oxono.controller.OxonoController;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MenuView extends VBox {
    private OxonoController oxonoController;
    public MenuView(Stage stage, OxonoController oxonoController){
        this.oxonoController = oxonoController;

        //BackGround
        Image image = new Image(getClass().getResource("/images/background.png").toExternalForm());
        BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(100, 100, true, true, true, true));
        Background background = new Background(backgroundimage);
        this.setBackground(background);

        Text text1 = new Text("Taille du plateau de jeu :");
        text1.setFill(Color.WHITE);
        text1.setFont(Font.font("System", FontWeight.BOLD, 13));
        javafx.scene.control.TextField size = new javafx.scene.control.TextField();
        size.setPrefColumnCount(5);
        Text text = new Text("Jouer contre un bot :");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("System", FontWeight.BOLD, 13));
        CheckBox checkBox = new CheckBox();
        javafx.scene.control.Button actionButton = new javafx.scene.control.Button("Jouer !");
        actionButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        this.setMargin(text1, new javafx.geometry.Insets(5));
        this.setMargin(size, new javafx.geometry.Insets(5));
        this.setMargin(text, new javafx.geometry.Insets(5));
        this.setMargin(checkBox, new javafx.geometry.Insets(5));
        this.setMargin(actionButton, new javafx.geometry.Insets(5));
        this.getChildren().add(text1);
        this.getChildren().add(size);
        this.getChildren().add(text);
        this.getChildren().add(checkBox);
        this.getChildren().add(actionButton);
        actionButton.setOnAction(e -> {
            oxonoController.menuAction(Integer.parseInt(size.getText()), checkBox.isSelected());
        });
        Scene scene = new Scene(this, 300, 160);
        stage.setScene(scene);
        stage.show();
    }
}
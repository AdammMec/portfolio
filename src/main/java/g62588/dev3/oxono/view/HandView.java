package g62588.dev3.oxono.view;

import g62588.dev3.oxono.model.Color;
import g62588.dev3.oxono.model.event.PawnEvent;
import g62588.dev3.oxono.model.event.StartGameEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HandView extends VBox {
    private final Label remainingXLabel;
    private final Label remainingOLabel;
    private final Color color;


    HandView(StartGameEvent event, Color color){
        this.color = color;
        HBox oPawn = new HBox(10);
        oPawn.setAlignment(Pos.CENTER);
        HBox xPawn = new HBox(10);
        xPawn.setAlignment(Pos.CENTER);
        if (this.color == Color.PINK){
            this.remainingOLabel = new Label(String.valueOf(event.getPawnPinkO()));
            this.remainingXLabel = new Label(String.valueOf(event.getPawnPinkX()));
            Image image = new Image(getClass().getResource("/images/PinkO.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            Image image2 = new Image(getClass().getResource("/images/PinkX.png").toExternalForm());
            ImageView imageView2 = new ImageView(image2);
            oPawn.getChildren().addAll(imageView, remainingOLabel);
            xPawn.getChildren().addAll(imageView2, remainingXLabel);
            this.getChildren().addAll(oPawn, xPawn);
        }else{
            this.remainingOLabel = new Label(String.valueOf(event.getPawnBlackO()));
            this.remainingXLabel = new Label(String.valueOf(event.getPawnBlackX()));
            Image image = new Image(getClass().getResource("/images/BlackO.png").toExternalForm());
            ImageView imageView = new ImageView(image);
            Image image2 = new Image(getClass().getResource("/images/BlackX.png").toExternalForm());
            ImageView imageView2 = new ImageView(image2);
            oPawn.getChildren().addAll(remainingOLabel, imageView);
            xPawn.getChildren().addAll(remainingXLabel, imageView2);
            this.getChildren().addAll(oPawn, xPawn);
        }
        this.setMargin(oPawn, new javafx.geometry.Insets(30));
        this.setMargin(xPawn, new javafx.geometry.Insets(30));
        remainingOLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        remainingOLabel.setFont(Font.font("System", FontWeight.BOLD , 15));
        remainingXLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        remainingXLabel.setFont(Font.font("System", FontWeight.BOLD , 15));
    }
    public void update(PawnEvent event){
        this.remainingOLabel.setText(String.valueOf(event.getPawnO()));
        this.remainingXLabel.setText(String.valueOf(event.getPawnX()));
    }
}

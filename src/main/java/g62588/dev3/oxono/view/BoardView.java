package g62588.dev3.oxono.view;

import g62588.dev3.oxono.controller.OxonoController;
import g62588.dev3.oxono.model.*;
import g62588.dev3.oxono.model.event.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class BoardView extends GridPane {
    private ArrayList<Position> validMove = new ArrayList<>();
    private int cellSize;
    private int boardSize;
    private StackPane[][] boardView;
    private g62588.dev3.oxono.model.Color color;
    boolean bot;
    ImageView totemOView;
    ImageView totemXView;
    Image blackO;
    Image blackX;
    Image pinkO;
    Image pinkX;

    BoardView(StartGameEvent startGameEvent, OxonoController oxonoController, boolean bot){
        this.bot = bot;
        this.boardSize = startGameEvent.getSize();
        this.boardView = new StackPane[this.boardSize][this.boardSize];
        this.cellSize = 480/this.boardSize;
        loadImage();
        for (int y = 0; y < this.boardSize; y++) {
            for (int x = 0; x < this.boardSize; x++) {
                StackPane cell = new StackPane();
                Rectangle rectangle = new Rectangle(cellSize,cellSize);
                rectangle.setFill(Color.valueOf("65338a"));
                rectangle.setStroke(Color.valueOf("8d238e"));
                rectangle.setStrokeWidth(1);
                cell.getChildren().add(rectangle);
                this.add(cell, x, y);
                this.boardView[y][x] = cell;

                int finalX = x;
                int finalY = y;
                this.color = startGameEvent.getColor();
                cell.setOnMouseClicked(e -> {
                    oxonoController.gridInteraction(new Position(finalX, finalY), this.color);
                });
            }
        }

        this.setupImage(this.totemOView, this.boardView[startGameEvent.getTotemO().y()][startGameEvent.getTotemO().x()]);
        this.setupImage(this.totemXView, this.boardView[startGameEvent.getTotemX().y()][startGameEvent.getTotemX().x()]);

    }

    private void loadImage(){
        Image totemO = new Image(getClass().getResource("/images/TotemO.png").toExternalForm());
        this.totemOView = new ImageView(totemO);
        Image totemX = new Image(getClass().getResource("/images/TotemX.png").toExternalForm());
        this.totemXView = new ImageView(totemX);
        this.blackO = new Image(getClass().getResource("/images/BlackO.png").toExternalForm());
        this.blackX = new Image(getClass().getResource("/images/BlackX.png").toExternalForm());
        this.pinkO = new Image(getClass().getResource("/images/PinkO.png").toExternalForm());
        this.pinkX = new Image(getClass().getResource("/images/PinkX.png").toExternalForm());
    }

    public void setupImage(ImageView imageView, StackPane cell){
        imageView.setFitHeight(((double) cellSize /100)*80);
        imageView.setFitWidth(((double) cellSize /100)*80);
        if (cell.getChildren().getLast() instanceof ImageView){
            cell.getChildren().removeLast();
        }else{
            cell.getChildren().add(imageView);
        }
    }
    public void validMoveChange(ArrayList<Position> validMove){
        for (int i = 0; i < this.validMove.size(); i++) {
            StackPane cell = this.boardView[this.validMove.get(i).y()][this.validMove.get(i).x()];
            Rectangle rectangle = (Rectangle) cell.getChildren().get(0);
            rectangle.setFill(Color.valueOf("65338a"));
        }
        this.validMove = validMove;
        for (int i = 0; i < this.validMove.size(); i++) {
            StackPane cell = this.boardView[this.validMove.get(i).y()][this.validMove.get(i).x()];
            Rectangle rectangle = (Rectangle) cell.getChildren().get(0);
            rectangle.setFill(Color.valueOf("aa56e8"));
        }
    }

    public void update(Event event){
        if (event instanceof SelectTotemEvent){
            this.validMoveChange(((SelectTotemEvent) event).getValidTotemMove());
        }if (event instanceof TotemEvent){
            this.validMoveChange(((TotemEvent) event).getValidInsertPawn());
            Position posinit = ((TotemEvent) event).getInit();
            Position posEnd = ((TotemEvent) event).getEnd();
            StackPane cell = this.boardView[posinit.y()][posinit.x()];
            cell.getChildren().removeLast();

            StackPane cellEnd = this.boardView[posEnd.y()][posEnd.x()];
            if (((TotemEvent) event).getSymbole() == Symbole.X){
                this.setupImage(this.totemXView, cellEnd);
            }else{
                this.setupImage(this.totemOView, cellEnd);
            }

        }if (event instanceof PawnEvent){
            this.validMoveChange(((PawnEvent) event).getValidInsertPawn());
            Position pos = ((PawnEvent) event).getPos();
            StackPane cell = this.boardView[pos.y()][pos.x()];
            Rectangle rectangle = (Rectangle) cell.getChildren().getFirst();
            if (((PawnEvent) event).getPawnColor() == g62588.dev3.oxono.model.Color.BLACK){
                this.color = g62588.dev3.oxono.model.Color.PINK;
                if (((PawnEvent) event).getSymbole() == Symbole.O){
                    this.setupImage(new ImageView(blackO), cell);
                }else{
                    this.setupImage(new ImageView(blackX), cell);
                }
            }else {
                this.color = g62588.dev3.oxono.model.Color.BLACK;
                if (((PawnEvent) event).getSymbole() == Symbole.O){
                    this.setupImage(new ImageView(pinkO), cell);
                }else{
                    this.setupImage(new ImageView(pinkX), cell);
                }
            }
        }
    }
}
package g62588.dev3.oxono.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import g62588.dev3.oxono.model.event.StartGameEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;


public class GameTest {
    Game game;
    @BeforeEach
    void setUp(){
        this.game = new Game(6, true);
    }

    @Test
    void startGame_shouldNotifyObservers() {
        AtomicBoolean notified = new AtomicBoolean(false);

        game.registerObserver(event -> {
            assertTrue(event instanceof StartGameEvent);
            notified.set(true);
        });

        game.startGame();

        assertTrue("L'événement StartGameEvent n'a pas été envoyé", notified.get());
    }

    @Test
    void play_shouldSelectTotemIfPositionIsTotem() {

        boolean result = game.play(new Position(3, 3));

        assertTrue("La sélection du totem O aurait dû réussir", result);
    }

    @Test
    void play_shouldNotAllowInvalidTotemSelection() {
        Position empty = new Position(0, 0); // case vide

        boolean result = game.play(empty);

        assertFalse("La sélection sur une case vide ne doit pas être valide", result);
    }

    @Test
    void testSelectTotem(){
        Assertions.assertTrue(game.selectTotem(new Position(3, 3)));
        Assertions.assertTrue(game.selectTotem(new Position(2, 2)));
    }

    @Test
    void testMoveTotem(){
        game.selectTotem(new Position(3, 3));
        Assertions.assertTrue(game.moveTotem(new Position(3, 1)));
        Assertions.assertFalse(game.moveTotem(new Position(1, 2)));
        Assertions.assertFalse(game.moveTotem(new Position(11, 1)));
    }

    @Test
    void testinsertPawn1(){
        game.selectTotem(new Position(3, 3));
        game.play(new Position(4, 3));
        Assertions.assertTrue(game.insertPawn(new Position(3,3)));

    }
    @Test
    void testinsertPawn2(){
        game.selectTotem(new Position(3, 3));
        game.play(new Position(4, 3));
        Assertions.assertTrue(game.insertPawn(new Position(5,3)));

    }
    @Test
    void testinsertPawn3(){
        game.selectTotem(new Position(3, 3));
        game.play(new Position(4, 3));
        Assertions.assertFalse(game.insertPawn(new Position(3,4)));
    }
    @Test
    void testinsertPawn4(){
        game.selectTotem(new Position(3, 3));
        game.play(new Position(4, 3));
        Assertions.assertFalse(game.insertPawn(new Position(10,3)));
    }
    @Test
    void testinsertPawn5(){
        game.selectTotem(new Position(3, 3));
        game.play(new Position(4, 3));
        Assertions.assertFalse(game.insertPawn(new Position(2,3)));
    }

    @Test
    void testundoTotem(){
        game.selectTotem(new Position(2, 2));
        game.play(new Position(3, 2));
        game.undo(true);
        game.play(new Position(1, 2));
        Assertions.assertTrue(game.play(new Position(1,1)));
    }

    @Test
    void testundoInsert(){
        game.play(new Position(2, 2));
        game.play(new Position(3, 2));
        game.play(new Position(3,1));
        game.undo(true);
        Assertions.assertTrue(game.insertPawn(new Position(2,2)));
    }

    @Test
    void testredo(){
        game.play(new Position(2, 2));
        game.play(new Position(3, 2));
        game.play(new Position(2,2));
        //Le bot joue
        game.undo(true);
        game.undo(true);
        game.redo(true);
        Assertions.assertTrue(game.play(new Position(2,2)));
    }

    @Test
    void testPlay(){
        Assertions.assertTrue(game.play(new Position(3,3)));
    }

    @Test
    void testSurrender(){
        game.play(new Position(2,2));
        game.surrender();
    }
}

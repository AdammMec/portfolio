package g62588.dev3.oxono.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;
    private Board board2;
    private Board board3;
    @BeforeEach
    void setUp(){
        this.board = new Board(6);
        this.board2 = new Board(10);
        this.board3 = new Board(12);
    }
    @Test
    void testBoardInitialization() {
        assertEquals(6, board.getSize());
        assertEquals(34, board.emptyCase());
        assertEquals(10, board2.getSize());
        assertEquals(98, board2.emptyCase());
        assertEquals(12, board3.getSize());
        assertEquals(142, board3.emptyCase());
    }

    @Test
    void testInserPawn(){
        // InsertPawn return si le jeu est gagnat
        Position pos = new Position(2, 2);
        Position pos1 = new Position(4, 2);
        Position pos2 = new Position(1, 7);
        Pawn pawn = new Pawn(Color.BLACK, Symbole.O);
        Assertions.assertFalse(board.insertPawn(pawn, pos));
        Assertions.assertFalse(board.insertPawn(pawn, pos1));
        Assertions.assertFalse(board2.insertPawn(pawn, pos2));
        Assertions.assertFalse(board2.insertPawn(pawn, pos1));
        Assertions.assertFalse(board3.insertPawn(pawn, pos2));
    }
    @Test
    void testMoveTotem(){
        Position pos = new Position(2, 2);
        Position pos1 = new Position(4, 2);
        Position pos2 = new Position(1, 7);
        Symbole symbole = board.moveTotem(Symbole.O, pos);
        assertEquals(pos, board.getPosTotem(symbole));
        assertEquals(pos, board.getPosTotem(symbole));
        Symbole symbole2 = board.moveTotem(Symbole.O, pos1);
        assertEquals(pos1, board.getPosTotem(symbole2));
        assertEquals(pos1, board.getPosTotem(symbole2));
    }

    @Test
    void testIsSurrounded(){
        Position pos1 = new Position(4, 2);
        assertFalse(board.isSurrounded(pos1));
    }

    @Test
    void testIsWin(){
        board.insertPawn(new Pawn(Color.BLACK, Symbole.O), new Position(1, 1));
        board.insertPawn(new Pawn(Color.BLACK, Symbole.O), new Position(1, 2));
        board.insertPawn(new Pawn(Color.BLACK, Symbole.O), new Position(1, 3));
        boolean win = board.insertPawn(new Pawn(Color.BLACK, Symbole.O), new Position(1, 4));
        assertTrue(win);
    }

}

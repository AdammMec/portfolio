package g62588.dev3.oxono.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import g62588.dev3.oxono.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    Player player;
    @BeforeEach
    void setUp(){
        this.player = new Player(Color.BLACK, 6);
    }
    @Test
    void testInit(){
        assertEquals(player.getPawnOsize(), 8);
        assertEquals(player.getPawnXsize(), 8);
    }
    @Test
    void testAddPawn(){
        Pawn pawn = new Pawn(Color.BLACK, Symbole.O);
        player.addPawn(pawn);
        assertEquals(player.getPawnOsize(), 9);
    }
    @Test
    void testUsePawn(){
        player.usePawn(Symbole.O);
        assertEquals(player.getPawnOsize(), 7);
    }
}

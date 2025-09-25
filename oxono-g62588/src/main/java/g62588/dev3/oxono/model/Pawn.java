package g62588.dev3.oxono.model;

/**
 * Describe a Pawn of the board, with symbole and color
 */
public class Pawn implements Token{
    private Symbole symbole;
    private Color color;

    /**
     * Init the pawn with the color and the symbol give
     * @param color
     * @param symbole
     */
    public Pawn(Color color, Symbole symbole){
        this.color = color;
        this.symbole = symbole;
    }

    /**
     * get symbole
     * @return
     */
    public Symbole getSymbole() {
        return symbole;
    }

    /**
     * get color
     * @return
     */
    public Color getColor() {
        return color;
    }
}

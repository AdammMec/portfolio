package g62588.dev3.oxono.model;

/**
 * Describe a totem game
 */
public class Totem implements Token{
    private Symbole symbole;
    private Position pos;

    /**
     * initaliston of the totem with the symbole
     * @param symbole
     */
    public Totem(Symbole symbole){
        this.symbole = symbole;
    }

    /**
     * get the symbole
     * @return symbole of the totem
     */
    public Symbole getSymbole() {
        return symbole;
    }

    /**
     * get the postion of the totem by creating another object
     * @return position of the totem
     */
    public Position getPos() {
        return new Position(pos.x(), pos.y());
    }

    /**
     * set the position of the totem
     * @param pos new position of the totem
     */
    void setPos(Position pos) {
        this.pos = pos;
    }
}

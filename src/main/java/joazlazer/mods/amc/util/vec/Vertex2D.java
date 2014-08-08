package joazlazer.mods.amc.util.vec;

/**
 * Utility class for representing gui vertices.
 */
public class Vertex2D {
    public int x;
    public int y;

    public Vertex2D() {
        x = 0;
        y = 0;
    }

    public Vertex2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vertex2D(Vertex2D v) {
        x = v.getX();
        y = v.getY();
    }

    public Vertex2D copy() {
        return new Vertex2D(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vertex2D setX(int newX) {
        this.x = newX;
        return this;
    }

    public Vertex2D setY(int newY) {
        this.y = newY;
        return this;
    }

    public Vertex2D translate(int xDelta, int yDelta) {
        this.x += xDelta;
        this.y += yDelta;
        return this;
    }
}

public class Position {
    public int x;
    public int y;
    public boolean navigable;
    private String customChar;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Position(int x, int y, boolean nav) {
        this.x = x;
        this.y = y;
        this.navigable = nav;
        this.customChar = null;
    }

    public void floodPoint() {
        this.navigable = false;
    }

    public boolean isNavigable() {
        return navigable;
    }

    public boolean equals(Position pos) {
        return (pos.x == this.x && pos.y == this.y && pos.navigable == this.navigable);
    }

    public Position clone() {
        return new Position(x, y, navigable);
    }

    public String getRepresentationChar() {
        if (customChar == null)
            return (this.isNavigable() ? ANSI_GREEN + " . " : ANSI_BLUE + " ~ ") + ANSI_RESET;

        return customChar;
    }

    public void setRepresentationChar(String rep) {
        this.customChar = rep;
    }

    public void changePositionToDirection(Direction horizontal, Direction vertical, int times) {
        if (horizontal == Direction.RIGHT)
            this.x += times;

        else if (horizontal == Direction.LEFT)
            this.x -= times;
        // else do nothing

        if (vertical == Direction.DOWN)
            this.y += times;

        else if (vertical == Direction.UP)
            this.y -= times;
        // else do nothing
    }

    public void changePositionToDirection(Direction[] direction) {
        changePositionToDirection(direction, 1);
    }

    public void changePositionToDirection(Direction[] direction, int times) {
        changePositionToDirection(direction[0], direction[1], times);
    }

    public static int distanceBetweenPoints(Position point1, Position point2) {
        int a = Math.abs(point1.x - point2.x);
        int b = Math.abs(point1.y - point2.y);

        if (a != 0 && b != 0)
            return (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        else if (a == 0)
            return b;
        return a;
    }
}
abstract class Robot {

    protected Terrain terrain;
    protected Position position;
    protected Position previousPosition;
    protected Position movementDestination;
    private Position finalDestination;

    private final Direction[][] directions = {
            { Direction.RIGHT, Direction.DOWN },
            { Direction.LEFT, Direction.UP },
            { Direction.RIGHT, Direction.UP },
            { Direction.LEFT, Direction.DOWN },
            { Direction.RIGHT, null },
            { Direction.LEFT, null},
            { null, Direction.UP },
            { null, Direction.DOWN },
    };

    public Robot(Position current, Position dest) {
        position = current;
        finalDestination = dest;
        movementDestination = finalDestination.clone();
    }

    public Position getPosition() {
        return position;
    }

    public Position getFinalDestination() {
        return finalDestination;
    }

    abstract boolean canStandAt(Position pos);

    public void defineCurrentTarget() {
        movementDestination = finalDestination.clone();
    }

    public void stepAhead() {
        previousPosition = position.clone();
        position = getShortestStep();
    }

    protected Position  getShortestStep() {
        Position shortest = position.clone();

        if (shortest.x == movementDestination.x) {
            if (shortest.y > movementDestination.y) shortest.y--;
            else shortest.y++;

            return shortest;

        } else if (shortest.y == movementDestination.y) {
            if (shortest.x > movementDestination.x) shortest.x--;
            else shortest.x++;

            return shortest;
        }

        Position nextPos = position.clone();
        int lastPath = -1;
        for (Direction[] dir: directions) {
            nextPos.changePositionToDirection(dir);
            if ((shortest == null || lastPath == -1 || Position.distanceBetweenPoints(movementDestination, nextPos) < lastPath)) {
                shortest = nextPos.clone();
                lastPath = Position.distanceBetweenPoints(movementDestination, nextPos);
            }
        }

        shortest.navigable = terrain.isNavigable(shortest);

        return shortest;
    }

    public Direction[] setDirectionToPosition(Position point) {
        Direction[] direction = new Direction[2];
        if (position.x > point.x)
            direction[0] = Direction.LEFT;
        else if (position.x < point.x)
            direction[0] = Direction.RIGHT;

        if (position.y > point.y)
            direction[1] = Direction.UP;
        else if (position.y < point.y)
            direction[1] = Direction.DOWN;

        return direction;
    }

}

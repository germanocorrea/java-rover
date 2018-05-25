abstract class Robot {

    protected Position position;
    protected Position movementDestination;
    protected Position finalDestination;
    protected Terrain terrain;

    protected final Direction[][] directions = {
            { Direction.RIGHT, Direction.DOWN},
            { Direction.LEFT, Direction.UP},
            { Direction.RIGHT, Direction.UP},
            { Direction.LEFT, Direction.DOWN},
            { Direction.RIGHT, null },
            { Direction.LEFT, null},
            { null, Direction.UP},
            { null, Direction.DOWN},
    };

    public Robot(Position current, Position dest) {
        position = current;
        finalDestination = dest;
        movementDestination = dest;
    }

    abstract boolean canStandAt(Position pos);

    public void defineCurrentTarget(Position dest) {
        movementDestination = dest;
    }

    public void defineCurrentTarget() {
        movementDestination = finalDestination;
    }

    public void defineRouteToDestiny() {
        Position newTmpPos = !isDestinyAligned() ? defineReadjustmentPosition() : this.finalDestination;
    }

    public Position defineReadjustmentPosition() {
        if (position.x == movementDestination.x || position.y == movementDestination.y)
            return position;

        Position[] destinations = getMovementToReadjustment();

        return getShortestMovementPosition(destinations);
    }

    public Position getShortestMovementPosition(Position destinations[]) {
        Position shortest = null;
        int lastPathSize = 0;
        for (Position point: destinations) {
            if (point == null) continue;

            int a = Math.abs(position.x - point.x);
            int b = Math.abs(position.y - point.y);

            int pathSize;
            if (a != 0 && b != 0)
                pathSize = (int) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            else if (a == 0)
                pathSize = b;
            else
                pathSize = a;

            if (shortest == null || pathSize > lastPathSize)
                lastPathSize = pathSize;
            shortest = point;
        }

        return shortest;
    }

    private Position[] getMovementToReadjustment() {
        Position[] positions = new Position[8];

        for (int i = 0; i < 8; i++) {
            positions[i] = new Position(position.x, position.y, position.isNavigable());
            do {
                positions[i].parseDirection(directions[i][0], directions[i][1]);
                if (!canStandAt(positions[i])) {
                    positions[i] = null;
                    break;
                }
            } while (!isDestinyAligned(positions[i]));
        }

        return positions;
    }

    public boolean isDestinyAligned(Position position) {
        return  (position.x == this.position.x)
                || (position.y == this.position.y)
                || (position.x - this.position.y == this.position.x - position.y);
//                || Math.abs((position.x - this.position.x) / (position.y - this.position.y)) == 1;
    }

    public boolean isDestinyAligned() {
        return isDestinyAligned(this.movementDestination);
    }

}

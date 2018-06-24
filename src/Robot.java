abstract class Robot {

    protected Terrain terrain;
    private Position position;
    private Position movementDestination;
    private Position finalDestination;

    private Direction[] currentMovementDirection = {null, null};

    private final int SAFE_DISTANCE = 3;
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
        movementDestination = dest;
    }

    public Position getPosition() {
        return position;
    }

    public Position getFinalDestination() {
        return finalDestination;
    }

    abstract boolean canStandAt(Position pos);

    public void defineCurrentTarget(Position dest) {
        movementDestination = dest;
    }

    public void defineCurrentTarget() {
        movementDestination = finalDestination;
    }

    public void stepAhead() {
        if (movementDestination.equals(finalDestination)) {
            movementDestination = isDestinyAligned() ? movementDestination : getReadjustmentPosition();
        } else if (movementDestination.equals(position)) {
            defineCurrentTarget();
            stepAhead();
            return;
        }

        setCurrentMovementDirection();
        Position nextStep = position.clone();
        nextStep.changePositionToDirection(currentMovementDirection);
        if (canStandAt(nextStep) && safeDistanceTo(nextStep)) {
            position = nextStep;
        } else {
            // rota de escape

        }
    }

    public void setCurrentMovementDirection() {
        if (position.x > movementDestination.x)
            currentMovementDirection[0] = Direction.LEFT;
        else if (position.x < movementDestination.x)
            currentMovementDirection[0] = Direction.RIGHT;

        if (position.y > movementDestination.y)
            currentMovementDirection[1] = Direction.UP;
        else if (position.y < movementDestination.y)
            currentMovementDirection[1] = Direction.DOWN;
    }

    public boolean safeDistanceTo(Position pos) {
        if (pos.x == this.position.x)
            return (Math.abs(this.position.x - pos.x) < SAFE_DISTANCE);
        else if (pos.y == this.position.y)
            return (Math.abs(this.position.y - pos.y) < SAFE_DISTANCE);


        return true;
    }

    public Position getReadjustmentPosition() {
        if (position.x == movementDestination.x || position.y == movementDestination.y)
            return position;

        Position[] destinations = getMovementToReadjustment();

        return getBestMovementPosition(destinations);
    }

    public Position getBestMovementPosition(Position destinations[]) {
        Position shortest = null;
        int lastPathSize = 0;
        for (Position point: destinations) {
            if (point == null) continue;

            int pathSize = Position.distanceBetweenPoints(finalDestination, point) + Position.distanceBetweenPoints(position, point);

            if (shortest == null || pathSize < lastPathSize) {
                lastPathSize = pathSize;
                shortest = point;
            }
        }

        return shortest;
    }

    public Position[] getMovementToReadjustment() {
        Position[] positions = new Position[8];

        for (int i = 0; i < 8; i++) {
            positions[i] = position.clone();
            do {
                positions[i].changePositionToDirection(directions[i]);
                if (!canStandAt(positions[i])) {
                    positions[i] = null;
                    break;
                }
            } while (!isDestinyAligned(positions[i]));
        }

        return positions;
    }

    public boolean isDestinyAligned(Position position) {
        return  (position.x == this.finalDestination.x)
                || (position.y == this.finalDestination.y)
                || (position.x - this.finalDestination.y == this.finalDestination.x - position.y);
    }

    public boolean isDestinyAligned() {
        return isDestinyAligned(this.movementDestination);
    }

}

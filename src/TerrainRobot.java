public class TerrainRobot extends Robot {

    private Direction[] lastDirection = {null, null};

    public TerrainRobot(Position current, Position dest) {
        super(current, dest);
    }

    public boolean canStandAt(Position pos) {
        return terrain.isNavigable(pos);
    }

    public void stepAhead() {
        if (position.equals(movementDestination) && !movementDestination.equals(getFinalDestination()))
            movementDestination = getFinalDestination().clone();

        checkForObstacles();
        super.stepAhead();
    }

    public void checkForObstacles() {
        Position shortestNext = getShortestStep();
        if (canStandAt(shortestNext))
            return;

        setEvasiveManeuver(shortestNext);
    }

    public void setEvasiveManeuver(Position shortestPosition) {
        Direction[] direction = setDirectionToPosition(shortestPosition);
        Position nextPos = position.clone();

        if (direction[0] != null && isRouteBlockedAt(direction[0]) && direction[1] != null) {
            direction[1] = null;

        } else if (direction[0] != null && !isRouteBlockedAt(direction[0]) && direction[1] != null) {
            direction[0] = null;

            // FIXME: daqui pra baixo era pra tentar pegar uma manobra de evasão quando o destino ta alinhado horizontal ou verticalmente mas não funciona
        } else {
            if (lastDirection[0] != null || lastDirection[1] != null) {
                direction = lastDirection;
                if (direction[0] != null && !isRouteBlockedAt(direction[0])) {
                    direction[0] = null;
                    direction[1] = Direction.UP;

                } else if (direction[1] != null && !isRouteBlockedAt(direction[1])) {
                    direction[1] = null;
                    direction[0] = Direction.RIGHT;
                }
            }

        }

        nextPos.changePositionToDirection(direction);
        lastDirection = direction;
        movementDestination = nextPos;
    }

    public boolean isRouteBlockedAt(Direction direction) {
        Position testPos = position.clone();
        Direction[] directions = new Direction[2];
        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            directions[0] = direction;
        } else {
            directions[1] = direction;
        }

        testPos.changePositionToDirection(directions);
        return canStandAt(testPos);
    }

}

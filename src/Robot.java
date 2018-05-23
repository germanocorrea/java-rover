abstract class Robot {

    protected Position position;
    protected Position movementDestination;
    protected Position finalDestination;
    protected Terrain terrain;

    protected final String[] directions = {
            "++", // direita-baixo
            "--", // esquerda-cima
            "+-", // direita-cima
            "-+", // esquerda-baixo

            "+ ", // direita
            "- ", // esquerda
            " -", // acima
            " +", // baixo
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
        Position newTmpPos = !isDestinyPerpendicular() ? defineReadjustmentPosition() : this.finalDestination;
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
                parseDirectionCode(positions[i], directions[i]);
                if (!canStandAt(positions[i])) {
                    positions[i] = null;
                    break;
                }
            } while (!isDestinyPerpendicular(positions[i]));
        }

        return positions;
    }

    protected void parseDirectionCode(Position position, String direction) {
        if (direction.charAt(0) == '+'){
            position.increaseAxis('x');
        } else if (direction.charAt(0) == '-') {
            position.decreaseAxis('x');
        } // else do nothing

        if (direction.charAt(1) == '+'){
            position.increaseAxis('x');
        } else if (direction.charAt(1) == '-') {
            position.decreaseAxis('x');
        } // else do nothing
    }

    public boolean isDestinyPerpendicular(Position position) {
        return  (position.x == this.position.x)
                || (position.y == this.position.y)
                || Math.abs((position.x - this.position.x) / (position.y - this.position.y)) == 1;
    }

    public boolean isDestinyPerpendicular() {
        return isDestinyPerpendicular(this.movementDestination);
    }

}

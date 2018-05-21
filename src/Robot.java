abstract class Robot {

    protected Position position;
    protected Position movmentDestination;
    protected Position finalDestination;
    protected Terrain terreno;

    protected final String[] direcoes = {
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
        movmentDestination = null;
        finalDestination = dest;
    }

    public void defineAlvoAtual(Position dest) {
        movmentDestination = dest;
    }

    public void defineAlvoAtual() {
        movmentDestination = finalDestination;
    }

    public void defineRotaToDest() {
        if (!isDestinyPerpendicular()) {
            Position newTmpPos = defineReadjustmentPosition();
        }
    }

    /**
    * Define a posição em que o robô deve reajustar o curso para estar perpendicular ao destino
     * @see Position
     * @return Position posição que é perpendicular ao destino
    * */
    private Position defineReadjustmentPosition() {
        if (position.x == movmentDestination.x || position.y == movmentDestination.y)
            return position;

        Position[] destinos = getMovementToReadjustment();

        return getShortestMovementPosition(destinos);
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

    // abstract Position getShortestMovementPositionAvailable(Position[] destinations);
    abstract boolean canStandAt(Position pos);

    /**
     * Retorna um array com as posições para reajustar a posição em cada "eixo" (horizontal, vertical ou diagonal)
     * @see Position
     * @return objeto Position, representando a posição de menor movimento para reajuste
     * */
    private Position[] getMovementToReadjustment() {
        Position[] positions = new Position[8];

        for (int i = 0; i < 8; i++) {
            positions[i] = new Position(position.x, position.y);
            do {
                parseDirectionCode(positions[i], direcoes[i]);
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

    /**
     * Verifica se o destino está perpendicular de forma horizontal, vertical ou oblíqua, à posição informada
     * @see Position
     * @param position objeto da classe Position
     * @return informa se está ou não perpendicular
     * */
    private boolean isDestinyPerpendicular(Position position) {
        return  (position.x == movmentDestination.x)
                || (position.y == movmentDestination.y)
                || Math.abs((position.x - movmentDestination.x) / (position.y - movmentDestination.y)) == 1;
    }

    /**
     * Verifica se o destino está perpendicular de forma horizontal, vertical ou diagonal, à posição atual do robô
     * @see Position
     * @return informa se está ou não perpendicular
     * */
    private boolean isDestinyPerpendicular() {
        return isDestinyPerpendicular(this.position);
    }

}

public class TerrainRobot extends Robot {

    public TerrainRobot(Position current, Position dest) {
        super(current, dest);
    }

    public boolean canStandAt(Position pos) {
        return terreno.isGround(pos);
    }

}

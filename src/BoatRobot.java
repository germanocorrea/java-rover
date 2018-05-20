public class BoatRobot extends Robot {

    public BoatRobot(Position current, Position dest) {
        super(current, dest);
    }

    public boolean canStandAt(Position pos) {
        return terreno.isPointAvailable(pos);
    }

}

import java.util.Scanner;

public class JavaRover {

    public static void main(String[] args) {
        TerrainRobot robot = new TerrainRobot(new Position(39, 25, true), new Position(45, 20, true));
        Terrain surface = new Terrain(50);

        createTheLand(surface);
        robot.terrain = surface;

        surface.addEntity(robot.getPosition(), Position.ANSI_PURPLE + getRobotEmoji() + Position.ANSI_RESET);
        surface.addEntity(robot.getFinalDestination(), Position.ANSI_RED + 'x' + Position.ANSI_RESET);

        boolean notDone = true;
        robot.defineCurrentTarget();
        while (notDone) {
            robot.stepAhead();
            if (robot.getPosition().equals(robot.getFinalDestination()))
                notDone = false;
            else surface.addEntity(robot.getPosition(), Position.ANSI_YELLOW + '+' + Position.ANSI_RESET);
        }

        surface.printTerrainRepresentation();
    }

    public static String getRobotEmoji() {
        StringBuffer sb = new StringBuffer();
        sb.append(Character.toChars(129302));
        return sb.toString();
    }

    public static void createTheLand(Terrain surface) {
        surface.floodSquareRegion(new int[] {6, 3}, new int[] {18, 8});
        surface.floodSquareRegion(new int[] {19, 12}, new int[] {34, 18});
        surface.floodSquareRegion(new int[] {5, 26}, new int[] {25, 33});
        surface.floodSquareRegion(new int[] {39, 2}, new int[] {43, 19});
    }
}
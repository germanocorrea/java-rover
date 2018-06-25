public class JavaRover {

    public static void main(String[] args) {
        Terrain surface = new Terrain(50);
        createTheLand(surface);

        TerrainRobot[] robots = {
                new TerrainRobot(new Position(30, 40, true), new Position(48, 48, true)),
                new TerrainRobot(new Position(36, 6, true), new Position(36, 40, true)),
                new TerrainRobot(new Position(10, 40, true), new Position(30, 22, true))
        };

        for (TerrainRobot robot: robots) {
            makeRobotWalk(robot, surface);
        }

        System.out.println("ROBÔ TERRESTRE:\n");
        surface.printTerrainRepresentation();
        System.out.println("\n=================================================================\n");
        System.out.println("ROBÔ BARCO:\n");

        surface = new Terrain(50);
        createTheLand(surface);

        BoatRobot[] boatRobots = {
                new BoatRobot(new Position(30, 40, true), new Position(48, 48, true)),
                new BoatRobot(new Position(36, 6, true), new Position(36, 40, true)),
                new BoatRobot(new Position(10, 40, true), new Position(30, 22, true))
        };

        for (BoatRobot robot: boatRobots ) {
            makeRobotWalk(robot, surface);
        }

        surface.printTerrainRepresentation();
    }

    public static void makeRobotWalk(Robot robot, Terrain surface) {
        robot.terrain = surface;

        surface.addEntity(robot.getPosition(), Position.ANSI_PURPLE + " " + getRobotEmoji() + " " + Position.ANSI_RESET);
        surface.addEntity(robot.getFinalDestination(), Position.ANSI_RED + " x " + Position.ANSI_RESET);

        boolean notDone = true;
        robot.defineCurrentTarget();
        while (notDone) {
            robot.stepAhead();
            if (robot.getPosition().equals(robot.getFinalDestination()))
                notDone = false;
            else surface.addEntity(robot.getPosition(), Position.ANSI_YELLOW + " + " + Position.ANSI_RESET);
        }

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
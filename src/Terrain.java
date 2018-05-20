public class Terrain {
    private char[][] surface;
    private final char WATER = '~';
    private final char GROUND = '#';

    public Terrain(int resolution) {
        surface = new char[resolution][resolution];
    }

    public Terrain(int resolutionX, int resolutionY) {
        surface = new char[resolutionY][resolutionX];
    }

    public boolean isGround(Position pos) {
        return isPointAvailable(pos) && surface[pos.x][pos.y] != WATER;
    }

    public boolean isPointAvailable(Position pos) {
        return surface.length >= pos.x && surface[pos.x].length >= pos.y;
    }
}

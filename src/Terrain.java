public class Terrain {
    private Position[][] surface;

    public Terrain(int resolution) {
        surface = new Position[resolution][resolution];
        fillWithGround(resolution, resolution);
    }

    public Terrain(int resolutionX, int resolutionY) {
        surface = new Position[resolutionY][resolutionX];
        fillWithGround(resolutionX, resolutionY);
    }

    public Terrain(Position[][] surface) {
        this.surface = surface;
        fillWithGround(surface.length, surface[0].length);
    }

    public void printTerrainRepresentation() {
        String surfaceLine;
        for (Position[] line: surface) {
            surfaceLine = "";
            for (Position point: line)
                surfaceLine += point.getRepresentationChar();
            System.out.println(surfaceLine);
        }
    }

    private void fillWithGround(int resolutionX, int resolutionY) {
        for (int i = 0; i < resolutionY; i++)
            for (int w = 0; w < resolutionX; w++) {
                surface[i][w] = new Position(i,w,true);
            }
    }

    public boolean isNavigable(Position pos) {
        return isPointAvailable(pos) &&
                surface.length -1 >= pos.x &&
                surface[0].length -1 >= pos.y &&
                surface[pos.x][pos.y].isNavigable();
    }

    public boolean isPointAvailable(Position pos) {
        return surface.length -1 >= pos.x && surface[0].length - 1 >= pos.y;
    }

    public void floodSquareRegion(int[] startPoint, int[]endPoint) {
        for (int i = startPoint[1]; i < endPoint[1]; i++)
            for (int w = startPoint[0]; w < endPoint[0]; w++) {
                surface[i][w].floodPoint();
            }
    }

    public void addEntity(Position entityPos, String representation) {
        surface[entityPos.x][entityPos.y].setRepresentationChar(representation);
    }

    public void addEntity(Position entityPos, char representation) {
        addEntity(entityPos, representation + "");
    }
}

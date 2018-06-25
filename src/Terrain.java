public class Terrain {
    private Position[][] surface;

    public Terrain(int resolution) {
        surface = new Position[resolution][resolution];
        fillWithGround(resolution, resolution);
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
                surface.length -1 >= pos.y &&
                surface[0].length -1 >= pos.x &&
                surface[pos.y][pos.x].isNavigable();
    }

    public boolean isPointAvailable(Position pos) {
        return surface.length -1 >= pos.y && surface[0].length - 1 >= pos.x;
    }

    public void floodSquareRegion(int[] startPoint, int[]endPoint) {
        for (int i = startPoint[1]; i < endPoint[1]; i++)
            for (int w = startPoint[0]; w < endPoint[0]; w++) {
                surface[i][w].floodPoint();
            }
    }

    public void addEntity(Position entityPos, String representation) {
        surface[entityPos.y][entityPos.x].setRepresentationChar(representation);
    }

}

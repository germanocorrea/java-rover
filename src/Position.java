public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void decreaseAxis(char axis) {
        switch (axis) {
            case 'x' :
                this.x--;
                break;
            case 'y' :
                this.x--;
                break;
            case 'z':
                this.x--;
                this.y--;
        }
    }
    public void increaseAxis(char axis) {
        switch (axis) {
            case 'x' :
                this.x++;
                break;
            case 'y' :
                this.x++;
                break;
            case 'z':
                this.x++;
                this.y++;
        }
    }
}
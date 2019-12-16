// Alex Beamer
// December 16, 2019
// Galleta Games and Software

public class Player {

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    private Point location;
    private MazeGame game;

    public Player(Point startPoint, MazeGame g) {
        location = startPoint.duplicate();
        game = g;
    }

    public boolean move(int direction) {
        if(!isValidMove(direction)) {
            return false;
        }
        if(direction == UP) {
            location.setY(location.getY()-1);
        } else if(direction == RIGHT) {
            location.setX(location.getX()+1);
        } else if(direction == DOWN) {
            location.setY(location.getY()+1);
        } else if(direction == LEFT) {
            location.setX(location.getX()-1);
        } else {
            return false;
        }
        return true;
    }

    private boolean isValidMove(int direction) {
        return !((direction == UP && (location.getY() == 0 || game.getMazeLayout().isWallAt(new Point(location.getX(), location.getY()-1), MazeLayout.DOWN))) || (direction == RIGHT && (location.getX() == game.getMazeLayout().getSize()[0]-1 || game.getMazeLayout().isWallAt(location, MazeLayout.RIGHT))) || (direction == DOWN && (location.getY() == game.getMazeLayout().getSize()[1]-1 || game.getMazeLayout().isWallAt(location, MazeLayout.DOWN))) || (direction == LEFT && (location.getX() == 0 || game.getMazeLayout().isWallAt(new Point(location.getX()-1, location.getY()), MazeLayout.RIGHT))));
    }

    public Point getLocation() {
        return location;
    }

}

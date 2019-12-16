// Alex Beamer
// December 11, 2019
// Galleta Games and Software

import javafx.scene.input.KeyEvent;

public class MazeGame {

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    private MazeLayout mazeLayout;
    private Player player;
    private int moves = 0;
    private boolean won = false;

    public MazeGame(int[] size) {
        mazeLayout = new MazeLayout(size);
        player = new Player(mazeLayout.getStartPosition(), this);
    }

    public MazeGame(MazeLayout ml) {
        mazeLayout = ml;
        player = new Player(mazeLayout.getStartPosition(), this);
    }

    public void keyPressed(KeyEvent event) {
        if(won) {
            return;
        }
        String key = event.getCode().getName();
        if(key.equals("W")) {
            movePlayer(UP);
        } else if(key.equals("D")) {
            movePlayer(RIGHT);
        } else if(key.equals("S")) {
            movePlayer(DOWN);
        } else if(key.equals("A")) {
            movePlayer(LEFT);
        }
        checkWin();
    }

    private void checkWin() {
        if(player.getLocation().equals(mazeLayout.getEndPosition())) {
            won = true;
        }
    }

    private void movePlayer(int direction) {
        if(player.move(direction)) {
            moves++;
        }
    }

    public MazeLayout getMazeLayout() {
        return mazeLayout;
    }

    public void setMazeLayout(MazeLayout mazeLayout) {
        this.mazeLayout = mazeLayout;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getWon() {
        return won;
    }

    public int getMoves() {
        return moves;
    }

}

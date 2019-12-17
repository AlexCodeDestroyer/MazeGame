// Alex Beamer
// December 11, 2019
// Galleta Games and Software

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MazeGameApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private GraphicsContext gc;
    private MazeGame mazeGame;
    private int[] size = {60, 60};
    private int[] screenSize = {900, 900};
    private boolean debug = false, cheat = false;

    @Override
    public void start(Stage stage) throws Exception {
        mazeGame = new MazeGame(size);
        stage.setTitle("Maze Game");
        Group group = new Group();
        Canvas canvas = new Canvas(screenSize[0], screenSize[1]);
        canvas.setOnKeyPressed(this::keyPressed);
        gc = canvas.getGraphicsContext2D();
        group.getChildren().add(canvas);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        canvas.requestFocus();
        stage.show();
        draw(gc);
    }

    public void keyPressed(KeyEvent event) {
        //System.out.println(event.getCode().getChar());
        if(event.getCode().getName().equals("Slash")) {
            debug = !debug;
        } else if(event.getCode().getName().equals("R")) {
            mazeGame = new MazeGame(size);
        } else if(event.getCode().getChar().equals(".")) {
            cheat = !cheat;
        }
        mazeGame.keyPressed(event);
        draw(gc);
    }

    private void draw(GraphicsContext g) {
        g.setFill(Color.color(0.1, 0.1, 0.1));
        g.fillRect(0, 0, screenSize[0], screenSize[1]);
        g.setStroke(Color.color(0.6, 0.6, 0.6));
        g.setLineWidth(2);
        for (int i = 0; i < mazeGame.getMazeLayout().getWalls().length; i++) {
            Wall wall = mazeGame.getMazeLayout().getWalls()[i];
            g.strokeLine(wall.getPoint1().getX() * (screenSize[0]/size[0])+((wall.getPoint1().getX() == wall.getPoint2().getX())?screenSize[0]/size[0]:0), wall.getPoint1().getY() * (screenSize[1]/size[1])+((wall.getPoint1().getY() == wall.getPoint2().getY())?screenSize[1]/size[1]:0), wall.getPoint2().getX() * (screenSize[0]/size[0])+((wall.getPoint1().getX() == wall.getPoint2().getX())?screenSize[0]/size[0]:0), wall.getPoint2().getY() * (screenSize[1]/size[1])+((wall.getPoint1().getY() == wall.getPoint2().getY())?screenSize[1]/size[1]:0));
        }
        g.setFont(Font.font(10));
        if(debug) {
            for (int r = 0; r < size[1]; r++) {
                for (int c = 0; c < size[0]; c++) {
                    g.setFill(Color.WHITE);
                    if (mazeGame.getMazeLayout().finaltrues[r][c]) {
                        g.setFill(Color.GREEN);
                    }
                    g.fillText(mazeGame.getMazeLayout().finalDists[r][c] + "", c * (screenSize[0] / size[0]) + (screenSize[0] / size[0]) / 4, r * (screenSize[1] / size[1]) + (screenSize[1] / size[1]) / 4);
                }
            }
        }
        if(cheat) {
            //System.out.println(mazeGame.getMazeLayout().finalPath);
            g.setLineWidth(4);
            g.setFill(Color.GREEN);
            g.setStroke(Color.GREEN);
            for(int i = 1; i < mazeGame.getMazeLayout().finalPath.length; i++) {
                Point p = mazeGame.getMazeLayout().finalPath[i-1];
                Point p1 = mazeGame.getMazeLayout().finalPath[i];
                g.strokeLine(p.getX() * (screenSize[0] / size[0]) + (screenSize[0] / size[0]) / 2, p.getY() * (screenSize[1] / size[1]) + (screenSize[1] / size[1]) / 2, p1.getX() * (screenSize[0] / size[0]) + (screenSize[0] / size[0]) / 2, p1.getY() * (screenSize[1] / size[1]) + (screenSize[1] / size[1]) / 2);
                //System.out.println(p.getX() + " " + p.getY() + " - " + p1.getX() + " " + p1.getY());
            }
            //System.out.println(mazeGame.getMazeLayout().finalPath[mazeGame.getMazeLayout().finalPath.length-1]);
        }
        g.setFill(Color.BLUE);
        g.fillOval(mazeGame.getMazeLayout().getStartPosition().getX() * (screenSize[0]/size[0])+(screenSize[0]/size[0])/4, mazeGame.getMazeLayout().getStartPosition().getY() * (screenSize[1]/size[1])+(screenSize[1]/size[1])/4, (screenSize[0]/size[0])/2, (screenSize[1]/size[1])/2);
        g.setFill(Color.GREENYELLOW);
        g.fillOval(mazeGame.getMazeLayout().getEndPosition().getX() * (screenSize[0]/size[0])+(screenSize[0]/size[0])/4, mazeGame.getMazeLayout().getEndPosition().getY() * (screenSize[1]/size[1])+(screenSize[1]/size[1])/4, (screenSize[0]/size[0])/2, (screenSize[1]/size[1])/2);
        g.setFill(Color.RED);
        g.fillOval(mazeGame.getPlayer().getLocation().getX() * (screenSize[0]/size[0])+(screenSize[0]/size[0])/4, mazeGame.getPlayer().getLocation().getY() * (screenSize[1]/size[1])+(screenSize[1]/size[1])/4, (screenSize[0]/size[0])/2, (screenSize[1]/size[1])/2);


        if(mazeGame.getWon()) {
            g.setFill(Color.YELLOW);
            g.setFont(Font.font(50));
            g.fillText("You won in " + mazeGame.getMoves() + " moves!\nThe minimum was " + mazeGame.getMazeLayout().getMinMoves() + ".", 200, 200);
        }
    }
}

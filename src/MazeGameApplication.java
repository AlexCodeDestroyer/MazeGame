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
    private int[] size = {49, 49};
    private int[] screenSize = {950, 950};
    private boolean debug = false;

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
        if(event.getCode().getName().equals("Slash")) {
            debug = !debug;
        } else if(event.getCode().getName().equals("R")) {
            mazeGame = new MazeGame(size);
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
        g.setFill(Color.BLUE);
        g.fillOval(mazeGame.getMazeLayout().getStartPosition().getX() * (screenSize[0]/size[0])+(screenSize[0]/size[0])/4, mazeGame.getMazeLayout().getStartPosition().getY() * (screenSize[1]/size[1])+(screenSize[1]/size[1])/4, (screenSize[0]/size[0])/2, (screenSize[1]/size[1])/2);
        g.setFill(Color.GREENYELLOW);
        g.fillOval(mazeGame.getMazeLayout().getEndPosition().getX() * (screenSize[0]/size[0])+(screenSize[0]/size[0])/4, mazeGame.getMazeLayout().getEndPosition().getY() * (screenSize[1]/size[1])+(screenSize[1]/size[1])/4, (screenSize[0]/size[0])/2, (screenSize[1]/size[1])/2);
        g.setFill(Color.RED);
        g.fillOval(mazeGame.getPlayer().getLocation().getX() * (screenSize[0]/size[0])+(screenSize[0]/size[0])/4, mazeGame.getPlayer().getLocation().getY() * (screenSize[1]/size[1])+(screenSize[1]/size[1])/4, (screenSize[0]/size[0])/2, (screenSize[1]/size[1])/2);
        g.setFont(Font.font(10));
        if(debug) {
            for (int r = 0; r < size[1]; r++) {
                for (int c = 0; c < size[0]; c++) {
                    g.setFill(Color.WHITE);
                    if (mazeGame.getMazeLayout().finaltrues[r][c]) {
                        g.setFill(Color.WHEAT);
                    }
                    g.fillText(mazeGame.getMazeLayout().finalDists[r][c] + "", c * (screenSize[0] / size[0]) + (screenSize[0] / size[0]) / 4, r * (screenSize[1] / size[1]) + (screenSize[1] / size[1]) / 4);
                }
            }
        }
        if(mazeGame.getWon()) {
            g.setFill(Color.YELLOW);
            g.setFont(Font.font(50));
            g.fillText("You won in " + mazeGame.getMoves() + " moves!\nThe minimum was " + mazeGame.getMazeLayout().getMinMoves() + ".", 200, 200);
        }
    }
}

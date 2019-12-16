// Alex Beamer
// December 11, 2019
// Galleta Games and Software

import java.util.ArrayList;

public class MazeLayout {

    public static int RIGHT = 0, DOWN = 1, LEFT = 2, UP = 3;

    private Point startPosition;
    private Point endPosition;
    private Wall[] walls;
    private int[] size;


    public MazeLayout(int[] size) {
        this.size = size;
        randInit();
    }

    public MazeLayout(Point[][] walls, Point start, Point end, int[] size) {
        this.size = size;
        Wall[] tempWalls = new Wall[walls.length];
        for (int i = 0; i < walls.length; i++) {
            tempWalls[i] = new Wall(walls[i][0], walls[i][1]);
        }
        init(tempWalls, start, end);
    }

    private void init(Wall[] walls, Point start, Point end) {
        startPosition = start;
        endPosition = end;
        this.walls = walls;
    }

    private void randInit() {
        genRandLayout();
    }

    private void genRandLayout() {
        class MyWall {
            static final int RIGHT = 0, DOWN = 1;
            int r, c;
            int id;

            MyWall(int y, int x, int side) {
                r = y;
                c = x;
                id = side;
            }

            @Override
            public boolean equals(Object o) {
                return ((MyWall)o).r == r && ((MyWall)o).c == c && ((MyWall)o).id == id;
            }

        }
        class Cell {
            boolean visited;
            int r, c;
            MyWall[] walls;

            Cell(int y, int x) {
                visited = false;
                r = y;
                c = x;
                if (y == size[1] - 1 && x == size[0] - 1) {
                    return;
                }
                walls = new MyWall[((y < size[1] - 1 && x < size[0] - 1) ? 2 : 1)];
                if (y < size[1] - 1 && x < size[0] - 1) {
                    walls[0] = new MyWall(r, c, MyWall.RIGHT);
                    walls[1] = new MyWall(r, c, MyWall.DOWN);
                } else if (y == size[1] - 1) {
                    walls[0] = new MyWall(r, c, MyWall.RIGHT);
                } else if (x == size[0] - 1) {
                    walls[0] = new MyWall(r, c, MyWall.DOWN);
                }
            }
        }
        class NotSet<E> {
            ArrayList<E> data = new ArrayList<>();

            void add(E item) {
                if (!contains(item)) {
                    data.add(item);
                }
            }

            boolean contains(E item) {
                return data.contains(item);
            }

            E get(int index) {
                return data.get(index);
            }

            boolean isEmpty() {
                return data.isEmpty();
            }

            void remove(E item) {
                data.remove(item);
            }

            int size() {
                return data.size();
            }

            E getRandom() {
                return data.get((int) (Math.random() * size()));
            }
        }
        class Util {
            void addWalls(NotSet<MyWall> walls, Cell cell, String exclude) {
                if (cell.r != size[1] - 1 && !exclude.equals("DOWN")) {
                    walls.add(new MyWall(cell.r, cell.c, MyWall.DOWN));
                }
                if (cell.r != 0 && !exclude.equals("UP")) {
                    walls.add(new MyWall(cell.r - 1, cell.c, MyWall.DOWN));
                }
                if (cell.c != 0 && !exclude.equals("LEFT")) {
                    walls.add(new MyWall(cell.r, cell.c - 1, MyWall.RIGHT));
                }
                if (cell.c != size[0] - 1 && !exclude.equals("RIGHT")) {
                    walls.add(new MyWall(cell.r, cell.c, MyWall.RIGHT));
                }
            }
        }
        Cell[][] map = new Cell[size[1]][size[0]];
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                map[r][c] = new Cell(r, c);
            }
        }
        Cell start = map[(int) (Math.random() * size[1])][(int) (Math.random() * size[0])];
        startPosition = new Point(start.c, start.r);
        start.visited = true;
        NotSet<MyWall> walls = new NotSet<>();
        NotSet<MyWall> finalWalls = new NotSet<>();
        NotSet<MyWall> doneWalls = new NotSet<>();
        Util util = new Util();
        util.addWalls(walls, start, "none");
        while (!walls.isEmpty()) {
            MyWall myWall = walls.getRandom();
            if (myWall.id == MyWall.DOWN) {
                if (!map[myWall.r][myWall.c].visited) {
                    map[myWall.r][myWall.c].visited = true;
                    util.addWalls(walls, map[myWall.r][myWall.c], "DOWN");
                } else if (!map[myWall.r + 1][myWall.c].visited) {
                    map[myWall.r + 1][myWall.c].visited = true;
                    util.addWalls(walls, map[myWall.r + 1][myWall.c], "UP");
                } else {
                    finalWalls.add(myWall);
                }
                walls.remove(myWall);
            } else if (myWall.id == MyWall.RIGHT) {
                if (!map[myWall.r][myWall.c].visited) {
                    map[myWall.r][myWall.c].visited = true;
                    util.addWalls(walls, map[myWall.r][myWall.c], "RIGHT");
                } else if (!map[myWall.r][myWall.c + 1].visited) {
                    map[myWall.r][myWall.c + 1].visited = true;
                    util.addWalls(walls, map[myWall.r][myWall.c + 1], "LEFT");
                } else {
                    finalWalls.add(myWall);
                }
                walls.remove(myWall);
                doneWalls.add(myWall);
            }
            for (int i = walls.size() - 1; i >= 0; i--) {
                if (finalWalls.contains(walls.get(i)) || doneWalls.contains(walls.get(i))) {
                    walls.remove(walls.get(i));
                }
            }
            if(walls.isEmpty()) {
                endPosition = new Point(myWall.c, myWall.r);
            }
        }

        this.walls = new Wall[finalWalls.size()];
        for (int i = 0; i < finalWalls.size(); i++) {
            Point p1 = null;
            Point p2 = null;
            if (finalWalls.get(i).id == MyWall.RIGHT) {
                MyWall myWall = finalWalls.get(i);
                p1 = new Point(myWall.c, myWall.r);
                p2 = new Point(myWall.c, myWall.r + 1);
            } else if (finalWalls.get(i).id == MyWall.DOWN) {
                MyWall myWall = finalWalls.get(i);
                p1 = new Point(myWall.c, myWall.r);
                p2 = new Point(myWall.c + 1, myWall.r);
            }
            this.walls[i] = new Wall(new Point[]{p1, p2});
        }
        System.out.println(isSolvable());
    }

    public boolean isWallAt(Point point, int direction) {
        Wall check = new Wall(point, ((direction == RIGHT)?new Point(point.getX(), point.getY()+1) : new Point(point.getX()+1, point.getY())));
        for(int i = 0; i < walls.length; i++) {
            if(walls[i].equals(check)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidMove(Point location, int direction) {
        return !((direction == UP && (location.getY() == 0 || isWallAt(new Point(location.getX(), location.getY()-1), MazeLayout.DOWN))) || (direction == RIGHT && (location.getX() == getSize()[0]-1 || isWallAt(location, MazeLayout.RIGHT))) || (direction == DOWN && (location.getY() == getSize()[1]-1 || isWallAt(location, MazeLayout.DOWN))) || (direction == LEFT && (location.getX() == 0 || isWallAt(new Point(location.getX()-1, location.getY()), MazeLayout.RIGHT))));
    }

    int[][] finalDists;
    boolean[][] finaltrues;
    int minMoves;
    private boolean isSolvable() {
        finaltrues = new boolean[size[1]][size[0]];
        int[][] dists = new int[size[1]][size[0]];
        boolean solve = solveHelp(startPosition, endPosition, new boolean[size[1]][size[0]], dists, 0);
        for(int r = 0; r < size[1]; r++) {
            for(int c = 0; c < size[0]; c++) {
                System.out.print(dists[r][c] + " ");
            }
            System.out.println();
        }
        finalDists = dists;
        minMoves = finalDists[endPosition.getY()][endPosition.getX()];
        return solve;
    }

    private int lengthSolution() {
        return -1;
    }

    private boolean solveHelp(Point current, Point end, boolean[][] visited, int[][] dists, int dist) {
        if(current.getY() == size[1] || current.getX() == size[0] || current.getX() == -1 || current.getY() == -1) {
            return false;
        }
        if(visited[current.getY()][current.getX()]) {
            return false;
        }
        visited[current.getY()][current.getX()] = true;
        dists[current.getY()][current.getX()] = dist;
        dist++;
        if(current.equals(end)) {
            finaltrues[current.getY()][current.getX()] = true;
            return true;
        }
        boolean left = (isValidMove(current, LEFT) && solveHelp(new Point(current.getX()-1, current.getY()), end, visited, dists, dist));
        boolean right = (isValidMove(current, RIGHT) && solveHelp(new Point(current.getX()+1, current.getY()), end, visited, dists, dist));
        boolean up = (isValidMove(current, UP) && solveHelp(new Point(current.getX(), current.getY()-1), end, visited, dists, dist));
        boolean down = (isValidMove(current, DOWN) && solveHelp(new Point(current.getX(), current.getY()+1), end, visited, dists, dist));
        if(left || right || up || down) {
            finaltrues[current.getY()][current.getX()] = true;
        }
        return left || right || up || down;
    }

    /*private boolean isSolvable() {
        return false;
    }

    private int lengthSolution() {
        return dijkstra();
    }*/

    /*private int dijkstra() {
        int[][] visited = new int[size[1]][size[0]];
        for(int r = 0; r < size[1]; r++) {
            for(int c = 0; c < size[0]; c++) {
                visited[r][c] = -2;
            }
        }
        return dijkstraHelper(startPosition, 0, visited);
    }*/



    /*private int dijkstraHelper(Point point, int distance, int[][] visited) {
        if(visited[point.getY()][point.getX()] != -2) {
            return -1;
        }
        //System.out.println(point);
        visited[point.getY()][point.getX()] = distance;
        int up = -1, right = -1, down = -1, left = -1;
        if(isValidMove(point, UP)) {
            up = dijkstraHelper(new Point(point.getX(), point.getY()-1), distance+1, visited);
        }
        if(isValidMove(point, RIGHT)) {
            right = dijkstraHelper(new Point(point.getX()+1, point.getY()), distance+1, visited);
        }
        if(isValidMove(point, DOWN)) {
            down = dijkstraHelper(new Point(point.getX(), point.getY()+1), distance+1, visited);
        }
        if(isValidMove(point, LEFT)) {
            left = dijkstraHelper(new Point(point.getX()-1, point.getY()), distance+1, visited);
        }
        System.out.println(up + " " + right + " " + down + " " + left + " " + isValidMove(point, UP));
        /*if(up == -1 && right == -1 && down == -1 && left == -1) {
            return -1;
        }*/
        /*int first = -1, second = -1;
        if(up == -1 || right == -1 || down == -1 || left == -1) {
            if (up == -1 || right == -1) {
                if (up != -1) {
                    first = up;
                } else if (right != -1) {
                    first = right;
                }
            } else {
                first = Math.min(up, right);
            }
            if (down == -1 || left == -1) {
                if (down != -1) {
                    second = down;
                } else if (left != -1) {
                    second = left;
                }
            } else {
                first = Math.min(down, left);
            }
            if(first == -1 || second == -1) {
                if (first != -1) {
                    return second;
                } else if(second != -1) {
                    return first;
                }
            } else {
                return Math.min(first, second);
            }
        }*//*
        if(up == -1) {
            up = Integer.MAX_VALUE;
        }
        if(right == -1) {
            right = Integer.MAX_VALUE;
        }
        if(down == -1) {
            down = Integer.MAX_VALUE;
        }
        if(left == -1) {
            left = Integer.MAX_VALUE;
        }
        return Math.min(Math.min(up, right), Math.min(down, left));
    }*/

    public Point getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Point startPosition) {
        this.startPosition = startPosition;
    }

    public Point getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Point endPosition) {
        this.endPosition = endPosition;
    }

    public Wall[] getWalls() {
        return walls;
    }

    public void setWalls(Wall[] walls) {
        this.walls = walls;
    }

    public int[] getSize() {
        return size;
    }

    public int getMinMoves() {
        return minMoves;
    }

}

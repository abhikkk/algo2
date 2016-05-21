package mazeGenerator;

import java.util.LinkedList;
import java.util.Random;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator extends MazeUtilities implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		super.generateMaze(maze);
		// TODO Auto-generated method stub
		int width = maze.sizeC;
        int height = maze.sizeR;
        Random rand;

        
        // Stores visited cells
        boolean[] visitedCells = new boolean[width * height];  
        LinkedList<Cell> stack = new LinkedList<Cell>();

        Cell cell = new Cell(maze.entrance.c, maze.entrance.r );
        stack.addFirst(cell);
        int[] neighbours = new int[4];

        do {
            // Mark the current cell as visited

            cells[cell.c*width + cell.r] = true;

            // Examine the current cell's neighbours

            int freeNeighbourCount = 0;
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case UP:
                        if (cell.r > 0 && !cells[(cell.r - 1)*width + cell.c]) {
                            neighbours[freeNeighbourCount++] = i;
                        }
                        break;
                    case RIGHT:
                        if (cell.c < width - 1 && !cells[cell.r*width + (cell.c + 1)]) {
                            neighbours[freeNeighbourCount++] = i;
                        }
                        break;
                    case DOWN:
                        if (cell.r < height - 1 && !cells[(cell.r + 1)*width + cell.c]) {
                            neighbours[freeNeighbourCount++] = i;
                        }
                        break;
                    case LEFT:
                        if (cell.c > 0 && !cells[cell.r*width + (cell.c - 1)]) {
                            neighbours[freeNeighbourCount++] = i;
                        }
                        break;
                }
            }

            // Pick a random free neighbour

            if (freeNeighbourCount > 0) {
                stack.addFirst(cell);
                cell = new Cell(cell.c, cell.r);
                int randomNum = (int) Math.floor(Math.random() * ( 3-0 ));

                switch (randomNum) {
                    case UP:
                        carve(cell.c, cell.r, UP, maze);
                        cell.r--;
                        break;
                    case RIGHT:
                        carve(cell.c, cell.r, RIGHT, maze);
                        cell.c++;
                        break;
                    case DOWN:
                        carve(cell.c, cell.r, DOWN, maze);
                        cell.r++;
                        break;
                    case LEFT:
                        carve(cell.c, cell.r, LEFT, maze);
                        cell.c--;
                        break;
                }

                //System.out.println(cell);
                //print(System.out);
                //System.out.println();
            } else {
                cell = stack.removeFirst();
            }
        } while (!stack.isEmpty());

	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator

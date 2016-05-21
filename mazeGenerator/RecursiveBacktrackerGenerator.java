package mazeGenerator;

import java.util.LinkedList;
import java.util.Random;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator extends MazeUtilities implements MazeGenerator {
	
//	int cellSideCount = 4;
	
	
	@Override
	public void generateMaze(Maze maze) {
		super.initMaze(maze);
		// TODO Auto-generated method stub
		this.width = maze.sizeC;
        this.height = maze.sizeR;
        Random rand;
        
        LinkedList<Cell> pathStack = new LinkedList<Cell>();

        Cell currentCell = new Cell(maze.entrance.r, maze.entrance.c );
        pathStack.addFirst(currentCell);
        int[] neighbourCells = new int[NUM_DIR];
        
        do {
//        	System.out.println(currentCell.r + " > " + currentCell.c);
            // Mark the current cell as visited
        	this.maze.map[currentCell.r][currentCell.c].visited = true;

            // Check cell for free neighbours
            int freeNeighbourCount = 0;
            for (int i = 0; i < NUM_DIR; i++) {
            	neighbourCells[i] = -1;
                switch (i) {
                    case NORTH:
                    	// check if it can move up
                        if (currentCell.r < height-1 && unvisitedCell(currentCell.r + 1, currentCell.c)) {
                        	neighbourCells[i] = i;
                        	freeNeighbourCount++;
//                          	System.out.println("unvisited =  UP");
                        }
                        break;
                    case EAST:
                        if (currentCell.c < width - 1 && unvisitedCell(currentCell.r, currentCell.c + 1)) {
                        	neighbourCells[i] = i;
                        	freeNeighbourCount++;
//                          	System.out.println("unvisited =  RIGHT");
                        }
                        break;
                    case SOUTH:
                        if (currentCell.r > 0 && unvisitedCell(currentCell.r - 1, currentCell.c)) {
                        	neighbourCells[i] = i;
                        	freeNeighbourCount++;
//                          	System.out.println("unvisited =  DOWN");
                        }
                        break;
                    case WEST:
                        if (currentCell.c > 0 && unvisitedCell(currentCell.r, currentCell.c - 1)) {
                        	neighbourCells[i] = i;
                        	freeNeighbourCount++;
//                          	System.out.println("unvisited =  LEFT");
                        }
                        break;
                }
            }

            // Pick a random free neighbour
            if (freeNeighbourCount > 0) {
            	boolean choseRandomFreeCell = false;
            	int randomNum;
            	// randomly select a valid direction - valid nums are greater than 0
            	do {
                  randomNum = (int) Math.round(Math.random() * ( (NUM_DIR-1)-0 ));
                  if (neighbourCells[randomNum] >= 0) {
                	  choseRandomFreeCell = true;
                  }
                  
            	} while (!choseRandomFreeCell);
                Cell newCell = draw(currentCell, randomNum);
                currentCell = newCell;
                pathStack.addFirst(currentCell);
            	
            } else {
            	currentCell = pathStack.removeFirst();
            }

        } while (!pathStack.isEmpty());
        System.out.println("Stack empty");
	} // end of generateMaze()

} // end of class RecursiveBacktrackerGenerator

package mazeGenerator;

import java.util.LinkedList;
import java.util.Random;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator extends MazeUtilities implements
		MazeGenerator {

	// int cellSideCount = 4;

	@Override
	public void generateMaze(Maze maze) {
		super.initMaze(maze);

		if (maze.type == maze.HEX) {
			hexagonalCellGenerator();
		} else {
			rectangularCellGenerator();
		}

	} // end of generateMaze()

	/**
	 * Rectangular cell maze generator
	 * 
	 * @return void
	 */
	public void rectangularCellGenerator() {
		LinkedList<Cell> pathStack = new LinkedList<Cell>();

		Cell currentCell = new Cell(maze.entrance.r, maze.entrance.c);
		pathStack.addFirst(currentCell);
		int[] neighbourCells = new int[maze.NUM_DIR];

		do {
			// Mark the current cell as visited
			this.maze.map[currentCell.r][currentCell.c].visited = true;
			
			// Check cell for free neighbours
			int freeNeighbourCount = 0;

			for (int i = 0; i < maze.NUM_DIR; i++) {
				neighbourCells[i] = -1;
				switch (i) {
				case NORTH:
					// check if it can move up
					if (currentCell.r < height - 1
							&& unvisitedCell(currentCell.r + 1, currentCell.c)) {
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case EAST:
					if (currentCell.c < width - 1
							&& unvisitedCell(currentCell.r, currentCell.c + 1)) {
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case SOUTH:
					if (currentCell.r > 0
							&& unvisitedCell(currentCell.r - 1, currentCell.c)) {
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case WEST:
					if (currentCell.c > 0
							&& unvisitedCell(currentCell.r, currentCell.c - 1)) {
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				}
			}

			// Pick a random free neighbour
			if (freeNeighbourCount > 0) {
				boolean choseRandomFreeCell = false;
				int randomNum;
				// randomly select a valid direction - valid nums are greater
				// than 0
				do {
					randomNum = (int) Math.round(Math.random()
							* ((maze.NUM_DIR - 1) - 0));
					if (neighbourCells[randomNum] >= 0) {
						choseRandomFreeCell = true;
					}

				} while (!choseRandomFreeCell);
				Cell newCell = drawRectangle(currentCell, randomNum);
				currentCell = newCell;
				pathStack.addFirst(currentCell);

			} else {
				currentCell = pathStack.removeFirst();
			}

		} while (!pathStack.isEmpty());
	}

	/**
	 * Hexagonal cell maze generator
	 * 
	 * @return void
	 */
	public void hexagonalCellGenerator() {
		LinkedList<Cell> pathStack = new LinkedList<Cell>();

		Cell currentCell = new Cell(maze.entrance.r, maze.entrance.c);
		pathStack.addFirst(currentCell);
		int[] neighbourCells = new int[maze.NUM_DIR];

		do {
			// Mark the current cell as visited
			this.maze.map[currentCell.r][currentCell.c].visited = true;

//			System.out.println(currentCell.r + " > " + currentCell.c);
			// Check cell for free neighbours
			int freeNeighbourCount = 0;

			for (int i = 0; i < maze.NUM_DIR; i++) {
				neighbourCells[i] = -1;
				switch (i) {
				case NORTHEAST:
//					 System.out.println("check =  NORTHEAST");
					// check if it can move up
					if (unvisitedCell(currentCell.r + 1, currentCell.c + 1)) {
//						System.out.println("NORTHEAST");
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case NORTHWEST:
//					 System.out.println("check =  NORTHWEST");
					if (unvisitedCell(currentCell.r + 1, currentCell.c)) {
//						System.out.println("NORTHWEST");
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case EAST:
//					 System.out.println("check =  EAST");
					if (unvisitedCell(currentCell.r, currentCell.c + 1)) {
//						System.out.println("EAST");
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case WEST:
//					 System.out.println("check =  WEST");
					if (unvisitedCell(currentCell.r, currentCell.c - 1)) {
//						System.out.println("WEST");
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case SOUTHEAST:
//					 System.out.println("check =  SOUTHEAST");
					if (unvisitedCell(currentCell.r - 1, currentCell.c)) {
//						System.out.println("SOUTHEAST");
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				case SOUTHWEST:
//					 System.out.println("check =  SOUTHWEST");
					if (unvisitedCell(currentCell.r - 1, currentCell.c - 1)) {
//						System.out.println("SOUTHWEST");
						neighbourCells[i] = i;
						freeNeighbourCount++;
					}
					break;
				}
			}
			// Pick a random free neighbour
			if (freeNeighbourCount > 0) {
				boolean choseRandomFreeCell = false;
				int randomNum;
				// randomly select a valid direction - valid nums are greater
				// than 0
				do {
					randomNum = (int) Math.round(Math.random()
							* ((maze.NUM_DIR - 1) - 0));
					if (neighbourCells[randomNum] >= 0) {
						choseRandomFreeCell = true;
					}

				} while (!choseRandomFreeCell);
				Cell newCell = drawHexagonal(currentCell, randomNum);
				currentCell = newCell;
				pathStack.addFirst(currentCell);

			} else {
				currentCell = pathStack.removeFirst();
			}

		} while (!pathStack.isEmpty());
	}

} // end of class RecursiveBacktrackerGenerator

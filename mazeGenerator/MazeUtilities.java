package mazeGenerator;

import maze.Cell;
import maze.Maze;
import maze.Wall;

public class MazeUtilities {

	protected int width;
	protected int height;
	protected Maze maze;
	// Stores visited cells
	boolean[][] visitedCells;
	public final static int EAST = 0;
	public final static int NORTHEAST = 1;
	public final static int NORTHWEST = 2;
	public final static int NORTH = 2;
	public final static int WEST = 3;
	public final static int SOUTHWEST = 4;
	public final static int SOUTHEAST = 5;
	public final static int SOUTH = 5;

	public MazeUtilities() {

	}

	public void initMaze(Maze maze) {
		this.width = maze.sizeC;
		this.height = maze.sizeR;
		this.maze = maze;
		this.visitedCells = new boolean[width][height];
	}

	/**
	 * Draws the path for a rectangular cell maze
	 * 
	 * @param currentCell
	 *            Cell Current path cell position
	 * @param direction
	 *            int Path direction to take
	 * @return Cell new cell in path
	 */
	public Cell drawRectangle(Cell currentCell, int direction) {
		Cell newCell = new Cell(currentCell.r, currentCell.c);

		this.maze.map[currentCell.r][currentCell.c].wall[direction].present = false;
		switch (direction) {
		case NORTH:
			newCell = new Cell(currentCell.r + 1, currentCell.c);
			break;
		case EAST:
			newCell = new Cell(currentCell.r, currentCell.c + 1);
			break;
		case SOUTH:
			newCell = new Cell(currentCell.r - 1, currentCell.c);
			break;
		case WEST:
			newCell = new Cell(currentCell.r, currentCell.c - 1);
			break;
		}

		return newCell;
	}

	/**
	 * Draws the path for a Hexagonal cell maze
	 * 
	 * @param currentCell
	 *            Cell Current path cell position
	 * @param direction
	 *            int Path direction to take
	 * @return Cell new cell in path
	 */
	public Cell drawHexagonal(Cell currentCell, int direction) {
		Cell newCell = new Cell(currentCell.r, currentCell.c);

		this.maze.map[currentCell.r][currentCell.c].wall[direction].present = false;
		switch (direction) {
		case NORTHEAST:
//			 System.out.println("draw =  NORTHEAST");
			newCell = new Cell(currentCell.r + 1, currentCell.c + 1);
			break;
		case NORTHWEST:
//			 System.out.println("draw =  NORTHWEST");
			newCell = new Cell(currentCell.r + 1, currentCell.c);
			break;
		case EAST:
//			 System.out.println("draw =  EAST");
			newCell = new Cell(currentCell.r, currentCell.c + 1);
			break;
		case WEST:
//			 System.out.println("draw =  WEST");
			newCell = new Cell(currentCell.r, currentCell.c - 1);
			break;
		case SOUTHEAST:
//			 System.out.println("draw =  SOUTHEAST");
			newCell = new Cell(currentCell.r - 1, currentCell.c);
			break;
		case SOUTHWEST:
//			 System.out.println("draw =  SOUTHWEST");
			newCell = new Cell(currentCell.r - 1, currentCell.c - 1);
			break;
		}

		return newCell;

	}

	/**
	 * Check if a cell is unvisited and within the boundaries
	 * 
	 * @param r
	 *            int R axis point of cell
	 * @param c
	 *            int C axis point of cell
	 * @return boolean
	 */
	public boolean unvisitedCell(int r, int c) {
		boolean valid = false;
		boolean isWithinBoundary = checkBoundary(r, c);
		boolean isVisited = true;

		// check first if it's within the boundary
		if (!isWithinBoundary) {
			return false;
		}

		isVisited = this.maze.map[r][c].visited;

		if (!isVisited) {
			valid = true;
		}
		return valid;
	}

	/**
	 * Checks if cell is within boundaries
	 * 
	 * @param cell
	 * @return boolean
	 */
	public boolean checkBoundary(int r, int c) {

		if (this.maze.type == maze.HEX) {
			return checkHexagonalBoundary(r, c);
		} else {
			return checkRectangularBoundary(r, c);

		}
	}

	public boolean checkRectangularBoundary(int r, int c) {
		boolean isInside = false;
		if (r >= 0 && r < height && c >= 0 && c < width) {
			isInside = true;
		}
		return isInside;
	}

	public boolean checkHexagonalBoundary(int r, int c) {
		boolean isInside = false;
		int maxWidth = (int) (width - 1 + Math.ceil((double)r/2));
		int rowStartIndex = (int) Math.ceil((double)r/2);
//		System.out.println("c = "+ c);
//		System.out.println(Math.ceil((double)r/2));
		if (r >= 0 && r < height && 
				c >= rowStartIndex && c <= maxWidth) {
			isInside = true;
		}
		return isInside;
	}
}

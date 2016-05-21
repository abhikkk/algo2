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
	public final static int NUM_DIR = 6;
	
    
    
	public void initMaze(Maze maze) {
		this.width = maze.sizeC;
	    this.height = maze.sizeR;
	    this.maze = maze;
	    this.visitedCells = new boolean[width][height];  
	}
	
	
	public Cell draw(Cell currentCell, int direction) {
		Cell newCell = new Cell(currentCell.r, currentCell.c);

		this.maze.map[currentCell.r][currentCell.c].wall[direction].present = false;
		switch(direction) {
		  case NORTH:
//          	System.out.println("draw =  UP");
			  newCell = new Cell(currentCell.r + 1, currentCell.c);
            break;
		  case EAST:
//          	System.out.println("draw =  RIGHT");
			  newCell = new Cell(currentCell.r, currentCell.c + 1);
            break;
		  case SOUTH:
//          	System.out.println("draw =  DOWN");
			  newCell = new Cell(currentCell.r - 1, currentCell.c);
            break;
		  case WEST:
//          	System.out.println("draw =  LEFT");
			  newCell = new Cell(currentCell.r, currentCell.c - 1);
            break;
		}
		
		return newCell;
		
	}
	
	public boolean unvisitedCell(int r,int c) {
		boolean valid = false;
		boolean isWithinBoundary = checkBoundary(r, c);
		boolean isVisited = true;

//		if( r >= 0 && r < this.visitedCells.length  && 
//				c >= 0 && c < this.visitedCells[r].length) {
//			isVisited = this.visitedCells[r][c];
//		};
		isVisited = this.maze.map[r][c].visited;
		
		if (!isVisited && isWithinBoundary) {
			valid = true;
		}
		return valid;
	}
	
	/**
	 * Checks if cell is within boundaries
	 * @param cell
	 */
	public boolean checkBoundary(int r,int c) {
		if (r >= 0 && r < width && 
				c >= 0 && c < height) {
			return true;
		}else {
			return false;
		}
	}
}

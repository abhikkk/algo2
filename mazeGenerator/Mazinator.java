package mazeGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import maze.Cell;
import maze.Maze;
import maze.Wall;

/**
 * Base class for maze generators
 * 
 */
public class Mazinator {

	protected int width;
	protected int height;
	protected Maze maze;
	
	protected int visitedCellCount;
	protected List<Cell> visitedList = new ArrayList<Cell>();
	protected int cellTotal;
	HashMap<String, Boolean> hmap = new HashMap<String, Boolean>();

	public void generateMaze(Maze maze) {
		this.width = maze.sizeC;
		this.height = maze.sizeR;
		this.maze = maze;
		this.visitedCellCount = 0;
		this.cellTotal = this.height * this.width;
	}
	
	/**
	 * Sets cell to visited status and increments total count
	 * @param Cell currentCell
	 */
	protected void visitCell(Cell currentCell) {
		if(currentCell.visited == false) {
			currentCell.visited = true;
			this.visitedCellCount++;
			this.visitedList.add(currentCell);
		}
	}
	
	/**
	 * Select a random cell from the maze
	 * @return Cell
	 */
	protected Cell selectRandomCell() {
		Cell newcell;
		int rIndex = (int) Math.round(Math.random() * ((this.height - 1) - 0));
		int cIndex;

		if(this.maze.type == Maze.HEX) {
			int cMax = (int) (width - 1 + Math.ceil((double)rIndex/2));
			int cMin = (int) Math.ceil((double)rIndex/2);
			cIndex = (int) Math.round(Math.random() * (cMax - cMin) + cMin);
		} else {
			cIndex = (int) Math.round(Math.random() * ((this.width - 1) - 0));
		}
		newcell = this.maze.map[rIndex][cIndex];
		return newcell;
	}
	
	
	protected Boolean hasUnvisitedNeighbours(Cell currentCell) {
		Cell[] neighbours = currentCell.neigh;
		int neighbourLength = neighbours.length;
		boolean validNeighbours = false;

		for (int i = 0; i < neighbourLength; i++) {
			if (neighbours[i] != null && neighbours[i].visited == false) {
				validNeighbours = true;
				break;
			}
		}
		
		return validNeighbours;
	}
	
	
	protected Boolean hasUnvisitedPathways(Cell currentCell) {
		Cell[] neighbours = currentCell.neigh;
		int neighbourLength = neighbours.length;
		boolean validNeighbours = false;
		Cell tunnelCell = currentCell.tunnelTo;

		for (int i = 0; i < neighbourLength; i++) {
			if (neighbours[i] != null && neighbours[i].visited == false) {
				validNeighbours = true;
				break;
			}
		}
		
		if(tunnelCell != null && tunnelCell.visited == false) {
			validNeighbours = true;
		}
		
		return validNeighbours;
	}
	
	public void printOutAllCells() {
		for(int r =0;r< this.maze.sizeR; r++) {
			
			for(int c =0;c< this.maze.sizeR; c++) {
				System.out.print("[" +this.maze.map[r][c].r + "," + this.maze.map[r][c].c+ "]" +this.maze.map[r][c].visited );
			}
			
		}
		System.out.println("");	
	}
}

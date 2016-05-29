package mazeSolver;

import maze.Cell;
import maze.Wall;

public class parentSolver {

	boolean visited[][];
	int cellsExplored = 0;
	
	
	protected Boolean hasUnvisitedPathways(Cell currentCell) {
		Wall[] walls = currentCell.wall;
		int wallsLength = walls.length;
		boolean validWalls = false;
		Cell tunnelCell = currentCell.tunnelTo;

		for (int i = 0; i < wallsLength; i++) {
			if (walls[i] != null && walls[i].present == false) {
				validWalls = true;
				break;
			}
		}
		
		if(tunnelCell != null && tunnelCell.visited == false) {
			validWalls = true;
		}
		
		return validWalls;
	}
	
	
	
}

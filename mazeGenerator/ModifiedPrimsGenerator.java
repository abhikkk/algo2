package mazeGenerator;

import java.util.ArrayList;
import java.util.List;

import maze.Cell;
import maze.Maze;

public class ModifiedPrimsGenerator extends Mazinator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		super.generateMaze(maze);
		cellTotal = this.width * this.height;
		Cell currentCell = selectRandomCell();
		visitCell(currentCell);
		

		do {
			pickNeighbour();
		} while (isInComplete());

	} // end of generateMaze()

	
	public void pickNeighbour() {
		int listSize = visitedList.size();
		int randomIndex;
		Cell randomCell;
		boolean neighbourFound = false;
		
		do{
			// select cell from visited list
			randomIndex = (int) Math.round(Math.random() * (listSize - 1));
			randomCell = visitedList.get(randomIndex);
			if(hasUnvisitedNeighbours(randomCell)) {
				// randomly choose neighbour
				do{
					int randomDirection = (int) Math.round(Math.random() * ((maze.NUM_DIR - 1) - 0));
					if(randomCell.neigh[randomDirection] != null && randomCell.neigh[randomDirection].visited == false) {
						randomCell.wall[randomDirection].present = false;
						visitCell(randomCell.neigh[randomDirection]);
						neighbourFound = true;
					}
					
				} while(!neighbourFound);
			}
			
			
		}while(!neighbourFound);
	}
	

	
	private boolean isInComplete() {
		if (visitedCellCount == cellTotal) {
			return false;
		}
		return true;
	}

} // end of class ModifiedPrimsGenerator

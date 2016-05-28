package mazeGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator extends Mazinator implements MazeGenerator {

	LinkedList<Cell> pathStack = new LinkedList<Cell>();

	@Override
	public void generateMaze(Maze maze) {
		super.initMaze(maze);

		Cell currentCell = selectRandomCell();
		visitCell(currentCell);
		
		do {
			pickNeighbour();
		} while (isInComplete());

	} // end of generateMaze()

	
	private boolean pickNeighbour() {
		Cell currentCell = pathStack.getFirst();
		boolean neighbourFound = false;
		
//		List<Cell> unvisitedCellOptions = getUnvisitedNeighbours(currentCell);
		
		if(hasUnvisitedNeighbours(currentCell)) {
			do{
				int randomDirection = (int) Math.round(Math.random() * ((maze.NUM_DIR - 1) - 0));
				if(currentCell.neigh[randomDirection] != null && currentCell.neigh[randomDirection].visited == false) {
					currentCell.wall[randomDirection].present = false;
					visitCell(currentCell.neigh[randomDirection]);
					neighbourFound = true;
				}
				
			} while(!neighbourFound);
			
		} else {
			pathStack.removeFirst();
		}

		return false;
	}
	
	protected void visitCell(Cell currentCell) {
		super.visitCell(currentCell);
		pathStack.addFirst(currentCell);
	}
	
	private boolean isInComplete() {
		if (visitedCellCount == cellTotal) {
			return false;
		}
		return true;
	}
	
} // end of class RecursiveBacktrackerGenerator

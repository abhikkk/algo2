package mazeGenerator;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import maze.Cell;
import maze.Maze;

public class RecursiveBacktrackerGenerator extends Mazinator implements MazeGenerator {
	
	// maze path stack
	LinkedList<Cell> pathStack = new LinkedList<Cell>();

	/**
	 * 
	 * super()
	 * selectRandomCell()
	 * visitCell()
	 * #doWhile( maze generation is incomplete )
	 * 	pickNeighbour()
	 * #end doWhile
	 * 
	 * ------------------------------------------------------------------
	 * Kick off maze generation
	 * 
	 */
	public void generateMaze(Maze maze) {
		super.generateMaze(maze);

		Cell currentCell = selectRandomCell();
		visitCell(currentCell);
		
		do {
			pickNeighbour();
		} while (isInComplete());

	} // end of generateMaze()

	/**
	 * 
	 * 
	 * #if (unvisited options)
	 * 	#if (checkTunnel)
	 * 		takeTunnel
	 * 		visitCell()
	 * 	#else
	 * 		choose random direction
	 * 		#doWhile (unvisted neighbour not found)
	 * 			#if (cell at random direction is unvisited)
	 * 				visitCell()
	 * 			#end if
	 * 		#end doWhile
	 * 	#end if
	 * #else 
	 * 	removeFromPathStack
	 * #end if
	 * 
	 * ------------------------------------------------------------------
	 * Pick the next cell in the path
	 * Moves forward if it finds an unvisited pathway or backtracks if not
	 * 
	 * @return 
	 */
	private Boolean pickNeighbour() {
		Cell currentCell = pathStack.getFirst();
		Boolean neighbourFound = false;
		Boolean hasTunnel = currentCell.tunnelTo != null;
		Cell neighbour = null;
		int directions = Maze.NUM_DIR -1;
		
		// choose a random  unvisited pathway
		if(hasUnvisitedPathways(currentCell)) {
			
				// always take tunnel if unvisited
				if(hasTunnel && currentCell.tunnelTo.visited == false) {
						neighbour = currentCell.tunnelTo;
						visitCell(neighbour);
						System.out.println("just tunnelled");
				} else {
					do{
						// choose a random direction
						int randomDirection = (int) Math.round(Math.random() * directions);
						if(currentCell.neigh[randomDirection] != null && currentCell.neigh[randomDirection].visited == false) {
							currentCell.wall[randomDirection].present = false;
							neighbour = currentCell.neigh[randomDirection];
							neighbourFound = true;
							visitCell(neighbour);
						}
				
					}while(!neighbourFound);
				}
			
		} else {
			pathStack.removeFirst();
		}
			
		return false;
	}
	
	
	/**
	 * super.visitCell()
	 * add cell to pathStack
	 * 
	 * ------------------------------------------------------------------
	 * Sets cell as visited and adds to path stack
	 * 
	 */
	protected void visitCell(Cell currentCell) {
		super.visitCell(currentCell);
		pathStack.addFirst(currentCell);
	}

	/**
	 * #if (total visted == cell count total)
	 * 	return true
	 * #end if
	 * 
	 * ------------------------------------------------------------------
	 * Checks if maze generation is complete
	 * 
	 */
	private Boolean isInComplete() {
		if (visitedCellCount == cellTotal) {
			return false;
		}
		return true;
	}
	
} // end of class RecursiveBacktrackerGenerator

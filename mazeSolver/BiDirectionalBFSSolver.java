package mazeSolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import maze.Cell;
import maze.Maze;
import maze.Wall;

/**
 * Implements Bi-directional BFS maze solving algorithm.
 */
public class BiDirectionalBFSSolver extends Solvinator implements MazeSolver {
	Queue<Cell> entryQueue = new LinkedList<Cell>();
	Queue<Cell> exitQueue = new LinkedList<Cell>();
	List<Cell> entryPathCells = new ArrayList<Cell>();
	List<Cell> exitPathCells = new ArrayList<Cell>();
	Cell entrance;
	Cell exit;
	Maze maze;
	boolean queuedCells[][];
	boolean isSolved = false;
	int cellsExplored = 0;

	/**
	 * ------------------------------------------------------------------
	 * 
	 */
	public void solveMaze(Maze maze) {
		this.maze = maze;
		entrance = maze.entrance;
		exit = maze.exit;
		
		if(this.maze.type == Maze.HEX) {
			queuedCells = new boolean[maze.sizeR][(maze.sizeR+1)/2 + maze.sizeC];
		} else {
			queuedCells = new boolean[maze.sizeR][maze.sizeC];
		}

		queueCells();
	}
	/**
	 * ** NOTE: Is currently on uni-directional
	 * The idea was to implement bi-directional by starting at the exit point
	 * & following the same methods, stoping when the one queue point matched
	 * a cell in the other path list
	 * 
	 * ------------------------------------------------------------------
	 * 
	 * #while (queue is not empty)
	 * 	explore cell
	 * 	queue up valid pathways
	 * #end while 
	 * 
	 * ------------------------------------------------------------------
	 * Queues up the maze's cells
	 * Starts at first queue of entry point 
	 * 
	 * 
	 */
	private void queueCells() {
		Cell entryCell;
		queueCell(entrance, entryQueue);
		exploreCell(entrance, entryPathCells);
		int entryPathwaysCount;
		boolean hasEntryCell;
//		Cell exitCell;
//		exitQueue.add(exit);
//		int exitPathwaysCount;
//		boolean hasExitCell;
		

//		while (!entryQueue.isEmpty() || !exitQueue.isEmpty()) {
		while (!entryQueue.isEmpty()) {
			hasEntryCell = !entryQueue.isEmpty();
			if(hasEntryCell) {
				entryCell = entryQueue.remove();
				entryPathwaysCount = unqueuedPathwaysCount(entryCell);
				if(entryCell == exit) {
					exploreCell(entryCell, entryPathCells);
					System.out.println("At exit");
					isSolved = true;
					break;
				}
				
				queueUpCells(entryPathwaysCount, entryCell, entryQueue, entryPathCells);
			}

//			hasExitCell = !exitQueue.isEmpty();
//			if(hasExitCell) {
//				exitCell = exitQueue.remove();
//				exitPathwaysCount = unqueuedPathwaysCount(exitCell);
//
//				if(exitCell == entrance) {
//					exploreCell(exitCell, exitPathCells);
//					System.out.println("At entry");
//					isSolved = true;
//					break;
//				}
//				queueUpCells(exitPathwaysCount, exitCell, exitQueue, exitPathCells);
//			}
			


		}
		
	}

	
	/**
	 * #if (cell has pathways) {
	 * 	explore the current cell
	 * 	#while (queued pathways is greater than 0)
	 * 		queue each pathway
	 * 	#end while
	 * #end if
	 * 
	 * ------------------------------------------------------------------
	 * Adds all of a cell's pathways to a queue
	 * 
	 * @param pathwayCount Number of pathways
	 * @param nextCell The curent cell
	 * @param queue The queue to add cells to
	 * @param list The list of visited cells
	 */
	private void queueUpCells(int pathwayCount, Cell nextCell, Queue<Cell> queue, List<Cell> list) {
		Cell neighbour;
		boolean hasTunnel = (nextCell.tunnelTo != null);
		Cell tunnelCell = nextCell.tunnelTo;
		
		if(pathwayCount > 0) {
			exploreCell(nextCell, list);
			
			while (pathwayCount > 0) {
				// always take tunnel if unexplored
				if(hasTunnel && queuedStatus(tunnelCell) == false) {
					queueCell(tunnelCell, queue);
					pathwayCount--;
				} else {
				int randomDirection = (int) Math.round(Math.random()* (Maze.NUM_DIR -1));
					if(nextCell.neigh[randomDirection] != null && nextCell.wall[randomDirection].present == false) {
						neighbour = nextCell.neigh[randomDirection];
						if(queuedStatus(neighbour) == false) {
							queueCell(neighbour, queue);
							pathwayCount--;
						}
					}
				}
			}
		}
	}
	
	
	private boolean queuedStatus(Cell nextCell) {
		return queuedCells[nextCell.r][nextCell.c];
		
	}
	
	/**
	 * Adds a cell to specified queue
	 * 
	 * @param nextCell Cell to queue
	 * @param queue Queue to add cell to
	 */
	private void queueCell(Cell nextCell,  Queue<Cell> queue) {
		queuedCells[nextCell.r][nextCell.c] = true;
		queue.add(nextCell);
	}
	
	/**
	 * 
	 * Increments explored cell count & add cell to list of explored paths
	 * 
	 * @param currentCell Cell to check
	 * @param list add Cell to path list
	 */
	protected void exploreCell(Cell currentCell, List<Cell> list) {
		list.add(currentCell);
		this.maze.drawFtPrt(currentCell);
		cellsExplored++;
	}
	
	
	/**
	 * 
	 * 
	 * #loop(for wall length)
	 * 	#if (wall is not present)
	 * 		#if (cell is not queued) 
	 * 			increment pathway count
	 * 		#end if
	 * 	#end if
	 * #end loop
	 * 
	 * #if ( is a tunnel && tunel cell is unqueued )
	 * 	increment pathway count
	 * #end if
	 * 
	 * ------------------------------------------------------------------
	 * Counts up the number of unqueued pathways for a certain cell
	 * 
	 * @param currentCell
	 * @return int Pathway count
	 */
	protected int unqueuedPathwaysCount(Cell currentCell) {
		Wall[] walls = currentCell.wall;
		int wallsLength = walls.length;
		int pathwayCount = 0;
		Cell neighbourCell;
		Cell tunnelCell = currentCell.tunnelTo;

		for (int i = 0; i < wallsLength; i++) {
			if (walls[i] != null && walls[i].present == false) {
				neighbourCell =   currentCell.neigh[i];
				if(queuedCells[neighbourCell.r][neighbourCell.c] == false) {
					pathwayCount++;
				}
			}
		}
		
		if(tunnelCell != null && queuedStatus(tunnelCell) == false) {
			pathwayCount++;
		}
		
		return pathwayCount;
	}
	
	
	public boolean isSolved() {
		return isSolved;
	} 

	public int cellsExplored() {
		return cellsExplored;
	} 

} // end of class BiDirectionalBFSSolver



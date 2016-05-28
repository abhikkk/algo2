package mazeGenerator;

import java.util.ArrayList;
import java.util.List;

import maze.Cell;
import maze.Maze;

public class KruskalGenerator extends Mazinator implements MazeGenerator {
	// contains all the cell sets for kruskal maze generation
	private ArrayList<ArrayList<Cell>> cellSets;
	
	/**
	 * Constructor
	 */
	public KruskalGenerator() {
		cellSets = new ArrayList<ArrayList<Cell>>();
	}
	
	/**
	 * 
	 * super()
	 * #doWhile( maze generation is incomplete )
	 * 	selectRandomCell()
	 * 	pickNeighbour()
	 * #end doWhile
	 * 
	 * ------------------------------------------------------------------
	 * Kick off maze generation
	 * 
	 */
	@Override
	public void generateMaze(Maze maze) {
		super.generateMaze(maze);
		Cell currentCell;
		
		do{
			// pick a random  cell
			currentCell = selectRandomCell();
			pickNeighbour(currentCell);
		}while(isInComplete());

	} // end of generateMaze()
	

	/**
	 * #if (total visited == cell count total && cellSet size == 1)
	 * 	return true
	 * #end if
	 * 
	 * ------------------------------------------------------------------
	 * Checks if maze generation is complete
	 * 
	 */
	private boolean isInComplete() {
		if(visitedCellCount == cellTotal && cellSets.size() == 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * choose a neighbouring cell
	 * #if (visited)
	 * 	return false
	 * #end if
	 * #doWhile (unvisted neighbour not found)
	 * 		choose random direction
	 * 	#if (neighbour already in current set)
	 * 		#return false
	 * 	#else if (neighbour in no set)
	 * 		visitCell()
	 * 		add neighbour to current set
	 * 		#return true
	 * 	#else if (neighbour in other set )
	 * 		visitCell()
	 * 		add neighbour's set items to current set
	 * 		remove neighbour's set from all sets
	 * 	#end if
	 * #end doWhile
	 * ------------------------------------------------------------------
	 * Picks an unvisited neighbouring cell
	 * 
	 * @param currentCell Cell to check for enighbours
	 * @return boolean
	 */
	private boolean pickNeighbour(Cell currentCell) {
		boolean choseRandomNeightbour = false;
		ArrayList<Cell> currentCellSet = getOrMakeCellSet(currentCell);
		ArrayList<Cell> neighbourCellSet;
		int randomNum;
		Cell neighbour;
		
		// visit our new cell
		if(!currentCell.visited) {
			visitCell(currentCell);
		}
		
		if(!checkNeighbours(currentCell,currentCellSet)) {
			return false;
		}
		
		do {
			randomNum = (int) Math.round(Math.random() * ((maze.NUM_DIR - 1) - 0));
			if(currentCell.neigh[randomNum] != null) {
				neighbour = currentCell.neigh[randomNum];
				Boolean neighbourCellInCurrentSet = isInCurrentSet(neighbour, currentCellSet);
				// check if neighbour is part of a set
				neighbourCellSet = getOrMakeCellSet(neighbour);
				if(neighbourCellInCurrentSet) {
					// it's already in our set, do nothing
					choseRandomNeightbour = false;
					
				} else if(getCellSet(neighbour) == null) {
					// in no set - means not visited so break wall
					currentCell.wall[randomNum].present = false;
					//change visited properties
					visitCell(neighbour);
					
					// add to the same set as the current cell
					currentCellSet.add(neighbour);
					choseRandomNeightbour = true;

				} else if(neighbourCellSet != null) {
					// it's already in a set, join them	
					currentCell.wall[randomNum].present = false;
					visitCell(neighbour);
					//add items from matching set to current set
					for(Cell item: neighbourCellSet) {
						currentCellSet.add(item);
					}
					
					// delete entire matching set
					removeCellSet(neighbourCellSet);
					choseRandomNeightbour = true;
					
				}
			}

		} while (!choseRandomNeightbour);
		
		return false;
	}
	
	/**
	 * 
	 * #loop (all neighbours)
	 * 	#if (neighbour not in set)
	 *    #return true
	 * 	#end if
	 * #end loop
	 * 
	 * ------------------------------------------------------------------
	 * Checks if there are any neighbours not in current cell set
	 * 
	 * @param currentCell Cell to check for neighbours
	 * @param currentCellSet The cell's current set
	 * @return Cell has unvisited neighbours
	 */
	private Boolean checkNeighbours(Cell currentCell, List<Cell> currentCellSet) {
		Cell[] neighbours = currentCell.neigh;
		int neighbourLength = neighbours.length;
		int validNeighbours = 0;
		int matches = 0;
		// check if there are any neighbours not in current cell set
		for(int i=0;i< neighbourLength; i++){
			if(neighbours[i] != null){
				validNeighbours++;
				if(currentCellSet.contains(neighbours[i])) {
					matches++;
				}
			}
		}
		
		if(validNeighbours == matches) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * #if (set contains cell)
	 * 	return true
	 * #end if
	 * 
	 * ------------------------------------------------------------------
	 * Checks if the cell is in the set
	 * @param newCell Cell to match
	 * @param currentSet Set to match
	 * @return
	 */
	private boolean isInCurrentSet(Cell newCell, List<Cell> currentSet) {
		boolean inSet = false;
		
		if(currentSet.contains(newCell)) {
			inSet = true;
		}
		
		return inSet;
	}
	
	/**
	 * 
	 * getCellSet()
	 * #if (set found)
	 * 	return set
	 * #else 
	 * 	create new set & add cell
	 * 	add set to all sets list
	 * 	return new set
	 * #
	 * 
	 * 
	 * ------------------------------------------------------------------
	 * Search for set containing cell or make new one
	 * 
	 * @param newCell
	 * @return
	 */
	private ArrayList<Cell> getOrMakeCellSet(Cell newCell) {
		ArrayList<Cell> matchingSet = getCellSet(newCell);
		
		// no set found, so create a new one & add cell
		if(matchingSet == null) {
			matchingSet = new ArrayList<Cell>();
			matchingSet.add(newCell);
			cellSets.add(matchingSet);
		}

		return matchingSet;
	}
	
	/**
	 * 
	 * #loop (list of all sets)
	 * 	#if (list contains newCell)
	 * 		return match
	 * 	#end if
	 * #end loop
	 * 
	 * ------------------------------------------------------------------
	 * Search for set containing cell
	 * 
	 * @param newCell Find a set with this cell
	 * @return List<Cell> The matching set
	 */
	private ArrayList<Cell> getCellSet(Cell newCell) {
		ArrayList<Cell> matchingSet = null;
		for(ArrayList<Cell> list: cellSets) {
			if(list.contains(newCell)){
				matchingSet = list;
				break;
			}
		}
		return matchingSet;
	}
	
	/**
	 * 
	 * #loop (list of all sets)
	 * 	#if (remove set == set)
	 * 	  remove set from list of all sets
	 * 	  #break
	 * 	#end if
	 * #end loop
	 * 
	 * ------------------------------------------------------------------
	 * Removes a set from the list of all sets
	 * 
	 * @param setToRemove The set to remove from all set list
	 */
	private void removeCellSet(List<Cell> setToRemove) {
		int index = 0;
		for(ArrayList<Cell> list: cellSets) {
			if(list.equals(setToRemove)){
				cellSets.remove(index);
				break;
			}
			index++;
		}
	}
	
	/**
	 * super.visitCell()
	 * #if (cell has tunnel) 
	 * 	add cell to current set
	 * #end if
	 * 
	 * ------------------------------------------------------------------
	 * Sets cell as visited and checks for tunnels
	 * 
	 * @param currentCell Cell to visit
	 * 
	 */
	protected void visitCell(Cell currentCell) {
		super.visitCell(currentCell);
		Cell tunnelCell = currentCell.tunnelTo;
		// we need to add tunnel cells to the set
		if(tunnelCell != null && tunnelCell.visited == false) {
			visitCell(tunnelCell);
			ArrayList<Cell> currentSet = getCellSet(currentCell);
			currentSet.add(tunnelCell);
		}
	}


} // end of class KruskalGenerator

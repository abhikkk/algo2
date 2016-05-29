package mazeSolver;
import maze.*;
import java.util.ArrayList;


/**
 * 
 * 
 * selectRandomCell()
 * solveMaze() Chooses what kind of maze we are solving. 
 * Depending on the maze it calls the method.
 * HexSolver() / TunnelSolver() / RectangleSolver() is called.
 * returns isSolved 
 * returns no of cells explored.
 * 
 * ------------------------------------------------------------------
 * 
 * 
 * 
 * If the current square is outside, return TRUE to indicate that a solution has been
 * found.
 * If the current square is marked, return FALSE to indicate that this path has been
 * tried.
 * Mark the current square.
 * for (each of the four compass directions)
 * { if ( this direction is not blocked by a wall )
 * { Move one step in the indicated direction from the current square.
 * Try to solve the maze from there by making a recursive call.
 * If this call shows the maze to be solvable, return TRUE to indicate that
 * fact.
 * }
 * }
 * Unmark the current square.
 * Return FALSE to indicate that none of the four directions led to a solution.
 * 
 */



public class RecursiveBacktrackerSolver extends Solvinator implements MazeSolver {
	
		boolean visited[][]; // Keeps a track of cells visited
		Cell Map[][];
		Maze maze;
		boolean isSolved = false;
		int cellsExplored = 0; 
		
		
		/**
		 * SolveMaze()
		 * Check maze type
		 * Case : Mazetype
		 * calls specific maze solver
		 * break
		 */
		
		
		@Override
		public void solveMaze(Maze maze) {

			this.maze = maze;
			Map = maze.map;
			Cell InitialCell ; // Chooses an initial cell 
			switch (maze.type) { // Decides the maze type
			case Maze.NORMAL:
				visited = new boolean[maze.sizeR][maze.sizeC];
				RectangleSolver(maze.entrance);
				break;
			case Maze.TUNNEL:
				visited = new boolean[maze.sizeR][maze.sizeC];
				InitialCell = Map[randomiser(0, maze.sizeR - 1)][randomiser(
						0, maze.sizeC - 1)];
				TunnelSolver(InitialCell);
				break;
			case Maze.HEX:
				visited = new boolean[maze.sizeR][(maze.sizeR+1)/2 + maze.sizeC];
				HexSolver(maze.entrance);
				break;
			default:
				break;
			}
		} // end of solveMaze()

		/**FIND-PATH(x, y)

		if (x,y outside maze) return false
		if (x,y is goal) return true
		mark x,y as part of solution path
		if (FIND-PATH(North of x,y) == false)
		Go to neighbor.
		#While(Till the direction is !=0 )
		Uses the formula (C+1)/2+C-1 for Upright and (C+1)/2+C for Upleft.
	  	pickNeighbour()
		return true
		unmark x,y apart of solution path
		return false
		++ no of cells explored
		**/
		
		private void HexSolver(Cell cell) {
			int r = cell.r;
			int c = cell.c;
			ArrayList<Integer> directions = getAllHexDir();

			try {
				Integer randomDirection;
				visited[cell.r][cell.c] = true;
				while (directions.size() != 0) {
					if(cell != maze.exit){
						randomDirection = getRandomArray(directions.toArray(new Integer[directions.size()]));
						if (isInHex(r + Maze.deltaR[randomDirection], c + Maze.deltaC[randomDirection])
								&& !visited[r + Maze.deltaR[randomDirection]][c	+ Maze.deltaC[randomDirection]]
										&& !cell.wall[randomDirection].present) {
							cell.wall[randomDirection].present = false;
							HexSolver(cell.neigh[randomDirection]);
							if(isSolved){
								maze.drawFtPrt(cell);
								cellsExplored++;
								return;
							}
						}
						directions.remove(randomDirection);
					}else{
						maze.drawFtPrt(cell);
						isSolved = true;
						cellsExplored++;
						return;
					}	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private boolean isInHex(int r, int c) {
			return r >= 0 && r < maze.sizeR && c >= (r + 1) / 2 && c < maze.sizeC + (r + 1) / 2;
		}

		/**FIND-PATH(x, y)

		if (x,y outside maze) return false
		if (x,y is goal) return true
		mark x,y as part of solution path
		if x,y has a tunnel, goes to the tunnel exit points and repeats the process.
		if (FIND-PATH(North of x,y) == false)
		Go to neighbor.
		#While(Till the direction is !=0 )
	  	pickNeighbour()
		return true
		unmark x,y apart of solution path
		return false
		++ no of cells explored
		**/
		private void TunnelSolver(Cell cell) {
			int r = cell.r;
			int c = cell.c;
			ArrayList<Integer> directions = getAllDirs();

			try {
				Integer randomDirection;
				visited[cell.r][cell.c] = true;
				if(null != Map[cell.r][cell.c].tunnelTo){
					cell.tunnelTo = Map[cell.r][cell.c].tunnelTo;
					visited[cell.tunnelTo.r][cell.tunnelTo.c] = true; // Tunnels the pointer automatically to the end of the tunnel
				}
				while (directions.size() != 0) {
					if(cell != maze.exit){
						randomDirection = getRandomArray(directions.toArray(new Integer[directions.size()]));
						if (isIn(r + Maze.deltaR[randomDirection], c	+ Maze.deltaC[randomDirection])
								&& !visited[r + Maze.deltaR[randomDirection]][c	+ Maze.deltaC[randomDirection]] 
										&& !cell.wall[randomDirection].present) {
							cell.wall[randomDirection].present = false;
							TunnelSolver(cell.neigh[randomDirection]);
							if(isSolved){
								maze.drawFtPrt(cell);
								cellsExplored++;
								return;
							}
						}
						directions.remove(randomDirection);
					}else{
						maze.drawFtPrt(cell);
						isSolved = true;
						cellsExplored++;
						return;
					}	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**FIND-PATH(x, y)

		if (x,y outside maze) return false
		if (x,y is goal) return true
		mark x,y as part of solution path
		if (FIND-PATH(North of x,y) == false)
		Go to neighbor.
		#While(Till the direction is !=0 )
	  	pickNeighbour()
		return true
		unmark x,y apart of solution path
		return false
		++ no of cells explored
		**/
		
		private void RectangleSolver(Cell cell) {
			int r = cell.r;
			int c = cell.c;
			ArrayList<Integer> directions = getAllDirs();

			try {
				Integer randomDirection;
				visited[cell.r][cell.c] = true;
				while (directions.size() != 0) {
					if(cell != maze.exit){
						randomDirection = getRandomArray(directions.toArray(new Integer[directions.size()]));
						if (isIn(r + Maze.deltaR[randomDirection], c	+ Maze.deltaC[randomDirection])
								&& !visited[r + Maze.deltaR[randomDirection]][c	+ Maze.deltaC[randomDirection]] 
										&& !cell.wall[randomDirection].present) {
							RectangleSolver(cell.neigh[randomDirection]);
							if(isSolved){
								maze.drawFtPrt(cell);
								cellsExplored++;
								return;
							}
						}
						directions.remove(randomDirection);
					}else{
						maze.drawFtPrt(cell);
						isSolved = true;
						cellsExplored++;
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		protected boolean isIn(int r, int c) {
			return r >= 0 && r < maze.sizeR && c >= 0 && c < maze.sizeC;
		}
		
		
		
		@Override
		public boolean isSolved() {
			return isSolved;
		} // end if isSolved()


		@Override
		public int cellsExplored() {
			return cellsExplored;
		} // end of cellsExplored()


	} // end of class RecursiveBackTrackerSolver




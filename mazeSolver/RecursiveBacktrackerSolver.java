package mazeSolver;
import maze.*;
import java.util.ArrayList;

/**
 * Implements the recursive backtracking maze solving algorithm.
 */
public class RecursiveBacktrackerSolver extends Solvinator implements MazeSolver {
	
		boolean visited[][];
		Cell Map[][];
		Maze maze;
		boolean isSolved = false;
		int cellsExplored = 0;
		
		@Override
		public void solveMaze(Maze maze) {

			this.maze = maze;
			Map = maze.map;
			Cell InitialCell ; // Chooses an initial cell 
			switch (maze.type) {
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


		private void TunnelSolver(Cell cell) {
			int r = cell.r;
			int c = cell.c;
			ArrayList<Integer> directions = getAllDirs();

			try {
				Integer randomDirection;
				visited[cell.r][cell.c] = true;
				if(null != Map[cell.r][cell.c].tunnelTo){
					cell.tunnelTo = Map[cell.r][cell.c].tunnelTo;
					visited[cell.tunnelTo.r][cell.tunnelTo.c] = true;
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




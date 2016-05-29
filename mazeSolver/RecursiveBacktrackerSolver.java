package mazeSolver;

import maze.Maze;
import java.util.ArrayList;
import java.util.Random;

import maze.*;

/**
 * Implements the recursive backtracking maze solving algorithm.
 */
public class RecursiveBacktrackerSolver extends Solvinator implements MazeSolver {
	
		boolean visited[][];
		Cell map[][];
		Maze maze;
		boolean isSolved = false;
		int cellsExplored = 0;
		
		@Override
		public void solveMaze(Maze maze) {

			this.maze = maze;
			map = maze.map;
			// pickup first random cell;
			Cell initCell ;
			switch (maze.type) {
			case Maze.NORMAL:
				visited = new boolean[maze.sizeR][maze.sizeC];
				makeRectWay(maze.entrance);
				break;
			case Maze.TUNNEL:
				visited = new boolean[maze.sizeR][maze.sizeC];
				initCell = map[randNumber(0, maze.sizeR - 1)][randNumber(
						0, maze.sizeC - 1)];
				makeTunnelWay(initCell);
				break;
			case Maze.HEX:
				visited = new boolean[maze.sizeR][(maze.sizeR+1)/2 + maze.sizeC];
				makeHexWay(maze.entrance);
				break;
			default:
				break;
			}
		} // end of solveMaze()


		private void makeHexWay(Cell cell) {
			int r = cell.r;
			int c = cell.c;
			ArrayList<Integer> directions = getAllHexDir();

			try {
				Integer randomDirection;
				visited[cell.r][cell.c] = true;
				while (directions.size() != 0) {
					if(cell != maze.exit){
						randomDirection = getRandArray(directions.toArray(new Integer[directions.size()]));
						if (isInHex(r + Maze.deltaR[randomDirection], c + Maze.deltaC[randomDirection])
								&& !visited[r + Maze.deltaR[randomDirection]][c	+ Maze.deltaC[randomDirection]]
										&& !cell.wall[randomDirection].present) {
							cell.wall[randomDirection].present = false;
							makeHexWay(cell.neigh[randomDirection]);
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


		private void makeTunnelWay(Cell cell) {
			int r = cell.r;
			int c = cell.c;
			ArrayList<Integer> directions = getAllDirs();

			try {
				Integer randomDirection;
				visited[cell.r][cell.c] = true;
				if(null != map[cell.r][cell.c].tunnelTo){
					cell.tunnelTo = map[cell.r][cell.c].tunnelTo;
					visited[cell.tunnelTo.r][cell.tunnelTo.c] = true;
				}
				while (directions.size() != 0) {
					if(cell != maze.exit){
						randomDirection = getRandArray(directions.toArray(new Integer[directions.size()]));
						if (isIn(r + Maze.deltaR[randomDirection], c	+ Maze.deltaC[randomDirection])
								&& !visited[r + Maze.deltaR[randomDirection]][c	+ Maze.deltaC[randomDirection]] 
										&& !cell.wall[randomDirection].present) {
							cell.wall[randomDirection].present = false;
							makeTunnelWay(cell.neigh[randomDirection]);
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

		private void makeRectWay(Cell cell) {
			int r = cell.r;
			int c = cell.c;
			ArrayList<Integer> directions = getAllDirs();

			try {
				Integer randomDirection;
				visited[cell.r][cell.c] = true;
				while (directions.size() != 0) {
					if(cell != maze.exit){
						randomDirection = getRandArray(directions.toArray(new Integer[directions.size()]));
						if (isIn(r + Maze.deltaR[randomDirection], c	+ Maze.deltaC[randomDirection])
								&& !visited[r + Maze.deltaR[randomDirection]][c	+ Maze.deltaC[randomDirection]] 
										&& !cell.wall[randomDirection].present) {
							makeRectWay(cell.neigh[randomDirection]);
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




package mazeSolver;
import java.util.*;
import maze.*;

import maze.Maze;

public class SupportSolver {

	
		protected int sizeCol;
		protected int sizeRow;
		protected Maze myMaze;
		protected int visitedCellsCount;
		protected List<Cell> visitedCells = new ArrayList<Cell>();
		protected int totalCells;
		HashMap<String,Boolean> mapCell = new HashMap<String,Boolean>();
		
		public void initMaze(Maze maze){
			this.sizeRow = maze.sizeR;
			this.sizeCol = maze.sizeC;
			}
		public ArrayList<Integer> getAllDirs() {
			ArrayList<Integer> directions = new ArrayList<Integer>();
				directions.add(Maze.EAST);
				directions.add(Maze.NORTH);
				directions.add(Maze.WEST);
				directions.add(Maze.SOUTH);
				return directions;
			} //end of getAllDirs()
			
			public ArrayList<Integer> getAllHexDir() {
				ArrayList<Integer> directions = new ArrayList<Integer>();
				directions.add(Maze.EAST);
				directions.add(Maze.NORTHEAST);
				directions.add(Maze.NORTHWEST);
				directions.add(Maze.WEST);
				directions.add(Maze.SOUTHWEST);
				directions.add(Maze.SOUTHEAST);
				return directions;
			} //end of getAllHexDir()

			
			
			public int randNumber(int min, int max){
				int toReturn = 0;
				Random rand = new Random();
				toReturn = min + rand.nextInt((max - min) + 1);
				return toReturn;
			} // end of randNumber()
			public int getRandDirs(){
				int randNumber = randNumber(1, 4);
				int toReturn = 0;
				switch (randNumber) {
				case 1:
					toReturn = Maze.EAST;
					break;
				case 2:
					toReturn = Maze.NORTH;		
					break;
				case 3:
					toReturn = Maze.WEST;
					break;
				case 4:
					toReturn = Maze.SOUTH;
					break;
				default:
					System.out.println("Warning : Default called!!");
					break;
				}
				return toReturn;
			} // end of getRandDirs()
			
			public Integer getRandArray(Integer a[]){
				return a[randNumber(1, a.length)-1];
			} // end of getRandArray()
	}



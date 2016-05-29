package mazeSolver;
import java.util.*;
import maze.*;

import maze.Maze;

//Support class for Solving maze
public class Solvinator {

	
		protected int sizeC;
		protected int sizeR;
		protected Maze myMaze;
		protected int visitedCellsCount;
		protected List<Cell> visitedCells = new ArrayList<Cell>();
		protected int totalCells;
		HashMap<String,Boolean> mapCell = new HashMap<String,Boolean>();
		
		public void InitialMaze(Maze maze){
			this.sizeR = maze.sizeR;
			this.sizeC = maze.sizeC;
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

			
			// Method to get a Random Number
			public int randomiser(int min, int max){
				int Return = 0;
				Random rand = new Random();
				Return = min + rand.nextInt((max - min) + 1);
				return Return;
			} // end of randomiser()
			
			
			// Method to get a Random Direction
			public int getRandomDir(){
				int randomiser = randomiser(1, 4);
				int Return = 0;
				switch (randomiser) {
				case 1:
					Return = Maze.EAST;
					break;
				case 2:
					Return = Maze.NORTH;		
					break;
				case 3:
					Return = Maze.WEST;
					break;
				case 4:
					Return = Maze.SOUTH;
					break;
				default:
					System.out.println("Unknown Direction!");
					break;
				}
				return Return;
			} // end of getRandomDir()
			
			public Integer getRandomArray(Integer a[]){
				return a[randomiser(1, a.length)-1];
			} // end of getRandomArray()
	}



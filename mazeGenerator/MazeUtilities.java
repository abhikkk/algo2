package mazeGenerator;

import maze.Maze;

public class MazeUtilities {
	
    /** Represents UP. */
    public static final int UP    = 0;

    /** Represents RIGHT. */
    public static final int RIGHT = 1;

    /** Represents DOWN. */
    public static final int DOWN  = 2;

    /** Represents LEFT. */
    public static final int LEFT  = 3;

    private int width;
    private int height;
    // Stores whether the walls exist or not

    private boolean[] horizWalls;
    private boolean[] vertWalls;
    

	public void generateMaze(Maze maze) {
		width = maze.sizeC;
	    height = maze.sizeR;
	    horizWalls = new boolean[width * (height + 1)];
	    vertWalls  = new boolean[(width + 1) * height];
	}
	
	protected boolean carve(int x, int y, int direction, Maze maze) {
		// Check the arguments


		checkDirection(direction);
		checkLocation(x, y);

		int index = -1;
		boolean[] array = null;

		switch (direction) {
			case UP:
				index = y*width + x;
				array = horizWalls;
				break;
			case DOWN:
				index = (y + 1)*width + x;
				array = horizWalls;
				break;
			case LEFT:
				index = y*(width + 1) + x;
				array = vertWalls;
				break;
			case RIGHT:
				index = y*(width + 1) + (x + 1);
				array = vertWalls;
				break;
		}

		// Set the wall to 'false' and return what it was before

		boolean b = array[index];
		array[index] = false;
		return b;
	}
	
	
	  private static void checkDirection(int direction) {
	        switch (direction) {
	            case UP:
	            case RIGHT:
	            case DOWN:
	            case LEFT:
	                break;
	            default:
	                throw new IllegalArgumentException("Bad direction: " + direction);
	        }
	    }
	  
	    /**
	     * Checks that the given cell location is valid.
	     *
	     * @param x the cell's X-coordinate
	     * @param y the cell's Y-coordinate
	     * @throws IndexOutOfBoundsException if the coordinate is out of range.
	     */
	    protected void checkLocation(int x, int y) {
	        if (x < 0 || width <= x) {
	            throw new IndexOutOfBoundsException("X out of range: " + x);
	        }
	        if (y < 0 || height <= y) {
	            throw new IndexOutOfBoundsException("Y out of range: " + y);
	        }
	    }
}

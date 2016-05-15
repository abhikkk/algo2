package mazeSolver;

import java.util.Random;
import java.util.Stack;
import maze.Cell;
import maze.Maze;
import maze.Wall;
import mazeSolver.MazeSolver;

public class SampleSolver
implements MazeSolver {
    Maze maze = null;
    Random randGen = new Random();
    boolean solved = false;
    boolean[][] visited = null;

    protected int[] randDirSeq() {
        int[] seq = new int[6];
        boolean[] present = new boolean[6];
        int i = 0;
        while (i < 6) {
            do {
                seq[i] = this.randGen.nextInt(6);
            } while (present[seq[i]]);
            present[seq[i]] = true;
            ++i;
        }
        return seq;
    }

    boolean isVisited(Cell cell) {
        return this.visited[cell.r][cell.c];
    }

    public void solveMaze(Maze maze) {
        this.maze = maze;
        this.visited = maze.type == 2 ? new boolean[maze.sizeR][maze.sizeC + (maze.sizeR + 1) / 2] : new boolean[maze.sizeR][maze.sizeC];
        Stack<Cell> stack = new Stack<Cell>();
        stack.push(maze.entrance);
        while (!stack.isEmpty()) {
            Cell cell = (Cell)stack.peek();
            this.visited[cell.r][cell.c] = true;
            maze.drawFtPrt(cell);
            if (cell == maze.exit) {
                this.solved = true;
                return;
            }
            if (maze.type == 1 && cell.tunnelTo != null && !this.isVisited(cell.tunnelTo)) {
                stack.push(cell.tunnelTo);
                continue;
            }
            boolean expandable = false;
            int[] seq = this.randDirSeq();
            int i = 0;
            while (i < 6) {
                Cell next = cell.neigh[seq[i]];
                if (next != null && !cell.wall[seq[i]].present && !this.isVisited(next)) {
                    stack.push(next);
                    expandable = true;
                    break;
                }
                ++i;
            }
            if (expandable) continue;
            stack.pop();
        }
    }

    public boolean isSolved() {
        return this.solved;
    }

    public int cellsExplored() {
        int acc = 0;
        int sizeC = this.maze.sizeC;
        if (this.maze.type == 2) {
            sizeC = this.maze.sizeC + (this.maze.sizeR + 1) / 2;
        }
        int i = 0;
        while (i < this.maze.sizeR) {
            int j = 0;
            while (j < sizeC) {
                if (this.visited[i][j]) {
                    ++acc;
                }
                ++j;
            }
            ++i;
        }
        return acc;
    }
}


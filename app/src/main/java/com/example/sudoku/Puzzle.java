package com.example.sudoku;

import java.io.*;

/**
 *
 */
public class Puzzle implements Serializable
{
    /**
     * Creates a Sudoku Puzzle with the given current puzzle and solution
     * @param puzzle the current puzzle value
     * @param solution the solution of the puzzle
     */
    public Puzzle(int[][] puzzle, int[][] solution)
    {
        this.puzzle = puzzle;
        this.solution = solution;
        this.notes = new boolean[9][9][9];
    }

    /**
     * Checks if the current puzzle is legal after adding an entry
     * @param row row of new entry
     * @param col column of new entry
     * @param num value of new entry
     * @return true if current puzzle is valid, false otherwise
     */
    public boolean isLegal(int row, int col, int num)
    {
        // Checks for row clash
        for (int c = 0; c < PUZZLE_LENGTH; c++)
        {
            if (puzzle[row][c] == num)
                //TODO ADD HIGHLIGHT TO SHOW ROW CLASH
                return false;
        }

        // Checks for column clash
        for (int r = 0; r < PUZZLE_LENGTH; r++)
        {
            if (puzzle[r][col] == num)
                //TODO ADD HIGHLIGHT TO SHOW COLUMN CLASH
                return false;
        }

        // Checks for square clash
        int squareRowStart = row - row % SQUARE_LENGTH;
        int squareColStart = col - col % SQUARE_LENGTH;

        for (int r = squareRowStart; r < squareRowStart + SQUARE_LENGTH; r++)
        {
            for (int c = squareColStart; c < squareColStart + SQUARE_LENGTH; c++)
            {
                if (puzzle[r][c] == num)
                    //TODO ADD HIGHLIGHT TO SHOW SQUARE CLASH
                    return false;
            }
        }

        // No clash
        return true;
    }

    /**
     * Adds an entry to the puzzle and checks if it's legal
     * @param row row of new entry
     * @param col column of new entry
     * @param num value of new entry
     * @return true if current puzzle is valid; false otherwise
     */
    public boolean changeEntry(int row, int col, int num)
    {
        puzzle[row][col] = num;
        //TODO UPDATE UI OF PUZZLE
        return isLegal(row,col,num);
    }

    /**
     * Deletes the current entry from the puzzle
     * @param row row of entry to be deleted
     * @param col column of entry to be deleted
     * @param num value of entry to be deleted
     * @return true if the entry could be deleted; false otherwise
     */
    public boolean deleteEntry(int row, int col, int num)
    {
        if (solution[row][col] == 0)
        {
            puzzle[row][col] = 0;
            //TODO UPDATE UI OF PUZZLE
            return true;
        }

        return false;
    }

    /**
     * Changes the notes in the Sudoku puzzle
     * @param row row of note to change
     * @param col column of note to change
     * @param num value of note to change
     * @return true if note toggled on; false if note toggled off
     */
    public boolean toggleNote(int row, int col, int num)
    {
        notes[row][col][num] = !notes[row][col][num];
        //TODO UPDATE UI FOR NOTES
        return notes[row][col][num];
    }

    public final int[][] puzzle;
    public final int[][] solution;
    public final boolean[][][] notes;
    public static final int PUZZLE_LENGTH = 9;
    public static final int SQUARE_LENGTH = 3;
}

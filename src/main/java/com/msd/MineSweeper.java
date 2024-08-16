package com.msd;

import java.util.Random;
import java.util.Scanner;

/**
 * @author mahendrasridayarathna
 * @created 16/08/2024 - 10:01 am
 * @project IntelliJ IDEA
 */

public class MineSweeper {

    public static char[][] gridView;
    public static boolean[][] mines;
    public static boolean[][] uncovered;
    public static int gridSize;
    public static int mineCount;

    protected static final char MINE = '*';
    protected static final char COVERED = '_';
    protected static final int[] DX = {-1, -1, -1, 0, 0, 1, 1, 1};
    protected static final int[] DY = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) {
        startGame();
    }

    /**
     * Starts the game.
     */
    public static void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Minesweeper App !!");

        System.out.print("Enter the size of the grid (e.g. 4 for a 4x4 grid): ");
        gridSize = scanner.nextInt();
        System.out.print("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
        mineCount = scanner.nextInt();

        initializeGridView();
        randomPlaceMines();

        while (true) {
            displayCurrentGrid();
            System.out.print("Select a Square to reveal (e.g. A1): ");
            String input = scanner.next();
            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;
            // Check if the row and column are within the grid bounds.
            if (mines[row][col]) {
                System.out.println("Oh no, you detonated a mine! Game over.");
                break;
            } else {
                uncoverItem(row, col);
                // Check if the player has won the game.
                if (checkWin()) {
                    displayCurrentGrid();
                    System.out.println("Congratulations, you have won the game!");
                    break;
                }
            }
        }
    }

    /**
     * Initializes the grid with all squares covered and no mines placed.
     */
    public static void initializeGridView() {
        gridView = new char[gridSize][gridSize];
        mines = new boolean[gridSize][gridSize];
        uncovered = new boolean[gridSize][gridSize];

        // Initialize the grid with all squares covered.
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridView[i][j] = COVERED;
            }
        }
    }

    /**
     * Randomly places the specified number of mines on the grid.
     */
    public static void randomPlaceMines() {
        Random random = new Random();
        int placedMines = 0;

        // Place mines randomly on the grid.
        while (placedMines < mineCount) {
            int row = random.nextInt(gridSize);
            int col = random.nextInt(gridSize);
            if (!mines[row][col]) {
                mines[row][col] = true;
                placedMines++;
            }
        }
    }

    /**
     * Displays the current state of the grid.
     */
    private static void displayCurrentGrid() {
        System.out.print("  ");

        // Display the column numbers.
        for (int i = 1; i <= gridSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Display the grid with row letters and square contents.
        for (int i = 0; i < gridSize; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < gridSize; j++) {
                // If the square is uncovered, display the mine or the number of adjacent mines.
                if (uncovered[i][j]) {
                    // If the square is a mine, display the mine character.
                    if (mines[i][j]) {
                        System.out.print(MINE + " ");
                    } else {
                        System.out.print(countNextMines(i, j) + " ");
                    }
                } else {
                    System.out.print(COVERED + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Recursively uncovers the square at the specified row and column.
     * @param row
     * @param col
     */
    public static void uncoverItem(int row, int col) {
        // Check if the row and column are within the grid bounds and if the square is already uncovered.
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize || uncovered[row][col]) {
            return;
        }
        uncovered[row][col] = true;
        // If the square has no adjacent mines, uncover all adjacent squares.
        if (countNextMines(row, col) == 0) {
            for (int i = 0; i < 8; i++) {
                uncoverItem(row + DX[i], col + DY[i]);
            }
        }
    }

    /**
     * Counts the number of mines adjacent to the square at the specified row and column.
     * @param row
     * @param col
     * @return
     */
    public static int countNextMines(int row, int col) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int newRow = row + DX[i];
            int newCol = col + DY[i];
            // Check if the new row and column are within the grid bounds and if there is a mine at that location.
            if (newRow >= 0 && newRow < gridSize && newCol >= 0 && newCol < gridSize && mines[newRow][newCol]) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the player has won the game.
     * @return
     */
    public static boolean checkWin() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                // If there is a square that is not a mine and is not uncovered, the game is not won yet.
                if (!mines[i][j] && !uncovered[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}

package com.example.mob_dev_portfolio.EgoCrush;
import java.util.ArrayList;
import java.util.List;

public class Board {


    public static final String PLAYER = "O";
    public static final String COMPUTER = "X";

    private String[][] board = new String[3][3];
    public String[][] getBoard() {
        return board;
    }

    // This method is giving us
    // a list of all the empty cells
    public List<Cell> getAvailableCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == null || board[i][j].isEmpty()) {
                    cells.add(new Cell(i, j));
                }
            }
        }
        return cells;
    }

    // This method will tell
    // if the game is over or not
    public boolean isGameOver() {
        return hasComputerWon() || hasPlayerWon() || getAvailableCells().isEmpty();
    }

    // These methods are checking
    // whether the computer or player has won or not
    public boolean hasComputerWon() {
        return checkForWin(COMPUTER);
    }

    public boolean hasPlayerWon() {
        return checkForWin(PLAYER);
    }

    private boolean checkForWin(String player) {
        if (player.equals(board[0][0]) && player.equals(board[1][1]) && player.equals(board[2][2]) ||
                player.equals(board[0][2]) && player.equals(board[1][1]) && player.equals(board[2][0])) {
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            if (player.equals(board[i][0]) && player.equals(board[i][1]) && player.equals(board[i][2]) ||
                    player.equals(board[0][i]) && player.equals(board[1][i]) && player.equals(board[2][i])) {
                return true;
            }
        }
        return false;
    }

    // In this var we will store the computersMove
    private Cell computersMove;

    public Cell getComputersMove() {
        return computersMove;
    }

    // This is our minimax function to calculate
    // the best move for the computer
    public int minimax(int depth, String player) {
        if (hasComputerWon()) return +1;
        if (hasPlayerWon()) return -1;

        List<Cell> availableCells = getAvailableCells();
        if (availableCells.isEmpty()) return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < availableCells.size(); i++) {
            Cell cell = availableCells.get(i);
            if (player.equals(COMPUTER)) {
                placeMove(cell, COMPUTER);
                int currentScore = minimax(depth + 1, PLAYER);
                max = Math.max(currentScore, max);

                if (currentScore >= 0) {
                    if (depth == 0) computersMove = cell;
                }

                if (currentScore == 1) {
                    board[cell.getI()][cell.getJ()] = "";
                    break;
                }

                if (i == availableCells.size() - 1 && max < 0) {
                    if (depth == 0) computersMove = cell;
                }
            } else if (player.equals(PLAYER)) {
                placeMove(cell, PLAYER);
                int currentScore = minimax(depth + 1, COMPUTER);
                min = Math.min(currentScore, min);

                if (min == -1) {
                    board[cell.getI()][cell.getJ()] = "";
                    break;
                }
            }
            board[cell.getI()][cell.getJ()] = "";
        }

        return player.equals(COMPUTER) ? max : min;
    }

    // This function is placing a move in the given cell
    public void placeMove(Cell cell, String player) {
        board[cell.getI()][cell.getJ()] = player;
    }

}




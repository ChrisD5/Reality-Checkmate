package com.example.mob_dev_portfolio.EgoCrush;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mob_dev_portfolio.R;

public class EgoCrushMain extends Fragment {
    private View view;
    private ImageView[][] boardCells = new ImageView[3][3];
    private Board board = new Board();
    private TextView resultTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ego_crush_main, container, false);

        loadBoard();
        resultTextView = view.findViewById(R.id.text_view_result); // initialize here


        Button restartButton = view.findViewById(R.id.button_restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board = new Board();
                resultTextView.setText("");
                mapBoardToUi();
            }
        });

        return view;
    }

    private void loadBoard() {
        GridLayout layoutBoard = view.findViewById(R.id.layout_board);
        for (int i = 0; i < boardCells.length; i++) {
            for (int j = 0; j < boardCells[i].length; j++) {
                boardCells[i][j] = new ImageView(requireContext());

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);
                params.width = 250;
                params.height = 230;
                params.setMargins(5, 5, 5, 5);

                boardCells[i][j].setLayoutParams(params);

                // Check if the sum of the cell's row and column indices is even or odd
                if ((i + j) % 2 == 0) {
                    boardCells[i][j].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorWhite)); // Set to white color
                } else {
                    boardCells[i][j].setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorBlack)); // Set to black color
                }

                boardCells[i][j].setOnClickListener(new CellClickListener(i, j));
                layoutBoard.addView(boardCells[i][j]);
            }
        }
    }


    private void mapBoardToUi() {
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == null) {
                    boardCells[i][j].setImageResource(0);
                    boardCells[i][j].setEnabled(true);
                } else {
                    switch (board.getBoard()[i][j]) {
                        case Board.PLAYER:
                            boardCells[i][j].setImageResource(R.drawable.game_piece_one);
                            boardCells[i][j].setEnabled(false);
                            break;
                        case Board.COMPUTER:
                            boardCells[i][j].setImageResource(R.drawable.game_piece_two);
                            boardCells[i][j].setEnabled(false);
                            break;
                        default:
                            boardCells[i][j].setImageResource(0);
                            boardCells[i][j].setEnabled(true);
                            break;
                    }
                }
            }
        }
    }


    class CellClickListener implements View.OnClickListener {
        private int i;
        private int j;

        public CellClickListener(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public void onClick(View view) {
            if (!board.isGameOver()) {
                Cell cell = new Cell(i, j);
                board.placeMove(cell, Board.PLAYER);
                board.minimax(0, Board.COMPUTER);
                board.placeMove(board.getComputersMove(), Board.COMPUTER);
                mapBoardToUi();

                if (board.hasComputerWon()) {
                    resultTextView.setText(R.string.defeat);
                } else if (board.hasPlayerWon()) {
                    resultTextView.setText(R.string.win);
                } else if (board.isGameOver()) {
                    resultTextView.setText(R.string.lose);
                }
            }
        }
    }
}


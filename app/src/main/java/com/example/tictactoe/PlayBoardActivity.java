package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class PlayBoardActivity extends AppCompatActivity {

    MaterialButton newGameButton, homeButton;
    TextView playersTurn;
    TicTacToeBoard board;
    String[] playersNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_board);

        //New game & home button are not visible before the game ends.
        newGameButton = findViewById(R.id.new_game_button);
        homeButton = findViewById(R.id.home_button);
        newGameButton.setVisibility(View.GONE);
        homeButton.setVisibility(View.GONE);

        playersTurn = findViewById(R.id.turnTV);
        playersNames = getIntent().getStringArrayExtra("Player_names");
        playersTurn.setText(playersNames[0] + "'s Turn");
        playersTurn.setTextColor(ContextCompat.getColor(this, R.color.red));

        board = findViewById(R.id.ticTacToeBoard);
        board.setUpGame(newGameButton, homeButton, playersTurn, playersNames);
    }

    public void newGameButtonOnClick(View view){
        board.resetBoard();
        board.invalidate();
        Toast.makeText(this, "New Game", Toast.LENGTH_SHORT).show();
    }

    public void homeButtonOnClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
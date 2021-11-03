package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText player1, player2;
    Button play_button;
    String name1, name2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        player1 = findViewById(R.id.player1ET);
        player2 = findViewById(R.id.player2ET);
        play_button = findViewById(R.id.play_button);

        player1.addTextChangedListener(playerNameTextWatcher);
        player2.addTextChangedListener(playerNameTextWatcher);
    }

    // Only enabled play button when player1 and player2 name are not null
    private TextWatcher playerNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            name1 = player1.getText().toString().trim();
            name2 = player2.getText().toString().trim();
            play_button.setEnabled(!name1.isEmpty() && !name2.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public void playButtonOnClick(View view){
        Intent intent = new Intent(this, PlayBoardActivity.class);
        intent.putExtra("Player_names", new String[]{name1, name2});
        startActivity(intent);
    }

}
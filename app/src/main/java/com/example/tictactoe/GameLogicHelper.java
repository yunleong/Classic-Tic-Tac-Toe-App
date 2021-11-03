package com.example.tictactoe;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class GameLogicHelper {
    /* Each element in positionArray corresponds to a position on the game board,
       Entry 0 represents O , Entry 1 represents X */
    int[][] positionArray;
    // Player 1 will always go first and take symbol X
    int currentPlayer = 1;
    MaterialButton replay_button, home_button;
    TextView playersTurn;
    String[] playersName;

    GameLogicHelper(){
        positionArray = new int[3][3];
        //initialize each slot of positionArray with 99 to signify "not yet occupied".
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                positionArray[row][column] = 99;
            }
        }
    }

    public int[][] getPositionArray() {
        return positionArray;
    }

    public boolean updatePositionArray(int row, int column){
        //If position is empty, update positionArray with new symbol
        if(positionArray[row-1][column-1]==99){
            positionArray[row-1][column-1] = currentPlayer;
            if(currentPlayer == 1) {
                playersTurn.setText(playersName[1] + "'s Turn");
                playersTurn.setTextColor(Color.parseColor("#3F51B5"));
            }else{
                playersTurn.setText(playersName[0] + "'s Turn");
                playersTurn.setTextColor(Color.parseColor("#C31919"));
            }
            return true;
        }else{
            return false;
        }
    }

    //checkWinner returns true when there's a Winner or Draw, returns false when neither.
    public boolean checkWinner(){
        boolean winner = false;
        //Check horizontal
        for (int row = 0; row < 3 ; row++){
            if(positionArray[row][0] != 99 && (positionArray[row][0] == positionArray[row][1])
                    && (positionArray[row][0] == positionArray[row][2])){
                winner = true;
            }
        }
        //Check vertical
        for (int column = 0; column < 3 ; column++){
            if(positionArray[0][column] != 99 && (positionArray[0][column] == positionArray[1][column])
                    && (positionArray[0][column] == positionArray[2][column])){
                winner = true;
            }
        }
        //Check diagonal
        if(positionArray[0][0] != 99 && (positionArray[0][0] == positionArray[1][1])
                && (positionArray[0][0] == positionArray[2][2])){
            winner = true;
        }
        if(positionArray[2][0] != 99 && (positionArray[2][0] == positionArray[1][1])
                && (positionArray[2][0] == positionArray[0][2])){
            winner = true;
        }
        /*Check for draw. Increment filled every time we see a non-empty slot.
          If all positions are filled, filled = 9*/
        int filled = 0;
        for (int row = 0; row < 3; row++){
            for (int column = 0; column < 3; column++){
                if(positionArray[row][column]!=99){
                    filled++;
                }
            }
        }
        //Change textview to display Winner or Draw
        if(winner){
            int victor;
            String color;
            //Make new game & home button visible
            replay_button.setVisibility(View.VISIBLE);
            home_button.setVisibility(View.VISIBLE);
            /*Change TextView to display winner. currentPlayer already changed to the next turn player
            victor is the index of the name of the winner*/
            if(currentPlayer == 1){
                victor = 0;
                color = "#C31919";
            }else{
                victor = 1;
                color = "#3F51B5";
            }
            playersTurn.setText((playersName[victor]) + " Won !!!");
            playersTurn.setTextColor(Color.parseColor(color));
            return true;
        }else if(filled == 9){
            replay_button.setVisibility(View.VISIBLE);
            home_button.setVisibility(View.VISIBLE);
            //Change TextView to display winner
            playersTurn.setText("Draw !!!");
            playersTurn.setTextColor(Color.parseColor("#674ea7"));
            return true;
        }else{
            return false;
        }
    }


    public void setCurrentPlayer(int player){
        this.currentPlayer = player;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setReplayButton(MaterialButton replay_button) {
        this.replay_button = replay_button;
    }

    public void setHomeButton(MaterialButton home_button) {
        this.home_button = home_button;
    }

    public void setPlayersTurnTextView(TextView playersTurn) {
        this.playersTurn = playersTurn;
    }

    public void setPlayersName(String[] playersName) {
        this.playersName = playersName;
    }

    public void resetPositionArray(){
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                positionArray[row][column] = 99;
            }
        }
        //reset all game variables and hide buttons
        currentPlayer = 1;
        replay_button.setVisibility(View.GONE);
        home_button.setVisibility(View.GONE);
        playersTurn.setText((playersName[0]+"'s Turn"));
        playersTurn.setTextColor(Color.parseColor("#C31919"));
    }

}

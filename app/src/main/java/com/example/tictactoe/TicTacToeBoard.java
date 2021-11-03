package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

public class TicTacToeBoard extends View {

    private final int boardColor, XColor, OColor;
    int cellSize = getWidth()/3;
    private final Paint paint = new Paint();
    private GameLogicHelper game;
    private boolean WinOrDraw = false;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //Initialize our positionArray in GameLogicHelper class to all empty
        game = new GameLogicHelper();
        //get custom board attributes from attrs file
        TypedArray board_attrs = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TicTacToeBoard, 0, 0);

        try{
            boardColor = board_attrs.getInteger(R.styleable.TicTacToeBoard_boardColor,0);
            XColor = board_attrs.getInteger(R.styleable.TicTacToeBoard_XColor,0);
            OColor = board_attrs.getInteger(R.styleable.TicTacToeBoard_OColor,0);
        }finally{
            board_attrs.recycle();
        }
    }

    //Set up dimensions of tic tac toe board
    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);
        //Find the dimension of user's device & form a square using the minimum width or height
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3;
        //This defines the dimensions of our view
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        drawBoard(canvas);
        drawSymbols(canvas);
    }

    //Scan through the board & determine draw X or O
    private void drawSymbols(Canvas canvas) {
        for(int row = 0; row < 3; row++){
            for(int column = 0; column < 3; column++){
                //If position is not occupied, draw the symbol
                if(game.getPositionArray()[row][column] != 99){
                    // 0 represents O, 1 represents X
                    if(game.getPositionArray()[row][column] == 0){
                        drawO(canvas, row, column);
                    }else{
                        drawX(canvas, row, column);
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Get user's tap x, y coordinates
        float x = event.getX();
        float y = event.getY();

        //Check if touch is within the game board
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            //Convert x, y coordinates to rows and columns on game board
            int row = (int) Math.ceil(y / cellSize);
            int column = (int) Math.ceil(x / cellSize);
            /* Redraw X or Y within this new (row,column) on touch
               invalidate() runs onDraw() again.
             */
            if(!WinOrDraw) {
                if (game.updatePositionArray(row, column)) {
                    invalidate();
                    //Check winner every time after a new symbol is placed
                    if(game.checkWinner()){
                        WinOrDraw = true;
                        invalidate();
                    }
                    //Update player's turn
                    if (game.getCurrentPlayer() == 0) {
                        game.setCurrentPlayer(1);
                    } else {
                        game.setCurrentPlayer(0);
                    }
                }
            }
            invalidate();
            return true;
        }
        return false;
    }


    //This method draws the tic tac toe board
    private void drawBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(30);
        for (int column = 1; column < 3; column++){
            canvas.drawLine(cellSize*column, 0, cellSize*column, canvas.getWidth(),paint);
        }

        for (int row = 1; row < 3; row++){
            canvas.drawLine(0, cellSize*row, canvas.getWidth(), cellSize*row,paint);
        }
    }

    private void drawX(Canvas canvas, int row, int column) {
        paint.setColor(XColor);
        paint.setStrokeWidth(30);
        canvas.drawLine((float)((column + 1) * cellSize - cellSize*0.25),
                        (float)(row * cellSize + cellSize*0.25),
                        (float)(column * cellSize + cellSize*0.25),
                        (float)((row + 1) * cellSize - cellSize*0.25),
                        paint);
        canvas.drawLine((float)(column * cellSize +cellSize*0.25),
                        (float)(row * cellSize + cellSize*0.25),
                        (float)((column + 1) * cellSize - cellSize*0.25),
                        (float)((row + 1) * cellSize - cellSize*0.25),
                        paint);
    }

    private void drawO(Canvas canvas, int row, int column){
        paint.setColor(OColor);
        paint.setStrokeWidth(30);
        canvas.drawOval((float)(column*cellSize + cellSize*0.25),
                        (float)(row*cellSize + cellSize*0.25),
                        (float)((column*cellSize+cellSize) - cellSize*0.25),
                        (float)((row*cellSize+cellSize) - cellSize*0.25),
                        paint);
    }

    public void resetBoard(){
        game.resetPositionArray();
        WinOrDraw = false;
    }

    /*Pass widgets to GameLogicHelper. GameLogicHelper needs buttons access to set visibility only
    after a player wins, need textview to change text to show player's turn*/
    public void setUpGame(MaterialButton replay, MaterialButton home, TextView playersTurn, String[] names){
        game.setReplayButton(replay);
        game.setHomeButton(home);
        game.setPlayersTurnTextView(playersTurn);
        game.setPlayersName(names);
    }

}

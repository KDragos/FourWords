package com.kristindragos.fourwords;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayGameActivity extends AppCompatActivity {
    private GameBoard board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        board = new GameBoard();
        board.generateNewBoard();
        SetupBoardInView();
        clearWord();
    }

    public void addToWord(View view) {
        // Get the button's text.
        if(view instanceof Button) {
            Button button = (Button) view;

            // Access the current word.
            TextView currentWord = (TextView) findViewById(R.id.txt_current_word);

            // Add to the current word and set it as the new.
            currentWord.setText(currentWord.getText().toString() + button.getText().toString());
        }
    }

    public void scoreWord(View view) {
        TextView currentWord = (TextView) findViewById(R.id.txt_current_word);
        Toast.makeText(getApplicationContext(), currentWord.getText(), Toast.LENGTH_SHORT).show();

        // TODO: Call to dictionary to see if it's a word.
        // TODO: Score points based on length.

        currentWord.setText("");
    }

    private void clearWord() {
        TextView currentWord = (TextView) findViewById(R.id.txt_current_word);
        currentWord.setText("");
    }

    public void clearWord(View view) {
        clearWord();
    }

//    Setup the view to match the board.
    private void SetupBoardInView() {
//      Setup Row 1
        Button c1r1 = (Button) findViewById(R.id.btn_gamecube1_1);
        c1r1.setText(board.getCubeValueAt(1, 1));
        Button c2r1 = (Button) findViewById(R.id.btn_gamecube2_1);
        c2r1.setText(board.getCubeValueAt(2, 1));
        Button c3r1 = (Button) findViewById(R.id.btn_gamecube3_1);
        c3r1.setText(board.getCubeValueAt(3, 1));
        Button c4r1 = (Button) findViewById(R.id.btn_gamecube4_1);
        c4r1.setText(board.getCubeValueAt(4, 1));

//      Setup Row 2
        Button c1r2 = (Button) findViewById(R.id.btn_gamecube1_2);
        c1r2.setText(board.getCubeValueAt(1, 2));
        Button c2r2 = (Button) findViewById(R.id.btn_gamecube2_2);
        c2r2.setText(board.getCubeValueAt(2, 2));
        Button c3r2 = (Button) findViewById(R.id.btn_gamecube3_2);
        c3r2.setText(board.getCubeValueAt(3, 2));
        Button c4r2 = (Button) findViewById(R.id.btn_gamecube4_2);
        c4r2.setText(board.getCubeValueAt(4, 2));

//      Setup Row 3
        Button c1r3 = (Button) findViewById(R.id.btn_gamecube1_3);
        c1r3.setText(board.getCubeValueAt(1, 3));
        Button c2r3 = (Button) findViewById(R.id.btn_gamecube2_3);
        c2r3.setText(board.getCubeValueAt(2, 3));
        Button c3r3 = (Button) findViewById(R.id.btn_gamecube3_3);
        c3r3.setText(board.getCubeValueAt(3, 3));
        Button c4r3 = (Button) findViewById(R.id.btn_gamecube4_3);
        c4r3.setText(board.getCubeValueAt(4, 3));

//      Setup Row 4
        Button c1r4 = (Button) findViewById(R.id.btn_gamecube1_4);
        c1r4.setText(board.getCubeValueAt(1, 4));
        Button c2r4 = (Button) findViewById(R.id.btn_gamecube2_4);
        c2r4.setText(board.getCubeValueAt(2, 4));
        Button c3r4 = (Button) findViewById(R.id.btn_gamecube3_4);
        c3r4.setText(board.getCubeValueAt(3, 4));
        Button c4r4 = (Button) findViewById(R.id.btn_gamecube4_4);
        c4r4.setText(board.getCubeValueAt(4, 4));
    }

    // TODO: Setup OnTouchListener methods for each button so it can detect swipes.

}

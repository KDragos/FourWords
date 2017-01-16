package com.kristindragos.fourwords;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PlayGameActivity extends AppCompatActivity {
    private static String TAG = "PlayGameActivity";
    private GameBoard board;
    private ArrayList<String> previousWords;
    private ArrayAdapter<String> adapter;
    private DictionaryDelegate delegate;
    private TextView timerText;
    private CountDownTimer timer;
    private long timeLeft = 0;
    private final long threeMinuteTimer = 180000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        timerText = (TextView) findViewById(R.id.tv_timer);
        board = new GameBoard();
        board.generateNewBoard();
        SetupBoardInView();
        clearWord();
        setUpTrackingWords();
        setupTimer(threeMinuteTimer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Continue timer.
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Create new timer with time left.
        setupTimer(timeLeft);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancel timer
        timer.cancel();
    }

    private void setupTimer(long totalTimeRemaining) {
        timer = new CountDownTimer(totalTimeRemaining, 1000) {
            @Override
            public void onTick(long timeRemaining) {
                timeLeft = timeRemaining;
                long minutes = timeRemaining / 1000 / 60;

                String convertedTime = String.format("%01d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeRemaining) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeRemaining)),
                        TimeUnit.MILLISECONDS.toSeconds(timeRemaining) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeRemaining)));
                timerText.setText(convertedTime);
            }

            @Override
            public void onFinish() {
                timerText.setText("Done!");
                endOfGame();
            }
        }.start();
    }

    private void endOfGame() {
        previousWords = delegate.getWordList();
        int gameScore = 0;
        for (String word: previousWords) {
            gameScore += getScore(word);
        }
        Log.v(TAG, "Your final score: " + String.valueOf(gameScore));
        Toast.makeText(getApplicationContext(), "Final score: " + String.valueOf(gameScore), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, FinalGameScoreActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("playerScore", String.valueOf(gameScore));
        bundle.putStringArrayList("playerWords", previousWords);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private int getScore(String word) {
        if (word.length() == 3 || word.length() == 4) {
            return 1;
        }
        if (word.length() == 5) {
            return 2;
        }
        if (word.length() == 6) {
            return 3;
        }
        if (word.length() == 7) {
            return 4;
        }
        if (word.length() >= 8) {
            return 11;
        }
        return 0;
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
        // Only works if the text entered actually contains data.
        if (!currentWord.getText().toString().isEmpty()) {

            // Call to dictionary to see if it's a word.
            delegate.isInDictionary(currentWord.getText().toString(), adapter, previousWords);

            if ((delegate.getWordList().size() > 0) && (delegate.getWordList().size() % 4 == 0)) {
                board.generateNewBoard();
                SetupBoardInView();
            }

            // TODO: Score points based on length.

            currentWord.setText("");
        }
    }

    private void setUpTrackingWords(){
        previousWords = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.previous_words, R.id.previous_words_textView, previousWords);
        ListView previousWordsListView = (ListView) findViewById(R.id.lv_previous_words);
        previousWordsListView.setAdapter(adapter);
        delegate = new DictionaryDelegate();

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

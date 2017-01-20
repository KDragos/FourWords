package com.kristindragos.fourwords;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
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
//    private long threeMinuteTimer = 180000;
    private long threeMinuteTimer = 90000;

    private ArrayList<String> bestWords;
    private int lengthOfBestWords = 0;
    private ArrayList<View> allButtons;
    private int numOfNotAWord = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        timerText = (TextView) findViewById(R.id.tv_timer);
        board = new GameBoard();
        bestWords = new ArrayList<>();
        setUpNewGame();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Continue timer.
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (getIntent().getExtras() != null) {
            if(getIntent().hasExtra("restart")) {
                setUpNewGame();
            }
        } else {
            // Create new timer with time left.
            setupTimer(timeLeft);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Cancel timer
        timer.cancel();
    }

    private void setUpNewGame() {
        board.generateNewBoard();
        setupBoardInView();
        clearWord();
        setUpTrackingWords();
        setupTimer(threeMinuteTimer);
    }

    private void setupTimer(long totalTimeRemaining) {
        timer = new CountDownTimer(totalTimeRemaining, 1000) {
            @Override
            public void onTick(long timeRemaining) {
                timeLeft = timeRemaining;
                long minutes = timeRemaining / 1000 / 60;

                String convertedTime;
                convertedTime = String.format(Locale.getDefault(), "%01d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(timeRemaining) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeRemaining)),
                        TimeUnit.MILLISECONDS.toSeconds(timeRemaining) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeRemaining)));
                timerText.setText(convertedTime);
            }

            @Override
            public void onFinish() {
                timerText.setText(R.string.timer_done);
                endOfGame();
            }
        }.start();
    }

    private void endOfGame() {
        previousWords = delegate.getWordList();
        int gameScore = 0;
        for (String word: previousWords) {
            checkForBestWord(word);
            gameScore += getScore(word);
        }
        gameScore -= delegate.getNumberOfNonWords();
        Intent intent = new Intent(this, FinalGameScoreActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("playerScore", String.valueOf(gameScore));
        bundle.putStringArrayList("playerWords", previousWords);
        bundle.putStringArrayList("bestWords", bestWords);
        bundle.putInt("lengthOfBestWords", lengthOfBestWords);
        bundle.putInt("numberOfNonWords", delegate.getNumberOfNonWords());
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void checkForBestWord(String word) {
        if(word.length() == lengthOfBestWords) {
            bestWords.add(word);
            return;
        }
        if(word.length() > lengthOfBestWords) {
            bestWords = new ArrayList<>();
            bestWords.add(word);
            lengthOfBestWords = word.length();
        }
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

    // Adds a letter to the current word.
    public void addToWord(View view) {
        // Get the button's text.
        if(view instanceof Button) {
            Button button = (Button) view;

            // Access the current word.
            TextView currentWord = (TextView) findViewById(R.id.txt_current_word);

            // Add to the current word and set it as the new.
            currentWord.setText(currentWord.getText().toString().concat(button.getText().toString()));
//            Snackbar.make(findViewById(android.R.id.content), "Had a snack at Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            // Disable Button.
            button.setEnabled(false);
        }
    }

    public void scoreWord(View view) {
        TextView currentWord = (TextView) findViewById(R.id.txt_current_word);
        // Only works if the text entered actually contains data and has at least 3 characters.
        if (!currentWord.getText().toString().isEmpty() && currentWord.getText().toString().length() >= 3) {

            // Call to dictionary to see if it's a word.
            delegate.isInDictionary(currentWord.getText().toString(), adapter, previousWords, findViewById(android.R.id.content));

            resetButtons();
            currentWord.setText("");
        }
    }

    public void checkFourWords() {
        if ((delegate.getWordList().size() > 0) && (delegate.getWordList().size() % 4 == 0)) {
            board.generateNewBoard();
            setupBoardInView();
            resetButtons();
        }
    }

    private void setUpTrackingWords(){
        previousWords = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.previous_words, R.id.previous_words_textView, previousWords);
        ListView previousWordsListView = (ListView) findViewById(R.id.lv_previous_words);
        previousWordsListView.setAdapter(adapter);
        delegate = new DictionaryDelegate(PlayGameActivity.this);

    }

    private void clearWord() {
        TextView currentWord = (TextView) findViewById(R.id.txt_current_word);
        resetButtons();
        currentWord.setText("");
    }

    public void clearWord(View view) {
        resetButtons();
        clearWord();
    }

    private void resetButtons() {
        for (int i = 0; i < allButtons.size(); i++) {
            allButtons.get(i).setEnabled(true);
        }
    }

//    Setup the view to match the board.
    public ArrayList<View> setupBoardInView() {

        // Adding each button to the allButtons list individually,
        // prevents disabled buttons from being removed from the list.
        allButtons = new ArrayList<>();

//      Setup Row 1
        Button c1r1 = (Button) findViewById(R.id.btn_gamecube1_1);
        c1r1.setText(board.getCubeValueAt(1, 1));
        allButtons.add(c1r1);
        Button c2r1 = (Button) findViewById(R.id.btn_gamecube2_1);
        c2r1.setText(board.getCubeValueAt(2, 1));
        allButtons.add(c2r1);
        Button c3r1 = (Button) findViewById(R.id.btn_gamecube3_1);
        c3r1.setText(board.getCubeValueAt(3, 1));
        allButtons.add(c3r1);
        Button c4r1 = (Button) findViewById(R.id.btn_gamecube4_1);
        c4r1.setText(board.getCubeValueAt(4, 1));
        allButtons.add(c4r1);

//      Setup Row 2
        Button c1r2 = (Button) findViewById(R.id.btn_gamecube1_2);
        c1r2.setText(board.getCubeValueAt(1, 2));
        allButtons.add(c1r2);
        Button c2r2 = (Button) findViewById(R.id.btn_gamecube2_2);
        c2r2.setText(board.getCubeValueAt(2, 2));
        allButtons.add(c2r2);
        Button c3r2 = (Button) findViewById(R.id.btn_gamecube3_2);
        c3r2.setText(board.getCubeValueAt(3, 2));
        allButtons.add(c3r2);
        Button c4r2 = (Button) findViewById(R.id.btn_gamecube4_2);
        c4r2.setText(board.getCubeValueAt(4, 2));
        allButtons.add(c4r2);

//      Setup Row 3
        Button c1r3 = (Button) findViewById(R.id.btn_gamecube1_3);
        c1r3.setText(board.getCubeValueAt(1, 3));
        allButtons.add(c1r3);
        Button c2r3 = (Button) findViewById(R.id.btn_gamecube2_3);
        c2r3.setText(board.getCubeValueAt(2, 3));
        allButtons.add(c2r3);
        Button c3r3 = (Button) findViewById(R.id.btn_gamecube3_3);
        c3r3.setText(board.getCubeValueAt(3, 3));
        allButtons.add(c3r3);
        Button c4r3 = (Button) findViewById(R.id.btn_gamecube4_3);
        c4r3.setText(board.getCubeValueAt(4, 3));
        allButtons.add(c4r3);

//      Setup Row 4
        Button c1r4 = (Button) findViewById(R.id.btn_gamecube1_4);
        c1r4.setText(board.getCubeValueAt(1, 4));
        allButtons.add(c1r4);
        Button c2r4 = (Button) findViewById(R.id.btn_gamecube2_4);
        c2r4.setText(board.getCubeValueAt(2, 4));
        allButtons.add(c2r4);
        Button c3r4 = (Button) findViewById(R.id.btn_gamecube3_4);
        c3r4.setText(board.getCubeValueAt(3, 4));
        allButtons.add(c3r4);
        Button c4r4 = (Button) findViewById(R.id.btn_gamecube4_4);
        c4r4.setText(board.getCubeValueAt(4, 4));
        allButtons.add(c4r4);

        return allButtons;

    }

    // TODO: Setup OnTouchListener methods for each button so it can detect swipes.

}

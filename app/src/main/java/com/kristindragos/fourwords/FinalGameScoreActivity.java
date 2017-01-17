package com.kristindragos.fourwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalGameScoreActivity extends AppCompatActivity {
    private static String TAG = "FinalGameScoreActivity";
    private String playerScore = "0";
    private ArrayList<String> playerWords = new ArrayList<>();
    private ArrayAdapter<String> playerAdapter;

    private String opponentScore = "10";
    private ArrayList<String> opponentWords = new ArrayList<>();
    private ArrayAdapter<String> opponentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_game_score);
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if(!extrasBundle.isEmpty()) {
            boolean hasScore = extrasBundle.containsKey("playerScore");
            boolean hasWords = extrasBundle.containsKey("playerWords");
            // Add checks for opponent data.
            if (hasScore) {
                this.playerScore = (String) extrasBundle.get("playerScore");
            }
            if (hasWords) {
                this.playerWords = (ArrayList<String>) extrasBundle.get("playerWords");
            }
        }
        updateUI();
    }

    public void playAgain(View view) {
        Log.v(TAG, "You want to play again!");
        Intent intent = new Intent(FinalGameScoreActivity.this, PlayGameActivity.class);
        intent.putExtra("restart", true);
        startActivity(intent);
    }

    private void updateUI() {
        TextView playerScoreText = (TextView) findViewById(R.id.tv_your_score);
        playerScoreText.setText(playerScore);

        ListView playerWordListView = (ListView) findViewById(R.id.lv_your_words);
        // TODO: Eventually we'll change this from the "Previous Words" layout to a final screen layout with the score by the word in the list.
        playerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.previous_words, R.id.previous_words_textView, playerWords);
        playerWordListView.setAdapter(playerAdapter);
        playerAdapter.notifyDataSetChanged();

        prepopulateOpponentWords();
        TextView opponentScoreText = (TextView) findViewById(R.id.tv_opponent_score);
        opponentScoreText.setText(opponentScore);
        ListView opponentWordListView = (ListView) findViewById(R.id.lv_opponent_words);
        // TODO: Eventually we'll change this from the "Previous Words" layout to a final screen layout with the score by the word in the list.
        opponentAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.previous_words, R.id.previous_words_textView, opponentWords);
        opponentWordListView.setAdapter(opponentAdapter);
        playerAdapter.notifyDataSetChanged();

        determineWinOrLose();
    }

    private void determineWinOrLose() {
        String gameResult = "";
        if (Integer.parseInt(playerScore) > Integer.parseInt(opponentScore)) {
            gameResult = "WIN!";
        }
        if (Integer.parseInt(playerScore) < Integer.parseInt(opponentScore)) {
            gameResult = "LOSE!";
        }
        if (Integer.parseInt(playerScore) == Integer.parseInt(opponentScore)) {
            gameResult = "TIE!;";
        }

        TextView resultText = (TextView) findViewById(R.id.tv_result);
        resultText.setText(gameResult);

    }

    // TODO: Remove this when we hook up ability to play against an opponent.
    private void prepopulateOpponentWords() {
        opponentWords.add("blah");
        opponentWords.add("blah");
        opponentWords.add("blah");
        opponentWords.add("blah");
        opponentWords.add("blah");
        opponentWords.add("blah");
    }
}

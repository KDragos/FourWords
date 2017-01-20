package com.kristindragos.fourwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FinalGameScoreActivity extends AppCompatActivity {
    private static String TAG = "FinalGameScoreActivity";
    private String playerScore = "0";
    private ArrayList<String> playerWords = new ArrayList<>();
    private ArrayList<String> bestWords = new ArrayList<>();
    private ArrayAdapter<String> playerAdapter;
    private int lengthOfBestWords = 0;
    private int numNonWords = 0;

    private static final String SHARE_HASHTAG = "#FourWords";
    private String shareStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_game_score);
        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if(!extrasBundle.isEmpty()) {
            boolean hasScore = extrasBundle.containsKey("playerScore");
            boolean hasWords = extrasBundle.containsKey("playerWords");
            boolean hasBestWords = extrasBundle.containsKey("bestWords");
            boolean hasLengthOfBestWords = extrasBundle.containsKey("lengthOfBestWords");
            boolean hasNumNonWords = extrasBundle.containsKey("numberOfNonWords");

            if (hasScore) {
                this.playerScore = (String) extrasBundle.get("playerScore");
            }
            if (hasWords) {
                this.playerWords = (ArrayList<String>) extrasBundle.get("playerWords");
            }
            if(hasBestWords) {
                this.bestWords = (ArrayList<String>) extrasBundle.get("bestWords");
            }
            if(hasLengthOfBestWords) {
                this.lengthOfBestWords = (int) extrasBundle.get("lengthOfBestWords");
            }
            if(hasNumNonWords) {
                this.numNonWords = (int) extrasBundle.get("numberOfNonWords");
            }

        }
        updateUI();
        setupSharing();
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(createShareGameIntent());
        } else {
            Log.d(TAG, "Share Action Provider is null");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d(TAG, item.getTitle().toString());
        if (id == R.id.action_share) {
            createShareGameIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void playAgain(View view) {
        Intent intent = new Intent(FinalGameScoreActivity.this, PlayGameActivity.class);
        intent.putExtra("restart", true);
        startActivity(intent);
    }

    private void setupSharing() {
        shareStr = "I just scored " + playerScore + " points in FourWords. Think you can get a better score? ";

    }

    private Intent createShareGameIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareStr + SHARE_HASHTAG);
        return shareIntent;
    }

    private void updateUI() {
        TextView playerScoreText = (TextView) findViewById(R.id.tv_your_score);
        playerScoreText.setText(playerScore);

        ListView playerWordListView = (ListView) findViewById(R.id.lv_your_words);
        // TODO: Eventually we'll change this from the "Previous Words" layout to a final screen layout with the score by the word in the list.
        playerAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.previous_words, R.id.previous_words_textView, playerWords);
        playerWordListView.setAdapter(playerAdapter);
        playerAdapter.notifyDataSetChanged();

        TextView nonWordCount = (TextView) findViewById(R.id.tv_your_non_word_count);
        nonWordCount.setText(String.valueOf(numNonWords));

        TextView bestWordsText = (TextView) findViewById(R.id.tv_your_best_word_score);
        String words = "";
        Log.v(TAG, bestWords.toString());
        if (bestWords.size() == 1) {
            words += bestWords.get(0).toString();
        } else {
            for (int i = 0; i < this.bestWords.size(); i++) {
                words += bestWords.get(i).toString();
                if(i < bestWords.size() && i+1 != bestWords.size()) {
                    words += ", ";
                }
            }
        }
        bestWordsText.setText(words);

    }

}

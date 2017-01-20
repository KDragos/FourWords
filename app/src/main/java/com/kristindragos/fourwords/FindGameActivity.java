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

public class FindGameActivity extends AppCompatActivity {
    private static final String TAG = "FindGameActivity";
    private static String SHARE_HASHTAG = "#FourWords";
    private String shareStr = "Think your word creation skills are better than mine? Play FourWords to find out! ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

    }

    public void startSoloGame(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);

    }

    public void showRules(View view) {
        Intent intent = new Intent(this, ShowRulesActivity.class);
        startActivity(intent);
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
            startActivity(createShareGameIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private Intent createShareGameIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        Log.d(TAG, "Creating share intent.");
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareStr + SHARE_HASHTAG);
        return shareIntent;
    }
}

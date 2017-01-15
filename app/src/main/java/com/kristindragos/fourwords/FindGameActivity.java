package com.kristindragos.fourwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class FindGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_game);

    }

    public void startSoloGame(View view) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        startActivity(intent);

    }

    public void startRandomGame(View view) {
        Log.d("FindGameActivity", "Starting a random game.");
    }
}

package com.kristindragos.fourwords;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void startGame(View view) {
        Log.d(TAG, "Starting game.");
        Intent intent = new Intent(this, FindGameActivity.class);
        startActivity(intent);

    }

    public void viewHistory(View view) {
        Log.d(TAG, "Viewing History");

    }

}

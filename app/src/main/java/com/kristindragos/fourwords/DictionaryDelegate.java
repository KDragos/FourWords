package com.kristindragos.fourwords;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kristin on 1/14/17.
 *
 * DictionaryDelegate handles the dictionary API connection, call, and response.
 */

public class DictionaryDelegate extends AppCompatActivity {
    private static String TAG = "DictionaryDelegate";
    protected ArrayAdapter adapter;
    protected ArrayList<String> previousWords;
    private int numberOfNonWords = 0;
    private View viewForMessaging;
    private PlayGameActivity activity;

    public DictionaryDelegate(PlayGameActivity act) {
        this.activity = act;
    }

    public void isInDictionary(String word, ArrayAdapter adapter, ArrayList<String> previousWords, View view) {
        this.adapter = adapter;
        this.previousWords = previousWords;
        viewForMessaging = view;
        FetchDictionaryDataTask dictionaryDataTask = new FetchDictionaryDataTask();
        dictionaryDataTask.execute(word);

    }

    public class FetchDictionaryDataTask extends AsyncTask<String, Object, Double> {
        private final String TAG = FetchDictionaryDataTask.class.getSimpleName();
        private URL url;
        private BufferedReader reader = null;
        private HttpURLConnection urlConnection = null;
        private String dictionaryResponse = null;
        private String currentWord;

        @Override
        protected Double doInBackground(String... strings) {
            currentWord = strings[0];
            Double numEntries = 0d;
            try {
                final String DICTIONARY_BASE_URL = "http://api.pearson.com/v2/dictionaries/entries?";
                final String HEADWORD_PARAM = "headword";
                Uri builtUri = Uri.parse(DICTIONARY_BASE_URL).buildUpon().appendQueryParameter(HEADWORD_PARAM, currentWord).build();
                url = new URL(builtUri.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect(); // Can't do this on the main thread.


                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
//                nothing to do
                    Log.d(TAG, "Input stream was null.");
                    return numEntries;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");

                }
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    Log.d(TAG, "Stream was empty.");
                    return numEntries;
                }

                dictionaryResponse = buffer.toString();
            } catch (IOException e) {
                Log.e(TAG, "Error", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }

                }
            }
            try {
                return getNumberOfDictionaryEntries(dictionaryResponse);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return numEntries;
        }

        private double getNumberOfDictionaryEntries(String dictionaryResponse) throws JSONException {
            JSONObject entries = new JSONObject(dictionaryResponse);
            return entries.getDouble("total");
        }

        @Override
        protected void onPostExecute(Double result) {
            if (result > 0 && !previousWords.contains(currentWord)) {
                previousWords.add(currentWord);
                adapter.notifyDataSetChanged();

                activity.checkFourWords();
            }
            if (result == 0) {
                numberOfNonWords++;
                createErrorMessage(currentWord);
            }

        }

    }

    public void addWordToList(String currentWord) {
        previousWords.add(currentWord);
        adapter.notifyDataSetChanged();
    }

    public ArrayList<String> getWordList() {
        return this.previousWords;
    }

    public int getNumberOfNonWords() {
        return this.numberOfNonWords;
    }

    public void createErrorMessage(String currentWord) {
        Snackbar.make(viewForMessaging, currentWord + " is not in the dictionary.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}

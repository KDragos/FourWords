package com.kristindragos.fourwords;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 */

public class DictionaryDelegate extends AppCompatActivity {
    private static String TAG = "DictionaryDelegate";
    protected ArrayAdapter adapter;
    protected ArrayList<String> previousWords;

    public boolean setupConnection(String word) throws IOException {

        return true;
    }

    private void readStream(InputStream in) {

        // Do something here.
    }

    public void isInDictionary(String word, ArrayAdapter adapter, ArrayList<String> previousWords) {
        this.adapter = adapter;
        this.previousWords = previousWords;
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
            currentWord = strings[0].toString();
            Double numEntries = new Double(0);
            try {
                final String DICTIONARY_BASE_URL = "http://api.pearson.com/v2/dictionaries/entries?";
                final String HEADWORD_PARAM = "headword";
                Uri builtUri = Uri.parse(DICTIONARY_BASE_URL).buildUpon().appendQueryParameter(HEADWORD_PARAM, currentWord).build();
                url = new URL(builtUri.toString());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect(); // Can't do this on the main thread.


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
//                nothing to do
                    Log.d(TAG, "Input stream was null.");
                    return numEntries;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

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
                return new Double(getNumberOfDictionaryEntries(dictionaryResponse));
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return numEntries;
        }

        private double getNumberOfDictionaryEntries(String dictionaryResponse) throws JSONException {
            JSONObject entries = new JSONObject(dictionaryResponse);
            double numEntries = entries.getDouble("total");
            return numEntries;
        }

        @Override
        protected void onPostExecute(Double result) {
            if (result > 0 && !previousWords.contains(currentWord)) {
                previousWords.add(currentWord);
                adapter.notifyDataSetChanged();
            }
        }

    }

    public void addWordToList(String currentWord) {
        previousWords.add(currentWord);
        adapter.notifyDataSetChanged();
    }


}

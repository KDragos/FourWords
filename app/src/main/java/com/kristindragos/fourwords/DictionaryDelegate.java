package com.kristindragos.fourwords;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kristin on 1/14/17.
 */

public class DictionaryDelegate {
    private URL url;

    public void setupConnection() throws IOException {
        try {
            url = new URL("https://api.pearson.com/v2/dictionaries/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        } finally {
            urlConnection.disconnect();
        }

    }

    private void readStream(InputStream in) {

        // Do something here.
    }
}

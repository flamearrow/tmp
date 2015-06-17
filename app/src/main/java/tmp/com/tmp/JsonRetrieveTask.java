package tmp.com.tmp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class JsonRetrieveTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = "JsonRetrieveTask";

    final String mUrl;

    public JsonRetrieveTask(String url) {
        mUrl = url;
    }

    protected String doInBackground(Void... args) {
        StringBuilder result = new StringBuilder();
        try {
            final URL url = new URL(mUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            final int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                final BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }
            } else {
                logWarning();
            }
        } catch (IOException e) {
            logWarning();
        }
        return result.toString();
    }

    protected void onPostExecute(String result) {
        onJsonResult(result);
    }

    protected abstract void onJsonResult(String result);

    private void logWarning() {
        Log.w(TAG, "Unable to retrieve JSON");
    }
}

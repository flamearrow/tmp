package tmp.com.tmp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonRetrieveTask extends AsyncTask<Void, Void, String> {
    final String mUrl;
    final ResultListener mResultListener;

    public JsonRetrieveTask(String url, ResultListener resultListener) {
        mUrl = url;
        mResultListener = resultListener;
    }

    protected String doInBackground(Void... args) {
        StringBuilder result = new StringBuilder();
        try {
            // open the connection

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
            }
        } catch (IOException e) {
        }
        return result.toString();
    }

    protected void onPostExecute(String result) {
        mResultListener.onJsonResult(result);
    }

    interface ResultListener {
        void onJsonResult(String result);
    }
}

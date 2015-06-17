package tmp.com.tmp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class IssueManager {
    private static String ISSUE_URL = "https://api.github.com/repos/crashlytics/secureudid/issues";

    public static void retrieveIssues(ResultListener resultListener) {
        new RetrieveTask(resultListener).execute();
    }

    interface ResultListener {
        void onIssueResult(ArrayList<Issue> issues);
    }

    private static class RetrieveTask extends AsyncTask<Void, Void, ArrayList<Issue>> {
        ResultListener mResultListener;

        public RetrieveTask(ResultListener resultListener) {
            mResultListener = resultListener;
        }

        protected ArrayList<Issue> doInBackground(Void... args) {
            ArrayList<Issue> result = new ArrayList<>();

            Calendar updated = Calendar.getInstance();

            try {
                URL url = new URL(ISSUE_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                connection.connect();
                int responseCide = connection.getResponseCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = br.readLine()) != null /*&& br.ready()*/) {
                    sb.append(line + "\r\n");
                }

                String rawJson = sb.toString();


                parseIssues(rawJson, result);

                Log.d("...", rawJson);

            } catch (IOException e) {
            }
            return result;
        }

        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(ArrayList<Issue> result) {
            mResultListener.onIssueResult(result);
        }

        private void parseIssues(String rawJson, ArrayList<Issue> issues) {
            try {
                JSONArray ja = new JSONArray(rawJson);
                for (int i = 0; i < ja.length(); i++) {
                    JSONObject jo = ja.getJSONObject(i);

                    String updatedAtString = jo.getString("updated_at").replaceAll("Z$", "+0000");


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
                    //sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date date = null;
                    try {
                        date = sdf.parse(updatedAtString);
                    } catch (ParseException e) {
                        Log.d("...", e.toString());
                    }

                    Calendar updated = new GregorianCalendar();
                    updated.setTime(date);

                    String title = jo.getString("title");
                    String body = jo.getString("body");
                    String url = jo.getString("comments_url");

                    final Issue issue = new Issue(updated, title, body, url);
                    issues.add(issue);
                }
            } catch (JSONException e) {
            }
        }
    }
}

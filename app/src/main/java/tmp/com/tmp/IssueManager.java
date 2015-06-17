package tmp.com.tmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IssueManager {
    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

    public static void retrieveIssues(final ResultListener resultListener) {
        final String issuesUrl = "https://api.github.com/repos/crashlytics/secureudid/issues";
        new JsonRetrieveTask(issuesUrl) {
            @Override
            public void onJsonResult(String result) {
                final ArrayList<Issue> issues = new ArrayList<>();
                try {
                    parseIssues(result, issues);
                } catch (JSONException|ParseException ignore) {
                }
                resultListener.onIssueResult(issues);
            }
        }.execute();
    }

    interface ResultListener {
        void onIssueResult(ArrayList<Issue> issues);
    }

    private static void parseIssues(String rawJson, ArrayList<Issue> issues) throws JSONException, ParseException {
        final JSONArray array = new JSONArray(rawJson);
        final int arrayLength = array.length();
        for (int arrayIndex = 0; arrayIndex < arrayLength; arrayIndex++) {
            final JSONObject object = array.getJSONObject(arrayIndex);

            // java does not handle ISO 8601 properly, so replace Z if necessary
            final String updatedAtString = object.getString("updated_at").replaceAll("Z$", "+0000");
            final Date updatedAtDate = DATE_FORMAT.parse(updatedAtString);
            final Calendar updatedAt = Calendar.getInstance();
            updatedAt.setTime(updatedAtDate);

            final String title = object.getString("title");

            final String body = object.getString("body");

            final String url = object.getString("comments_url");

            issues.add(new Issue(updatedAt, title, body, url));
        }
    }

    private static Issue parseIssue(JSONObject object) throws JSONException, ParseException {
        // java does not handle ISO 8601 properly, so replace Z if necessary
        final String updatedAtString = object.getString("updated_at").replaceAll("Z$", "+0000");
        final Date updatedAtDate = DATE_FORMAT.parse(updatedAtString);
        final Calendar updatedAt = Calendar.getInstance();
        updatedAt.setTime(updatedAtDate);

        final String title = object.getString("title");

        final String body = object.getString("body");

        final String url = object.getString("comments_url");

        return new Issue(updatedAt, title, body, url);
    }
}


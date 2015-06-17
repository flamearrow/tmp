package tmp.com.tmp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommentManager {
    private static final String TAG = "CommentManager";
    private static final Map<String, ArrayList<Comment>> sCommentsMap = new ConcurrentHashMap<>();

    public static void retrieveComments(Issue issue, final ResultListener resultListener) {
        final String commentsUrl = issue.getCommentsUrl();

        final ArrayList<Comment> comments = sCommentsMap.get(commentsUrl);
        if (comments != null) {
            resultListener.onCommentsResult(comments);
            return;
        }

        new JsonRetrieveTask(issue.getCommentsUrl()) {
            @Override
            public void onJsonResult(String result) {
                final ArrayList<Comment> comments = new ArrayList<>();
                try {
                    parseComments(result, comments);
                    if (!sCommentsMap.containsKey(commentsUrl)) {
                        sCommentsMap.put(commentsUrl, comments);
                    }
                } catch (JSONException ignore) {
                    Log.w(TAG, "Unable to parse comments");
                }
                resultListener.onCommentsResult(comments);
            }
        }.execute();
    }

    private static void parseComments(String rawJson, ArrayList<Comment> comments) throws JSONException {
        final JSONArray array = new JSONArray(rawJson);
        for (int arrayIndex = 0; arrayIndex < array.length(); arrayIndex++) {
            final JSONObject object = array.getJSONObject(arrayIndex);

            final String name = object.getJSONObject("user").getString("login");
            final String body = object.getString("body");

            comments.add(new Comment(name, body));
        }
    }

    interface ResultListener {
        void onCommentsResult(ArrayList<Comment> comments);
    }
}

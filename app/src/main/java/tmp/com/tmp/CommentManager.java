package tmp.com.tmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class CommentManager {
    public static void retrieveComments(Issue issue, final ResultListener resultListener) {
        new JsonRetrieveTask(issue.getCommentsUrl()) {
                    @Override
                    public void onJsonResult(String result) {
                        final ArrayList<Comment> comments = new ArrayList<>();
                        try {
                            parseComments(result, comments);
                        } catch (JSONException |ParseException ignore) {
                        }
                        resultListener.onCommentsResult(comments);
                    }
                }.execute();
    }

    private static void parseComments(String rawJson, ArrayList<Comment> comments) throws JSONException, ParseException {
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

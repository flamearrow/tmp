package tmp.com.tmp;

import java.util.ArrayList;

public class CommentManager {
    public static void retrieveComments(Issue issue, ResultListener resultListener) {
    }

    interface ResultListener {
        void onCommentsResult(ArrayList<Comment> comments);
    }
}

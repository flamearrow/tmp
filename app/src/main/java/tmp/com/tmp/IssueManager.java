package tmp.com.tmp;

import java.util.ArrayList;

public class IssueManager {
    public static void retrieveIssues(ResultListener resultListener) {
    }

    interface ResultListener {
        void onIssueResult(ArrayList<Issue> issues);
    }
}

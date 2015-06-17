package tmp.com.tmp;

import java.util.Calendar;

public class Issue implements Comparable<Issue> {

    private final Calendar mUpdated;

    private final String mTitle;

    private final String mBody;

    private final String mCommentsUrl;

    public Issue(Calendar updated, String title, String body, String commentsUrl) {
        mUpdated = updated;
        mTitle = title;
        mBody = body;
        mCommentsUrl = commentsUrl;
    }

    public Calendar getUpdated() {
        return mUpdated;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getCommentsUrl() {
        return mCommentsUrl;
    }

    @Override
    public int compareTo(Issue another) {
        if (mUpdated.before(another)) {
            return -1;
        } else if (mUpdated.after(another)) {
            return 1;
        } else {
            return 0;
        }
    }
}

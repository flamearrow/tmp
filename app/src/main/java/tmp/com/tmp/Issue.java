package tmp.com.tmp;

import java.util.Calendar;

public class Issue {
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
}

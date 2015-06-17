package tmp.com.tmp;

import java.util.Calendar;

public class Issue {
    private Calendar mUpdated;
    private String mTitle;
    private String mBody;
    private String mCommentsUrl;

    public Issue(Calendar updated, String title, String body, String commentsUrl) {
        mUpdated = updated;
        mTitle = title;
        mBody = body;
        mCommentsUrl = commentsUrl;
    }

    public Calendar getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Calendar updated) {
        mUpdated = updated;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public String getCommentsUrl() {
        return mCommentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        mCommentsUrl = commentsUrl;
    }
}

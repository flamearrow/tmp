package tmp.com.tmp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Issue implements Comparable<Issue>, Parcelable {

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

    public Issue(Parcel p) {
        mTitle = p.readString();
        mBody = p.readString();
        mCommentsUrl = p.readString();
        mUpdated = Calendar.getInstance();
        mUpdated.setTimeInMillis(p.readLong());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mBody);
        dest.writeString(mCommentsUrl);
        dest.writeLong(mUpdated.getTimeInMillis());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Issue>() {

        @Override
        public Issue createFromParcel(Parcel source) {
            return new Issue(source);
        }

        @Override
        public Issue[] newArray(int size) {
            return new Issue[size];
        }
    };
}

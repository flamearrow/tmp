package tmp.com.tmp;

public class Comment {
    private final String mName;
    private final String mBody;

    public Comment(String body, String name) {
        mBody = body;
        mName = name;
    }

    public String getBody() {
        return mBody;
    }

    public String getName() {
        return mName;
    }
}

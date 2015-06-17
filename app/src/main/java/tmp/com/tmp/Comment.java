package tmp.com.tmp;

public class Comment {
    private final String mName;
    private final String mBody;

    public Comment(String name, String body) {
        mName = name;
        mBody = body;
    }

    public String getBody() {
        return mBody;
    }

    public String getName() {
        return mName;
    }
}

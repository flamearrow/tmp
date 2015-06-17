package tmp.com.tmp;

public class Comment {
    private String mName;
    private String mBody;

    public Comment(String body, String name) {
        mBody = body;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

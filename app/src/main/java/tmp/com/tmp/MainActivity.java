package tmp.com.tmp;

import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends ListActivity {

    private ArrayList<Issue> mIssues;

    private IssueAdapter mIssueAdapter;

    private static final String ISSUES_KEY = "ISSUES_KEY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mIssues == null) {
            if (savedInstanceState != null && savedInstanceState.containsKey(ISSUES_KEY)) {
                mIssues = savedInstanceState.getParcelableArrayList(ISSUES_KEY);
            } else {
                mIssues = new ArrayList<>();
                IssueManager.retrieveIssues(new IssueManager.ResultListener() {
                    @Override
                    public void onIssueResult(ArrayList<Issue> issues) {
                        Collections.sort(issues);
                        mIssues.addAll(issues);
                        mIssueAdapter.notifyDataSetChanged();
                    }
                });
            }
            mIssueAdapter = new IssueAdapter();

        }
        setListAdapter(mIssueAdapter);
        getListView().setOnItemClickListener(new IssueClickListener());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("mlgb", "saving state");
        outState.putParcelableArrayList(ISSUES_KEY, mIssues);
        super.onSaveInstanceState(outState);
    }

    class IssueClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.comments_dialogue);
            dialog.setTitle("Comments");
            final TextView tx = (TextView) dialog.findViewById(R.id.comments);
            tx.setMovementMethod(new ScrollingMovementMethod());
            CommentManager.retrieveComments(mIssues.get(position),
                    new CommentManager.ResultListener() {
                        @Override
                        public void onCommentsResult(ArrayList<Comment> comments) {
                            tx.setText(buildComments(comments));
                        }
                    });
            final Button button = (Button) dialog.findViewById(R.id.ok_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        private String buildComments(ArrayList<Comment> comments) {
            if (comments.size() == 0) {
                return "No comments!";
            }
            StringBuilder sb = new StringBuilder();
            for (Comment comment : comments) {
                sb.append("--------\n");
                sb.append(comment.getName());
                sb.append(" says:  ");
                sb.append(comment.getBody());
                sb.append("\n\n");
            }
            return sb.toString();
        }
    }


    class IssueAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mIssues.size();
        }

        @Override
        public Object getItem(int position) {
            return mIssues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, null);
            }
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView body = (TextView) convertView.findViewById(R.id.body);
            title.setText(mIssues.get(position).getTitle());
            body.setText(mIssues.get(position).getBody());
            return convertView;
        }
    }
}

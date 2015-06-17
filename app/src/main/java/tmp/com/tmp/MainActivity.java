package tmp.com.tmp;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    private ArrayList<Issue> mIssues;

    private IssueAdapter mIssueAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mIssues == null) {
            initializeIssueAdapter();
            IssueManager.retrieveIssues(new IssueManager.ResultListener() {
                @Override
                public void onIssueResult(ArrayList<Issue> issues) {
                    mIssues.addAll(issues);
                    mIssueAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void initializeIssueAdapter() {
        mIssues = new ArrayList<>();
        mIssueAdapter = new IssueAdapter();
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
            return convertView;
        }
    }
}

package by.minsk.angurets.webbrowser;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class HistoryActivity extends ActionBarActivity {

    private List<HistoryItem> historyItems = HistoryStorage.getHistoryItems();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        mListView = (ListView) findViewById(R.id.history_list);

        ArrayAdapter<HistoryItem> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, historyItems);

        mListView.setAdapter(adapter);
    }

}

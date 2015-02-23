package by.minsk.angurets.calculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class HistoryActivity extends ActionBarActivity {

    private List<HistoryItem> historyItems = HistoryItemsStorage.getAll();
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list);

        mListView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<HistoryItem> adapter = new ArrayAdapter<HistoryItem>
                (this, android.R.layout.simple_list_item_1, historyItems);

        mListView.setAdapter(adapter);
    }

}
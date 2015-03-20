package by.minsk.angurets.webbrowser;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import by.minsk.angurets.webbrowser.model.HistoryItem;

public class HistoryActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<HistoryItem>> {

    public static final int LOADER_ID = 0;

    private ListView mHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_activity);

        getLoaderManager().initLoader(LOADER_ID, null, this);
        mHistoryList = (ListView) findViewById(R.id.history_list);

        mHistoryList.setAdapter(new HistoryAdapter(this, new ArrayList<HistoryItem>()));
    }


    private void updateList() {
        ((BaseAdapter) mHistoryList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public Loader<List<HistoryItem>> onCreateLoader(int id, Bundle args) {
        return new HistoryItemLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<HistoryItem>> loader, List<HistoryItem> data) {
        mHistoryList.setAdapter(new HistoryAdapter(this, data));
        updateList();
    }

    @Override
    public void onLoaderReset(Loader<List<HistoryItem>> loader) {
    }

    static class HistoryItemLoader extends AbstractLoader<List<HistoryItem>> {

        HistoryItemLoader(Context context) {
            super(context);
        }

        @Override
        public List<HistoryItem> loadInBackground() {
            return HistoryStorage.getInstance().getHistoryItems();
        }
    }

}

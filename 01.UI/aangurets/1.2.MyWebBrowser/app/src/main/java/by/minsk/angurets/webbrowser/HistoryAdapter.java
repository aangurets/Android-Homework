package by.minsk.angurets.webbrowser;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryItem> {

    private LayoutInflater mLayoutInflater;

    public HistoryAdapter(Context context, List<HistoryItem> historyItems) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, historyItems);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public HistoryItem getItem(int position) {
        return HistoryStorage.getHistoryItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = convertView;
        if (mView == null) {
            mView = mLayoutInflater.inflate(R.layout.history_item, parent, false);
        }
        HistoryItem mHistoryItem = HistoryStorage.getHistoryItem(position);
        TextView mTextView = (TextView) mView.findViewById(R.id.history_item);
        mTextView.setText(mHistoryItem.getUrl());
        return mView;
    }
}

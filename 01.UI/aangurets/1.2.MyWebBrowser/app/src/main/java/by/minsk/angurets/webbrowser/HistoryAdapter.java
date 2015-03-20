package by.minsk.angurets.webbrowser;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.minsk.angurets.webbrowser.model.HistoryItem;

public class HistoryAdapter extends ArrayAdapter<HistoryItem> {
    List<HistoryItem> mHistoryItems;

    public HistoryAdapter(Context context, List<HistoryItem> historyItems) {
        super(context, android.R.layout.simple_list_item_1, android.R.id.text1, historyItems);
        mHistoryItems = historyItems;
        LayoutInflater.from(context);
    }

    @Override
    public HistoryItem getItem(int position) {
        return mHistoryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = convertView;
        if (mView == null) {
            mView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_item, parent, false);
        }
        HistoryItem mHistoryItem = mHistoryItems.get(position);
        TextView mTextView = (TextView) mView.findViewById(R.id.history_item);
        mTextView.setText(mHistoryItem.toString());
        return mView;
    }
}

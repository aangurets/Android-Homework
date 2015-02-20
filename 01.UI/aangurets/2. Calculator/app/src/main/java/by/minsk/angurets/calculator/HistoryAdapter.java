package by.minsk.angurets.calculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

    public class HistoryAdapter extends BaseAdapter {

        private List<String> mHistoryItems;
        private LayoutInflater mLayoutInflater;

        public HistoryAdapter(Context context, List<String> historyItems) {
            mHistoryItems = historyItems;
            mLayoutInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mHistoryItems.size();
        }

        @Override
        public Object getItem(int position) {
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
                mView = mLayoutInflater.inflate(R.layout.history_item, parent, false);
            }
            String mHistoryItem = mHistoryItems.get(position);
            TextView mTextView = (TextView) mView.findViewById(R.id.history_item);
            mTextView.setText(mHistoryItem);
            return mView;
        }
    }
package by.minsk.angurets.webbrowser;

import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    public static List<HistoryItem> mHistoryItems = new ArrayList<>();

    public static List<HistoryItem> getHistoryItems() {
        return mHistoryItems;
    }

    public static HistoryItem getHistoryItem(int i) {
        return mHistoryItems.get(i);
    }

    public static void addToHistoryItems(HistoryItem item) {
        mHistoryItems.add(item);
    }
}

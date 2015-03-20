package by.minsk.angurets.webbrowser;

import java.util.ArrayList;
import java.util.List;

import by.minsk.angurets.webbrowser.model.HistoryItem;

public class HistoryStorage {
    private static HistoryStorage sHistoryStorage;

    private List<HistoryItem> mHistoryItems;

    public HistoryStorage() {
        mHistoryItems = new ArrayList<>();
    }


    public List<HistoryItem> getHistoryItems() {
        return mHistoryItems;
    }

    public void addToHistoryItems(HistoryItem item) {
        mHistoryItems.add(item);
    }

    public static HistoryStorage getInstance() {
        if (sHistoryStorage == null) {
            sHistoryStorage = new HistoryStorage();
        }
        return sHistoryStorage;
    }
}

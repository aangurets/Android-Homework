package by.minsk.angurets.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoryItemsStorage {
    public static List<HistoryItem> mHistoryItems = new ArrayList<>();

    public static void add(HistoryItem historyItem) {
        mHistoryItems.add(historyItem);
    }

    public static List<HistoryItem> getAll() {
        return Collections.unmodifiableList(mHistoryItems);
    }
}

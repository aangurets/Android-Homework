package by.minsk.angurets.mywebbrowser;

import java.util.ArrayList;
import java.util.List;

public class HistoryStorage {

    public static List<String> mHistoryItems = new ArrayList<>();

    public static List<String> getHistoryItems() {
        return mHistoryItems;
    }

    public static void addToHistoryItems(String item) {
        mHistoryItems.add(item);
    }
}

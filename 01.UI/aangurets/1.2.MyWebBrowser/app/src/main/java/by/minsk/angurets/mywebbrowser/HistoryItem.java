package by.minsk.angurets.mywebbrowser;

/**
 * Created by User on 28.02.2015.
 */
public class HistoryItem {

    private String mUrl;

    public HistoryItem(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public String toString() {
        return "History item: " + mUrl;
    }
}

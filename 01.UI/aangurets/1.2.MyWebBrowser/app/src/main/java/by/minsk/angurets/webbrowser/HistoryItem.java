package by.minsk.angurets.webbrowser;

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

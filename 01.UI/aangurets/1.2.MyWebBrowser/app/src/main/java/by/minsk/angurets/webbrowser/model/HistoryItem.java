package by.minsk.angurets.webbrowser.model;

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

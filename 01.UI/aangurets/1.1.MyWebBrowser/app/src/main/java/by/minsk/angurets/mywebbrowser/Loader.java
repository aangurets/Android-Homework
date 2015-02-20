package by.minsk.angurets.mywebbrowser;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class Loader extends AsyncTaskLoader<HistoryStorage> {

    private HistoryStorage mHistoryStorage;
    
    public Loader(Context context) {
        super(context);
    }

    @Override
    public HistoryStorage loadInBackground() {
        return null;
    }
}

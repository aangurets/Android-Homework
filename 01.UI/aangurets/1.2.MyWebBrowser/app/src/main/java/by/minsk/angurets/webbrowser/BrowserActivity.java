package by.minsk.angurets.webbrowser;

import android.app.LoaderManager;
import android.content.*;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BrowserActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<HistoryItem>> {

    public static final String PREFIX = "https://";
    public static final String URL = "URL";
    public static final int LOADER_ID = 1;

    @InjectView(R.id.url)
    EditText mUrl;
    @InjectView(R.id.open_button)
    ImageButton mOpenButton;
    @InjectView(R.id.back_button)
    ImageButton mBackButton;
    @InjectView(R.id.forward_button)
    ImageButton mForwardButton;
    @InjectView(R.id.history_button)
    ImageButton mHistoryButton;
    @InjectView(R.id.webView)
    WebView mWebView;

    private String mTempURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_layout);

        ButterKnife.inject(this);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        repairingUrl(savedInstanceState);

        mBackButton.setEnabled(false);
        mForwardButton.setEnabled(false);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openButtonAction();
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonAction();
            }
        });
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forwardButtonAction();
            }
        });
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BrowserActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void forwardButtonAction() {
        if (mWebView.canGoForward()) {
            mWebView.goForward();
        } else {
            mForwardButton.setEnabled(false);
            Toast.makeText(
                    this, R.string.show_forward, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void backButtonAction() {
        mForwardButton.setEnabled(true);
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            mBackButton.setEnabled(false);
            Toast.makeText(
                    this, R.string.show_back, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void openButtonAction() {
        HistoryStorage.addToHistoryItems(new HistoryItem(mUrl.getText().toString()));
        mBackButton.setEnabled(true);
        if (mUrl.getText().toString().startsWith(PREFIX)) {
            mWebView.loadUrl(mUrl.getText().toString());
        } else {
            mWebView.loadUrl(PREFIX + mUrl.getText().toString());
        }
    }

    private void repairingUrl(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            if (preferences.contains(URL)) {
                String url = preferences.getString(URL, null);
                mWebView.loadUrl(url);
            }
        }
    }


    @Override
    protected void onStop() {
        if (isFinishing()) {
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(URL, mWebView.getUrl());
            editor.apply();
        }
        super.onStop();
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        mTempURL = mWebView.getUrl();
        state.putString("url", mTempURL);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTempURL = savedInstanceState.getString("url");
        mWebView.loadUrl(mTempURL);
        mUrl.setText(mTempURL);
    }


    @Override
    public Loader<List<HistoryItem>> onCreateLoader(int id, Bundle args) {
        return new HistoryItemLoader(this, HistoryStorage.getHistoryItems());
    }

    @Override
    public void onLoadFinished(Loader<List<HistoryItem>> loader, List<HistoryItem> data) {
        if (data != null) {
            data.clear();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<HistoryItem>> loader) {
    }

    static class HistoryItemLoader extends AbstractLoader<List<HistoryItem>> {

        private List<HistoryItem> historyItems;

        HistoryItemLoader(Context context, List<HistoryItem> historyItems) {
            super(context);
            historyItems = HistoryStorage.getHistoryItems();
        }

        @Override
        public List<HistoryItem> loadInBackground() {
            return historyItems;
        }
    }
}
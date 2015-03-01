package by.minsk.angurets.mywebbrowser;

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

import java.util.Collections;
import java.util.List;

/**
 * Created by aangurets on 07.02.2015.
 */

public class BrowserActivity extends ActionBarActivity
        implements LoaderManager.LoaderCallbacks<List<HistoryItem>>{

    private EditText mUrl;
    private ImageButton mOpenButton;
    private ImageButton mBackButton;
    private ImageButton mForwardButton;
    private ImageButton mHistoryButton;
    private String mTempURI;
    private WebView mWebView;
    public static final String PREFIX = "http://";
    public static final String URL = "URL";
    public static final int LOADER_ID = 1;
    private List<HistoryItem> mHistoryItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browser_layout);

        mUrl = (EditText) findViewById(R.id.url);
        mOpenButton = (ImageButton) findViewById(R.id.open_button);
        mBackButton = (ImageButton) findViewById(R.id.back_button);
        mForwardButton = (ImageButton) findViewById(R.id.forward_button);
        mHistoryButton = (ImageButton) findViewById(R.id.history_button);
        mWebView = (WebView) findViewById(R.id.webView);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        if (savedInstanceState == null) {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            if (preferences.contains(URL)) {
                String url = preferences.getString(URL, null);
                mWebView.loadUrl(url);
            }
        }
        mBackButton.setEnabled(false);
        mForwardButton.setEnabled(false);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryStorage.addToHistoryItems(new HistoryItem(mUrl.getText().toString()));
                mBackButton.setEnabled(true);
                if (mUrl.getText().toString().startsWith(PREFIX)) {
                    mWebView.loadUrl(mUrl.getText().toString());
                } else {
                    mWebView.loadUrl(PREFIX + mUrl.getText().toString());
                }
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mForwardButton.setEnabled(true);
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    mBackButton.setEnabled(false);
                    Toast.makeText(
                            BrowserActivity.this, R.string.show_back, Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView.canGoForward()) {
                    mWebView.goForward();
                } else {
                    mForwardButton.setEnabled(false);
                    Toast.makeText(
                            BrowserActivity.this, R.string.show_forward, Toast.LENGTH_LONG)
                            .show();
                }
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

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            SharedPreferences settings = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(URL, mWebView.getUrl());
            editor.apply();
        }
        super.onDestroy();
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        mTempURI = mWebView.getUrl();
        state.putString("url", mTempURI);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTempURI = savedInstanceState.getString("url");
        mWebView.loadUrl(mTempURI);
        mUrl.setText(mTempURI);
    }


    @Override
    public Loader<List<HistoryItem>> onCreateLoader(int id, Bundle args) {
        return new HistoryItemLoader(this, mHistoryItems);
    }

    @Override
    public void onLoadFinished(Loader<List<HistoryItem>> loader, List<HistoryItem> data) {

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
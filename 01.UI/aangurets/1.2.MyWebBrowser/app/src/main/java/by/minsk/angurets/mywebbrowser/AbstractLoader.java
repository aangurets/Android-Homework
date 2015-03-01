package by.minsk.angurets.mywebbrowser;

import android.content.AsyncTaskLoader;
import android.content.Context;


public abstract class AbstractLoader<E> extends AsyncTaskLoader<E> {

    private E mData;
    private boolean mListenerUnregistered = true;

    protected AbstractLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mData != null) {
            deliverResult(mData);
        } else {
            if (mListenerUnregistered) {
                registerListeners();
                mListenerUnregistered = false;
            }

            if (takeContentChanged() || mData == null) {
                forceLoad();
            }
        }
    }

    @Override
    public void deliverResult(E data) {
        if (isReset()) {
            liberationResources(data);
            return;
        }

        E oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null && oldData != data) {
            liberationResources(oldData);
        }
    }

    protected E getData() {
        return mData;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (mData != null) {
            liberationResources(mData);
            mData = null;
        }

        unregisterListeners();
        mListenerUnregistered = true;
    }

    protected void registerListeners() {
    }

    protected void unregisterListeners() {
    }

    @Override
    public void onCanceled(E data) {
        super.onCanceled(data);
        liberationResources(data);
    }

    protected void liberationResources(E data) {
    }
}

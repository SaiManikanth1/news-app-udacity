package com.example.android.report;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class reportloader extends AsyncTaskLoader<List<report>> {

    private static final String LOG_TAG = reportloader.class.getName();

    private String mUrl;

    public reportloader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<report> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<report> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
package com.abanoub.unit.dailynews.loader;

import android.content.Context;
import android.content.AsyncTaskLoader;

import com.abanoub.unit.dailynews.model.DailyNews;
import com.abanoub.unit.dailynews.utils.NewsUtils;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<DailyNews>> {

    /**String variable to save the connect url */
    private String url;

    /** all data should use it inside this class */
    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    /**
     * responsible of connect and fetch the data from internet
     * inside separate and background thread
     * @return a List of information about earthquake
     */
    @Override
    public List<DailyNews> loadInBackground() {
        if (url == null){
            return null;
        }

        List<DailyNews> list = NewsUtils.fetchDataFromInternet(url);
        return list;
    }


    @Override
    protected void onStartLoading() {
        onForceLoad();
    }
}

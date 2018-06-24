package com.abanoub.unit.dailynews.utils;

import android.util.Log;

import com.abanoub.unit.dailynews.model.DailyNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsUtils {

    /** refer to this class in log massages when catch error in compiling */
    private static final String LOG_TAG = NewsUtils.class.getSimpleName();

    /**
     * convert String url to url can use it in connecting to server
     * @param stringUrl the string url
     * @return url can accepted the http request
     */
    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "An error while preparing the url", e);
        }
        return url;
    }

    /**
     * convert the input stream comes from http connection to string
     * we can use
     * @param stream comes from http connection
     * @return string translated from the input stream
     */
    private static String readFromInputStream(InputStream stream){
        StringBuilder builder = new StringBuilder();

        try {
            InputStreamReader streamReader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                builder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "An error while converting the input stream", e);
        }
        return builder.toString();

    }

    /**
     * create connection with server to fetch data
     * @param url responsible of get the connection
     * @return string data getting from the server
     * @throws IOException caused of error in input or output
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String newsJson = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                newsJson = readFromInputStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error in response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "An error while preparing http request", e);
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return newsJson;
    }

    /**
     * extract the JSON data from string
     * @param newsJson string JSON well extract from
     * @return list of news data
     */
    private static List<DailyNews> extractJsonFromString(String newsJson){

        List<DailyNews> list = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(newsJson);
            JSONArray results = root.getJSONArray("results");

            for (int i = 0; i < results.length(); i++){
                JSONObject object = results.getJSONObject(i);

                String sectionName = object.getString("sectionName");
                String publicationDate = object.getString("webPublicationDate");
                String title = object.getString("webTitle");
                String newsUrl = object.getString("webUrl");

                DailyNews dailyNews = new DailyNews(sectionName, publicationDate, title, newsUrl);

                list.add(dailyNews);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "An error while extracting JSON", e);
        }

        return list;
    }

    /**
     * responsible of getting the data and convert it and add it to list
     * @param stringUrl responsible of connection to server
     * @return list of news data
     */
    public static List<DailyNews> fetchDataFromInternet(String stringUrl){
        URL url = createUrl(stringUrl);
        String newsData = "";
        try {
            newsData = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "An error while creating http request", e);
        }

        List<DailyNews> list = extractJsonFromString(newsData);
        return list;
    }
}

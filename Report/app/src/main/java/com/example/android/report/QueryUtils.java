package com.example.android.report;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<report> fetchNewsData(String requestUrl) {

        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {

            jsonResponse = getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<report> news = extractFeatureFromJson(jsonResponse);


        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private static List<report> extractFeatureFromJson(String NewsJSON) {

        if (TextUtils.isEmpty(NewsJSON)) {
            return null;
        }

        List<report> news = new ArrayList<>();

        try {
            JSONObject reader = new JSONObject(NewsJSON);
            JSONObject response = reader.getJSONObject("response");
            JSONArray newsArray = response.getJSONArray("results");

            for (int i = 0; i < newsArray.length(); i++) {

                JSONObject currentNews = newsArray.getJSONObject(i);

                String sectionName = currentNews.getString("sectionName");

                String newsTitle = currentNews.getString("webTitle");
                String newsPublish = currentNews.getString("webPublicationDate");
                String newsURL;
                if(currentNews.has("webUrl")){
                    newsURL = currentNews.getString("webUrl");
                }
                else{
                    newsURL = null;
                }

                String thumbnail;
                if(currentNews.has("fields")){
                    JSONObject fields = currentNews.getJSONObject("fields");
                    thumbnail = fields.getString("thumbnail");
                }
                else{
                    thumbnail = null;
                }


                news.add(new report("newsName", sectionName, newsTitle, newsURL, thumbnail, newsPublish));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }

        return news;
    }

}


package com.example.android.report;

public class report {
    private String mName;
    private String mSectionName;
    private String mTitle;
    private String mUrl;
    private String mUrlToImage;
    private String mPublishedAt;

    public report(String name, String sectionName, String title, String url, String urlToImage, String publishAt){
        mName = name;
        mSectionName = sectionName;
        mTitle = title;
        mUrl = url;
        mUrlToImage = urlToImage;
        mPublishedAt = publishAt;
    }

    public String getName(){
        return mName;
    }

    public String getAuthor(){
        return mSectionName;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getUrl(){
        return mUrl;
    }

    public String getUrlToImage(){
        return mUrlToImage;
    }

    public String getPublishedAt(){
        return mPublishedAt;
    }
}

package com.abanoub.unit.dailynews.model;


public class DailyNews {

    private String mSectionName;
    private String mPublishedDate;
    private String mTitle;
    private String mNewsUrl;


    public DailyNews(String sectionName, String publishedDate, String title, String newsUrl){
        mSectionName = sectionName;
        mPublishedDate = publishedDate;
        mTitle = title;
        mNewsUrl = newsUrl;
    }


    public String getmSectionName() {
        return mSectionName;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmNewsUrl() {
        return mNewsUrl;
    }
}

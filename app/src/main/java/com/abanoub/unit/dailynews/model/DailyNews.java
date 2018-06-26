package com.abanoub.unit.dailynews.model;


public class DailyNews {

    /** contain the name of section of news */
    private String mSectionName;

    /** contain the published date of news */
    private String mPublishedDate;

    /** contain the title of news */
    private String mTitle;

    /** contain the feed url of news */
    private String mNewsUrl;


    public DailyNews(String sectionName, String publishedDate, String title, String newsUrl){
        mSectionName = sectionName;
        mPublishedDate = publishedDate;
        mTitle = title;
        mNewsUrl = newsUrl;
    }

    /**@return string section name of news */
    public String getmSectionName() {
        return mSectionName;
    }

    /**@return string published date of news */
    public String getmPublishedDate() {
        return mPublishedDate;
    }

    /**@return string title of news */
    public String getmTitle() {
        return mTitle;
    }

    /**@return string news feed url */
    public String getmNewsUrl() {
        return mNewsUrl;
    }
}

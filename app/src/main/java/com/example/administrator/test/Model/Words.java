package com.example.administrator.test.Model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017-06-03.
 */

public class Words {

    private String title;
    private String book;
//    private int chap;
//    private int phrase;
    private String words;
    private String summary;
    private Date date;
    private UUID uuid;

    public Words(){
        date = new Date();
        uuid = UUID.randomUUID();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

//    public int getChap() {
//        return chap;
//    }
//
//    public void setChap(int chap) {
//        this.chap = chap;
//    }
//
//    public int getPhrase() {
//        return phrase;
//    }
//
//    public void setPhrase(int phrase) {
//        this.phrase = phrase;
//    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}

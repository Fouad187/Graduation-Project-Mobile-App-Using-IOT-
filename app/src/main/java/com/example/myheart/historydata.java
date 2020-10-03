package com.example.myheart;

public class historydata {

    String bpm;
    String date;
    String time;
    String casee;

    public historydata()
    {

    }
    public historydata(String bpm , String date, String time,String casee)
    {
        this.bpm=bpm;
        this.date=date;
        this.time=time;
        this.casee=casee;

    }

    public String getCasee() {
        return casee;
    }

    public void setCasee(String casee) {
        this.casee = casee;
    }

    public String getBpm() {
        return bpm;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setBpm(String bpm) {
        this.bpm = bpm;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

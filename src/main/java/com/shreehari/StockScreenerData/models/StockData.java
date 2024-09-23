package com.shreehari.StockScreenerData.models;

import lombok.Data;

import java.util.Date;

@Data
public class StockData {

    private String first_date;

    private String symbol;

    private String mcapName;

    private String sector;

    private String last_date;

    private int occurence_count;

    private String first_date_weekly;

    private String last_date_weekly;

    private String timeFrame;

    private int occurence_weekly;

    public StockData(String first_date, String symbol, String mcapName, String sector,int occurence_count, String timeFrame) {
        this.first_date = first_date;
        this.symbol = symbol;
        this.mcapName = mcapName;
        this.sector = sector;
        this.occurence_count= occurence_count;
        this.timeFrame = timeFrame;
    }

    public StockData(String firstDate, String symbol, String mkcapName, String sector, String lastDate, int occurence_count, String timeFrame) {
        this.first_date = firstDate;
        this.symbol = symbol;
        this.mcapName = mkcapName;
        this.sector = sector;
        this.last_date = lastDate;
        this.occurence_count = occurence_count;
        this.timeFrame=timeFrame;
    }

    public StockData(String firstDate, String symbol, String mkcapName, String sector, String lastDate, Integer occurence_count, String timeFrame,
                     String firstDateWeekly, String lastDateWeekly, int occurenceCountWeekly) {
        this.first_date = firstDate;
        this.symbol = symbol;
        this.mcapName = mkcapName;
        this.sector = sector;
        this.last_date = lastDate;
        this.occurence_count = occurence_count;
        this.timeFrame=timeFrame;
        this.first_date_weekly=firstDateWeekly;
        this.last_date_weekly=lastDateWeekly;
        this.occurence_weekly=occurenceCountWeekly;

    }
}

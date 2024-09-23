package com.shreehari.StockScreenerData.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.shreehari.StockScreenerData.models.StockData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Service
public class CsvUpdateService {

    public void updateOrAppendToCsvFile(String filePath, Map<String, StockData> stocks) throws IOException, CsvException {

        Map<String, StockData> fileData = fileWithDataExists(filePath);
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Symbol", "MarketCapName", "Sector", "First_Date", "Last_Date", "Occurence_Count", "TimeFrame", "First_Weekly", "Last_Weekly", "Weekly_Occurence"};
            csvWriter.writeNext(header);
            if (!fileData.isEmpty()) {
                for (String stock : fileData.keySet()) {
                    if (stocks.containsKey(stock)) {
                        StockData st = fileData.get(stock);
                        String[] data;
                        StockData current = stocks.get(stock);
                        if (st.getLast_date().equalsIgnoreCase(current.getLast_date()) || st.getLast_date().equalsIgnoreCase("")) {
                            data = new String[]{st.getSymbol(), st.getMcapName(), st.getSector(), st.getFirst_date(), current.getLast_date(),
                                    String.valueOf(st.getOccurence_count()), current.getTimeFrame(), current.getFirst_date_weekly(),
                                    current.getLast_date_weekly(), String.valueOf(current.getOccurence_weekly())};
                        } else {
                            data = new String[]{st.getSymbol(), st.getMcapName(), st.getSector(), st.getFirst_date(), stocks.get(stock).getLast_date(),
                                    String.valueOf(st.getOccurence_count() + 1), current.getTimeFrame(), current.getFirst_date_weekly(),
                                    current.getLast_date_weekly(), String.valueOf(current.getOccurence_weekly())};
                        }
                        csvWriter.writeNext(data);
                    } else {
                        String[] data = {stocks.get(stock).getSymbol(), stocks.get(stock).getMcapName(), stocks.get(stock).getSector(),
                                stocks.get(stock).getFirst_date(), stocks.get(stock).getLast_date(), String.valueOf(1), stocks.get(stock).getTimeFrame(),
                                stocks.get(stock).getFirst_date_weekly(),stocks.get(stock).getLast_date_weekly(), String.valueOf(stocks.get(stock).getOccurence_weekly())};
                        csvWriter.writeNext(data);
                    }

                }
            } else {
                // Write stocks data
                for (StockData stock : stocks.values()) {
                    String[] data = {stock.getSymbol(), stock.getMcapName(), stock.getSector(), stock.getFirst_date(), stock.getLast_date(),
                            String.valueOf(stock.getOccurence_count()), stock.getTimeFrame(), stock.getFirst_date_weekly(),
                            stock.getLast_date_weekly(), String.valueOf(stock.getOccurence_weekly())};
                    csvWriter.writeNext(data);
                }
            }
        }
    }

    private Map<String, StockData> fileWithDataExists(String filePath) throws IOException, CsvException {
        Map<String, StockData> stockMap = new LinkedHashMap<>();
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile(); //creating it
        } else {
            try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
                List<String[]> allData = csvReader.readAll();
                String[] header = {"Symbol", "MarketCapName", "Sector", "First_Date", "Last_Date", "Occurence_Count", "TimeFrame", "First_Weekly", "Last_Weekly", "Weekly_Occurence"};
                for (String[] row : allData) {
                    if (row.length < 5) continue; // Skip invalid rows
                    if (row[0].equalsIgnoreCase("Symbol")) continue;
                    String symbol = row[0];
                    String mkcapName = row[1];
                    String sector = row[2];
                    String first_date = row[3];
                    String last_date = row[4];
                    String occurence_count = row[5];
                    String timeFrame = row[6];
                    String first_date_weekly = row[7];
                    String last_date_weekly = row[8];
                    String occurence_count_weekly = row[9];
                    stockMap.put(symbol, new StockData(first_date, symbol, mkcapName, sector,
                            last_date, Integer.valueOf(occurence_count), timeFrame, first_date_weekly, last_date_weekly,
                            Integer.valueOf(occurence_count_weekly)));
                }
            }
        }
        return stockMap;
    }

    public Map<String, StockData> merge(Map<String, StockData> dataDaily, Map<String, StockData> dataWeekly) {

        Set<String> combinedKeySet = new LinkedHashSet<>(dataDaily.keySet());
        combinedKeySet.addAll(dataWeekly.keySet());
        for (String key : combinedKeySet) {
            if (dataDaily.containsKey(key) && dataWeekly.containsKey(key)) {
                StockData stock = dataDaily.get(key);
                stock.setFirst_date_weekly(dataWeekly.get(key).getFirst_date());
                stock.setLast_date_weekly(dataWeekly.get(key).getLast_date());
                stock.setTimeFrame("DW");
                stock.setOccurence_weekly(dataWeekly.get(key).getOccurence_count());
            } else if (dataWeekly.containsKey(key)) {
                dataDaily.put(key, dataDaily.get(key));
            }
        }
        return dataDaily;
    }
}

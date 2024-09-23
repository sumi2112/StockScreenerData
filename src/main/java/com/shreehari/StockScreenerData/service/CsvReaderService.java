package com.shreehari.StockScreenerData.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.shreehari.StockScreenerData.models.StockData;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvReaderService {

    public Map<String, StockData> readCsvFile(String filePath, String time) throws IOException, CsvException {
        Map<String, StockData> stockDataMap = new LinkedHashMap<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allData = csvReader.readAll();

            for (String[] row : allData) {
                // Assuming CSV columns are: Name, ID, Salary
                if (row[1].equalsIgnoreCase("symbol")) continue;
                String[] dateFormat = row[0].substring(0, 10).split("-"); //dd-MM-yyyy
                String date = dateFormat[2]+dateFormat[1]+dateFormat[0];  //yyyyMMdd
                String symbol = row[1];
                String mkcapName = row[2];
                String sector = row[3];

                if (stockDataMap.containsKey(symbol)) {
                    stockDataMap.get(symbol).setLast_date(date);
                    int count = stockDataMap.get(symbol).getOccurence_count();
                    stockDataMap.get(symbol).setOccurence_count(count + 1);
                } else {
                    StockData stockData = new StockData(date, symbol, mkcapName, sector, 1, time);
                    stockDataMap.put(symbol, stockData);
                }
            }
        }
        return stockDataMap;
    }

}
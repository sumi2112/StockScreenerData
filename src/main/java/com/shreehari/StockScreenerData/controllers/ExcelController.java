package com.shreehari.StockScreenerData.controllers;

import com.opencsv.exceptions.CsvException;
import com.shreehari.StockScreenerData.models.StockData;
import com.shreehari.StockScreenerData.service.CsvReaderService;
import com.shreehari.StockScreenerData.service.CsvUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@RestController
public class ExcelController {

    @Autowired
    private CsvReaderService csvReaderService;

    @Autowired
    private CsvUpdateService csvUpdateService;

    private String inputFilePath = "C:\\Users\\sumsingh2\\Downloads\\Backtest ema_crossover_profit2, Technical Analysis Scanner.csv";
    private String outputFilePath = "C:\\Development\\StockScreenerData.csv";

    private String inputFilePathWeekly = "C:\\Users\\sumsingh2\\Downloads\\Backtest ema_crossover_profit2_weekly, Technical Analysis Scanner.csv";
    private String outputFilePathWeekly = "C:\\Development\\StockScreenerDataWeekly.csv";

    @GetMapping("/processFiles")
    public void readExcel() {
        try {
            Map<String, StockData> dataDaily = csvReaderService.readCsvFile(inputFilePath, "D");
            Map<String, StockData> dataWeekly = csvReaderService.readCsvFile(inputFilePathWeekly, "W");
            Map<String, StockData> data = csvUpdateService.merge(dataDaily,dataWeekly);
            csvUpdateService.updateOrAppendToCsvFile(outputFilePath, data);

            try {
                // Delete the file
                Files.delete(Paths.get(inputFilePath));
                Files.delete(Paths.get(inputFilePathWeekly));
                System.out.println("File deleted successfully.");
                System.exit(200);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to delete the file.");
                System.exit(500);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            System.exit(500);
            // Handle exception accordingly
        }
    }

    @GetMapping("/processFileWeekly")
    public void readExcelWeekly() {
        try {
            Map<String, StockData> data = csvReaderService.readCsvFile(inputFilePathWeekly, "W");
            csvUpdateService.updateOrAppendToCsvFile(outputFilePathWeekly, data);

            try {
                // Delete the file
                Files.delete(Paths.get(inputFilePathWeekly));
                System.out.println("File deleted successfully.");
                System.exit(200);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Failed to delete the file.");
                System.exit(500);
            }
        } catch (CsvException | IOException e) {
            e.printStackTrace();
            System.exit(500);
            // Handle exception accordingly
        }
    }
}
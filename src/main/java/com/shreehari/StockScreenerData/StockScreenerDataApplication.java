package com.shreehari.StockScreenerData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class StockScreenerDataApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(StockScreenerDataApplication.class, args);
        Runtime rt = Runtime.getRuntime();
        rt.exec("rundll32 url.dll,FileProtocolHandler "
                    + "http://localhost:8080/processFiles");
    }

}

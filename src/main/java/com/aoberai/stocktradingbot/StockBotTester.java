package com.aoberai.stocktradingbot;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;


public class StockBotTester {

    public static void main(String[] args) {
        StockBotTester stockBotTester = new StockBotTester();
        stockBotTester.init();
    }

    public void init() {
        Config cfg = Config.builder()
                .key(Constants.ALPHAVANTAGE_API_KEY)
                .timeOut(Constants.CONNECT_TIMEOUT_MS)
                .build();

        AlphaVantage.api().init(cfg);

        System.out.println("\n\n\nStock Price: " + StockPriceReader.getInstance().getStockPrice(StockPriceReader.TimePeriod.MONTH, "AAPL", 6));
    }
}
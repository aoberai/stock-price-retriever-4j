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

        String stockToCheck = "AAPL";

        System.out.println("\n\n\n" + stockToCheck + " Stock Price: " + StockPriceReader.getInstance().getStockPrice(StockPriceReader.TimePeriod.PRICE_MONTH, stockToCheck, 3));
    }
}
package com.aoberai.stocktradingbot;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;


public class StockBotRunner {

    public static void main(String[] args) {
        StockBotRunner stockBotRunner = new StockBotRunner();
        stockBotRunner.init();
    }

    public void init() {
        Config cfg = Config.builder()
                .key(Constants.ALPHAVANTAGE_API_KEY)
                .timeOut(Constants.CONNECT_TIMEOUT_MS)
                .build();

        AlphaVantage.api().init(cfg);

        System.out.println(StockPriceRetriever.getInstance().getStockPrice(StockPriceRetriever.TimePeriod.NOW, "AAPL", 0));
    }
}
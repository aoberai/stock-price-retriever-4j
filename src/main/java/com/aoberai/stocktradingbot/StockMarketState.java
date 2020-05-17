package com.aoberai.stocktradingbot;

import java.util.ArrayList;
import java.util.List;

public class StockMarketState {
    private static StockMarketState sInstance = new StockMarketState();
    private List<String> mStocksToTrack = List.of("AAPL", "INTC", "AVGO", "FB", "VGT", "GOOG", "AMZN", "TSLA", "NFLX", "SPY", "XLE", "BRK-B", "TSLA", "NVDA", "MSFT", "QCOM", "AMD", "IBM", "CSCO", "LYFT");
    private ArrayList<String> mWatchList;

    private StockMarketState() {
    }

    public static StockMarketState getInstance() {
        return sInstance;
    }

    public void updateWatchList() {
    }
}

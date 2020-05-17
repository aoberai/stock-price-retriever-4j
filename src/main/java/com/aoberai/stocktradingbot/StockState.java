package com.aoberai.stocktradingbot;

import com.aoberai.stocktradingbot.utils.Timer;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.util.ArrayList;
import java.util.List;

public class StockState {
    private static StockState sInstance = new StockState();
    private List<String> mStocksToTrack = List.of("AAPL", "INTC", "AVGO", "FB", "VGT", "GOOG", "AMZN", "TSLA", "NFLX", "SPY", "XLE", "BRK-B", "TSLA", "NVDA", "MSFT", "QCOM", "AMD", "IBM", "CSCO", "LYFT");
    private ArrayList<String> mWatchList;

    private StockState() {}

    public static StockState getInstance() {
        return sInstance;
    }

    public void updateStockInfo() {
        for (int i = 0; i > -1; i++) {
            Double currentStockPrice = null;
            Double weekLowPrice = null;
            AlphaVantage.api()
                    .timeSeries()
                    .daily()
                    .adjusted()
                    .forSymbol(mStocksToTrack.get(i%mStocksToTrack.size()))
                    .outputSize(OutputSize.COMPACT)
                    .onSuccess(e -> handleTimeSeriesSuccess((TimeSeriesResponse) e, currentStockPrice, weekLowPrice))
                    .onFailure(e -> handleFailure(e))
                    .fetch();
            Timer.delay(Constants.API_CALL_INTERVAL);
        }
    }

    public void handleTimeSeriesSuccess(TimeSeriesResponse response, Double currentStockPrice, Double weekLowPrice) {
        currentStockPrice = response.getStockUnits().get(0).getAdjustedClose();
        System.out.print(response.getMetaData().getSymbol() + " " + response.getStockUnits().get(0).getDate() + " Price: ");
        System.out.println(currentStockPrice);
    }

    public void handleFailure(AlphaVantageException error) {
        System.out.println(error.getMessage());
        Timer.delay(2 * Constants.API_CALL_INTERVAL);
    }
}

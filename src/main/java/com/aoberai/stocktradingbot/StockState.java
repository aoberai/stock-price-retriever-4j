package com.aoberai.stocktradingbot;

import com.aoberai.stocktradingbot.utils.Timer;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.util.Set;

public class StockState {
    private Set<String> mStocksToTrack = Set.of("AAPL", "INTL", "AVGO", "FB", "VGT", "GOOG", "AMZN", "TSLA", "NFLX", "SPY", "XLE", "BRK-B");
    private Set<String> mActiveStocks;
    private static StockState sInstance = new StockState();

    public void updateStockInfo() {
        for (String stock : mStocksToTrack) {
            Double currentStockPrice = null;
            Double weekLowPrice = null;
            AlphaVantage.api()
                    .timeSeries()
                    .daily()
                    .adjusted()
                    .forSymbol(stock)
                    .outputSize(OutputSize.COMPACT)
                    .onSuccess(e->handleTimeSeriesSuccess((TimeSeriesResponse) e, currentStockPrice, weekLowPrice))
                    .onFailure(e->handleFailure(e))
                    .fetch();
            Timer.delay((long) 25000);
        }
    }


    public void handleTimeSeriesSuccess(TimeSeriesResponse response, Double currentStockPrice, Double weekLowPrice) {
        currentStockPrice = response.getStockUnits().get(0).getAdjustedClose();
        System.out.print(response.getMetaData().getSymbol() + " ");
        System.out.println(currentStockPrice);
    }

    public void handleFailure(AlphaVantageException error) {

        System.out.println("Doesn't function");
        System.out.println(error.getMessage());
    }

    public static StockState getInstance() {
        return sInstance;
    }
}

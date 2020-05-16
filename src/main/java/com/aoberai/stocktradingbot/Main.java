package com.aoberai.stocktradingbot;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class Main {

    public static void main(String[] args)
    {
        Main main = new Main();
        main.init();
    }

    public void init() {
        //initializer
        Config cfg = Config.builder()
                .key("OWTR2FQFI69GOFZA")
                .timeOut(100)
                .build();

        AlphaVantage.api().init(cfg);

        AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol("FB")
                .interval(Interval.FIVE_MIN)
                .outputSize(OutputSize.FULL)
                .onSuccess(e->handleTimeSeriesSuccess((TimeSeriesResponse) e))
                .onFailure(e->handleFailure(e))
                .fetch();
//
//        AlphaVantage.api()
//                .timeSeries()
//                .quote()
//                .forSymbol("FB")
//                .dataType(DataType.JSON)
//                .onSuccess(e->handleQuoteSuccess((QuoteResponse) e))
//                .onFailure(e->handleFailure((e)))
//                .fetch();


    }

    public void handleTimeSeriesSuccess(TimeSeriesResponse response) {
        System.out.println("Function");
        System.out.println(response.toString());
    }

    public void handleQuoteSuccess(QuoteResponse response) {
        System.out.println("Function");
        System.out.println(response.getPrice());
    }

    public void handleFailure(AlphaVantageException error) {

        System.out.println("Doesn't function");
        System.out.println(error.getMessage());
    }
}
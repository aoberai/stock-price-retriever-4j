package com.aoberai.stocktradingbot;

import com.aoberai.stocktradingbot.utils.Timer;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;

import java.util.Calendar;
import java.util.Date;

public class StockPriceRetriever {
    private static StockPriceRetriever sInstance = new StockPriceRetriever();

    private StockPriceRetriever() {}

    public static StockPriceRetriever getInstance() {
        return sInstance;
    }

    public enum TimePeriod {
        YEAR, MONTH, DAY, HOUR, MINUTE, NOW;
    }

    public double getStockPrice(TimePeriod timePeriod, String tickerSymbol, int rollBackVal) {
        Timer.delay(Constants.API_CALL_INTERVAL);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        double[] retVal = {0};
        switch (timePeriod) {
            case YEAR:
                break;
            case MONTH:
                break;
            case DAY:
                break;
            case HOUR:
                break;
            case MINUTE:
//                AlphaVantage.api()
//                        .timeSeries()
//                        .intraday()
//                        .forSymbol(tickerSymbol)
//                        .outputSize(OutputSize.FULL)
//                        .interval(Interval.ONE_MIN)
//                        .onSuccess(e-> {
//                            calendar.add(Calendar.MINUTE, -rollBackVal);
//                            for (StockUnit stockUnit : ((TimeSeriesResponse)e).getStockUnits()) {
//                                if (stockUnit.getDate(). == calendar.get(Calendar.MINUTE))
//                            }
//                        })
//                        .onFailure(e->handleFailure(e))
//                        .fetch();
                break;
            case NOW:
                AlphaVantage.api()
                        .timeSeries()
                        .quote()
                        .forSymbol(tickerSymbol)
                        .dataType(DataType.JSON)
                        .onSuccess(e -> {
                            retVal[0] = ((QuoteResponse) e).getPrice();
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
                break;
        }
        Timer.delay(Constants.API_CALL_INTERVAL);
        return retVal[0];
    }

    public void handleFailure(AlphaVantageException error) {
        System.out.println("Doesn't function");
        System.out.println(error.getMessage());
    }
}

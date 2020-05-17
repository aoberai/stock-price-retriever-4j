package com.aoberai.stocktradingbot;

import com.aoberai.stocktradingbot.utils.Timer;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.QuoteResponse;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StockPriceReader {
    private static StockPriceReader sInstance = new StockPriceReader();

    private StockPriceReader() {}

    public static StockPriceReader getInstance() {
        return sInstance;
    }

    public enum TimePeriod {
        YEAR, MONTH, DAY, HOUR, MINUTE, NOW;
    }

    public double getStockPrice(TimePeriod timePeriod, String tickerSymbol, int amount) {
        Timer.delay(Constants.API_CALL_INTERVAL); //Buffer period to prevent going over free api call limit.
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); //Sets time to current time
        double[] retVal = {0};
        switch (timePeriod) {
            case YEAR: {
                calendar.add(Calendar.YEAR, -amount);
                String reformattedDate = ( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) ).format( calendar.getTime() ); //Formats date into "yyyy-MM-dd HH:mm:ss" format
                AlphaVantage.api()
                        .timeSeries()
                        .monthly()
                        .adjusted()
                        .forSymbol(tickerSymbol)
                        .onSuccess(e-> {
                            System.out.println(amount + " years before: " + reformattedDate);
                            for (StockUnit stockUnit : ((TimeSeriesResponse)e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(reformattedDate) <= 0) {
                                    System.out.println(stockUnit.toString());
                                    retVal[0] = stockUnit.getAdjustedClose();
                                    break;
                                }
                            }
                        })
                        .onFailure(e->handleFailure(e))
                        .fetch();
                break;
        }
            case MONTH: {
                calendar.add(Calendar.MONTH, -amount);
                String reformattedDate = ( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) ).format( calendar.getTime() ); //Formats date into "yyyy-MM-dd HH:mm:ss" format
                AlphaVantage.api()
                        .timeSeries()
                        .weekly()
                        .adjusted()
                        .forSymbol(tickerSymbol)
                        .onSuccess(e-> {
                            System.out.println(amount +" month before: " + reformattedDate);
                            for (StockUnit stockUnit : ((TimeSeriesResponse)e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(reformattedDate) <= 0) {
                                    System.out.println(stockUnit.toString());
                                    retVal[0] = stockUnit.getAdjustedClose();
                                    break;
                                }
                            }
                        })
                        .onFailure(e->handleFailure(e))
                        .fetch();
                break;
            }
            case DAY: {
                calendar.add(Calendar.DATE, -amount);
                String reformattedDate = ( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) ).format( calendar.getTime() ); //Formats date into "yyyy-MM-dd HH:mm:ss" format
                reformattedDate = reformattedDate.substring(0, 11) + calendar.get(Calendar.HOUR_OF_DAY) + reformattedDate.substring(13); //Converts to 24 hr time
                String finalReformattedDate = reformattedDate;
                AlphaVantage.api()
                        .timeSeries()
                        .daily()
                        .adjusted()
                        .forSymbol(tickerSymbol)
                        .outputSize(OutputSize.FULL)
                        .onSuccess(e-> {
                            System.out.println(amount +" days before: " + finalReformattedDate);
                            for (StockUnit stockUnit : ((TimeSeriesResponse)e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(finalReformattedDate) <= 0) {
                                    System.out.println(stockUnit.toString());
                                    retVal[0] = stockUnit.getAdjustedClose();
                                    break;
                                }
                            }
                        })
                        .onFailure(e->handleFailure(e))
                        .fetch();
                break;
            }
            case HOUR: {
                calendar.add(Calendar.HOUR, -amount);
                String reformattedDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()); //Formats date into "yyyy-MM-dd HH:mm:ss" format
                reformattedDate = reformattedDate.substring(0, 11) + calendar.get(Calendar.HOUR_OF_DAY) + reformattedDate.substring(13); //Converts to 24 hr time
                String finalReformattedDate = reformattedDate;
                AlphaVantage.api()
                        .timeSeries()
                        .intraday()
                        .forSymbol(tickerSymbol)
                        .outputSize(OutputSize.FULL)
                        .interval(Interval.FIVE_MIN)
                        .onSuccess(e -> {
//                            System.out.println(amount + " hours before: " + finalReformattedDate);
                            for (StockUnit stockUnit : ((TimeSeriesResponse) e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(finalReformattedDate) <= 0) {
                                    retVal[0] = stockUnit.getClose();
                                    break;
                                }
                            }
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
                break;
            }
            case MINUTE: {
                calendar.add(Calendar.MINUTE, -amount);
                String reformattedDate = ( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) ).format( calendar.getTime() ); //Formats date into "yyyy-MM-dd HH:mm:ss" format
                reformattedDate = reformattedDate.substring(0, 11) + calendar.get(Calendar.HOUR_OF_DAY) + reformattedDate.substring(13); //Converts to 24 hr time
                String finalReformattedDate = reformattedDate;
                AlphaVantage.api()
                        .timeSeries()
                        .intraday()
                        .forSymbol(tickerSymbol)
                        .outputSize(OutputSize.FULL)
                        .interval(Interval.ONE_MIN)
                        .onSuccess(e-> {
//                            System.out.println(amount +" hour before: " + finalReformattedDate);
                            for (StockUnit stockUnit : ((TimeSeriesResponse)e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(finalReformattedDate) <= 0) {
                                    retVal[0] = stockUnit.getClose();
                                    break;
                                }
                            }
                        })
                        .onFailure(e->handleFailure(e))
                        .fetch();
                break;
            }
            case NOW: {
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
        }
        Timer.delay(Constants.API_CALL_INTERVAL); //Buffer period to prevent going over free api call limit.
        return retVal[0];
    }

    public void handleFailure(AlphaVantageException error) {
        System.out.println("Doesn't function");
        System.out.println(error.getMessage());
    }
}

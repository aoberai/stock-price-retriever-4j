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

    private StockPriceReader() {
    }

    public static StockPriceReader getInstance() {
        return sInstance;
    }

    public double getStockPrice(TimePeriod timePeriod, String tickerSymbol, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); //Sets time to current time
        double[] retVal = {0};
        switch (timePeriod) {
            case PRICE_YEAR: {
                calendar.add(Calendar.YEAR, -amount);

                AlphaVantage.api()
                        .timeSeries()
                        .monthly()
                        .adjusted()
                        .forSymbol(tickerSymbol)
                        .onSuccess(e -> {
                            double stockPriceSum = 0;
                            int stockUnitCount = 0;
                            for (StockUnit stockUnit : ((TimeSeriesResponse) e).getStockUnits()) {
                                if (Integer.parseInt(stockUnit.getDate().substring(0, 4)) == calendar.get(Calendar.YEAR)) {
                                    stockPriceSum += stockUnit.getAdjustedClose();
                                    stockUnitCount++;
                                    System.out.print(stockUnit);
                                }
                            }
                            retVal[0] = stockPriceSum / stockUnitCount;
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
                break;
            }
            case PRICE_MONTH: {
                calendar.add(Calendar.MONTH, -amount);

                AlphaVantage.api()
                        .timeSeries()
                        .daily()
                        .adjusted()
                        .forSymbol(tickerSymbol)
                        .outputSize(OutputSize.FULL)
                        .onSuccess(e -> {
                            double stockPriceSum = 0;
                            int stockUnitCount = 0;
                            for (StockUnit stockUnit : ((TimeSeriesResponse) e).getStockUnits()) {
                                if (Integer.parseInt(stockUnit.getDate().substring(5, 7)) == (calendar.get(Calendar.MONTH) + 1) && Integer.parseInt(stockUnit.getDate().substring(0, 4)) == calendar.get(Calendar.YEAR)) {
                                    stockPriceSum += stockUnit.getAdjustedClose();
                                    stockUnitCount++;
                                    System.out.print(stockUnit);
                                }
                            }
                            retVal[0] = stockPriceSum / stockUnitCount;
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
                break;
            }
            case PRICE_DAY: {
                calendar.add(Calendar.DATE, -amount);
                String reformattedDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()); //Formats date into "yyyy-MM-dd HH:mm:ss" format
                reformattedDate = reformattedDate.substring(0, 11) + calendar.get(Calendar.HOUR_OF_DAY) + reformattedDate.substring(13); //Converts to 24 hr time
                String finalReformattedDate = reformattedDate;
                AlphaVantage.api()
                        .timeSeries()
                        .daily()
                        .adjusted()
                        .forSymbol(tickerSymbol)
                        .outputSize(OutputSize.FULL)
                        .onSuccess(e -> {
                            for (StockUnit stockUnit : ((TimeSeriesResponse) e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(finalReformattedDate) <= 0) {
                                    retVal[0] = stockUnit.getAdjustedClose();
                                    System.out.print(stockUnit.toString());
                                    break;
                                }
                            }
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
                break;
            }
            case PRICE_HOUR: {
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
                            for (StockUnit stockUnit : ((TimeSeriesResponse) e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(finalReformattedDate) <= 0) {
                                    retVal[0] = stockUnit.getClose();
                                    System.out.print(stockUnit);
                                    break;
                                }
                            }
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
                break;
            }
            case PRICE_MINUTE: {
                calendar.add(Calendar.MINUTE, -amount);
                String reformattedDate = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(calendar.getTime()); //Formats date into "yyyy-MM-dd HH:mm:ss" format
                reformattedDate = reformattedDate.substring(0, 11) + calendar.get(Calendar.HOUR_OF_DAY) + reformattedDate.substring(13); //Converts to 24 hr time
                String finalReformattedDate = reformattedDate;

                AlphaVantage.api()
                        .timeSeries()
                        .intraday()
                        .forSymbol(tickerSymbol)
                        .outputSize(OutputSize.FULL)
                        .interval(Interval.ONE_MIN)
                        .onSuccess(e -> {
                            for (StockUnit stockUnit : ((TimeSeriesResponse) e).getStockUnits()) {
                                if (stockUnit.getDate().compareTo(finalReformattedDate) <= 0) {
                                    retVal[0] = stockUnit.getClose();
                                    System.out.print(stockUnit);
                                    break;
                                }
                            }
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
                break;
            }
            case PRICE_NOW: {
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
            case CHANGE_NOW: {
                AlphaVantage.api()
                        .timeSeries()
                        .quote()
                        .forSymbol(tickerSymbol)
                        .dataType(DataType.JSON)
                        .onSuccess(e -> {
                            retVal[0] = ((QuoteResponse) e).getChangePercent();
                        })
                        .onFailure(e -> handleFailure(e))
                        .fetch();
            }
        }
        Timer.delay(Constants.API_CALL_INTERVAL); //Buffer period to prevent going over free api call limit.
        return retVal[0];
    }

    public void handleFailure(AlphaVantageException error) {
        System.out.println("Doesn't function");
        System.out.println(error.getMessage());
    }

    public enum TimePeriod {
        PRICE_YEAR, PRICE_MONTH, PRICE_DAY, PRICE_HOUR, PRICE_MINUTE, PRICE_NOW, CHANGE_NOW;
    }
}

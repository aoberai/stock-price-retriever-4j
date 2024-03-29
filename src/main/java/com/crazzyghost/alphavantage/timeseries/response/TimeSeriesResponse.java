package com.crazzyghost.alphavantage.timeseries.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeSeriesResponse {


    private MetaData metaData;
    private List<StockUnit> stockUnits;
    private String errorMessage;

    private TimeSeriesResponse(MetaData metaData, List<StockUnit> stockUnits) {
        this.metaData = metaData;
        this.stockUnits = stockUnits;
        this.errorMessage = null;
    }

    private TimeSeriesResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.stockUnits = new ArrayList<>();
        this.metaData = MetaData.empty();
    }

    public static TimeSeriesResponse of(Map<String, Object> stringObjectMap, boolean adjusted) {
        Parser parser = new Parser(adjusted);
        return parser.parse(stringObjectMap);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public List<StockUnit> getStockUnits() {
        return stockUnits;
    }

    @Override
    public String toString() {
        return "TimeSeriesResponse{" +
                "metaData=" + metaData +
                ", stockUnits=" + stockUnits +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public static class Parser {


        private boolean adjusted;

        Parser(boolean adjusted) {
            this.adjusted = adjusted;
        }

        @SuppressWarnings("unchecked")
        TimeSeriesResponse parse(Map<String, Object> stringObjectMap) {

            List<String> keys = new ArrayList<>(stringObjectMap.keySet());

            Map<String, String> md;
            Map<String, Map<String, String>> stockData;

            try {
                md = (Map<String, String>) stringObjectMap.get(keys.get(0));
                stockData = (Map<String, Map<String, String>>) stringObjectMap.get(keys.get(1));

            } catch (ClassCastException ex) {
                return new TimeSeriesResponse((String) stringObjectMap.get(keys.get(0)));
            }

            MetaData metaData;
            if (md.get("4. Interval") == null) {
                metaData = new MetaData(
                        md.get("1. Information"),
                        md.get("2. Symbol"),
                        md.get("3. Last Refreshed"),
                        md.get("4. Output Size"),
                        md.get("5. Time Zone")
                );
            } else {

                metaData = new MetaData(
                        md.get("1. Information"),
                        md.get("2. Symbol"),
                        md.get("3. Last Refreshed"),
                        md.get("4. Interval"),
                        md.get("5. Output Size"),
                        md.get("6. Time Zone")
                );
            }

            List<StockUnit> stockUnits = new ArrayList<>();


            for (Map.Entry<String, Map<String, String>> e : stockData.entrySet()) {

                Map<String, String> m = e.getValue();
                StockUnit.Builder stockUnit = new StockUnit.Builder();
                stockUnit.time(e.getKey());
                stockUnit.open(Double.parseDouble(m.get("1. open")));
                stockUnit.high(Double.parseDouble(m.get("2. high")));
                stockUnit.low(Double.parseDouble(m.get("3. low")));
                stockUnit.close(Double.parseDouble(m.get("4. close")));
                if (!adjusted) {
                    stockUnit.volume(Long.parseLong(m.get("5. volume")));
                } else {
                    stockUnit.adjustedClose(Double.parseDouble(m.get("5. adjusted close")));
                    stockUnit.volume(Long.parseLong(m.get("6. volume")));
                    stockUnit.dividendAmount(Double.parseDouble(m.get("7. dividend amount")));
                    if (m.get("8. split coefficient") != null)
                        stockUnit.splitCoefficient(Double.parseDouble(m.get("8. split coefficient")));
                }
                stockUnits.add(stockUnit.build());

            }

            return new TimeSeriesResponse(metaData, stockUnits);
        }
    }
}


package com.crazzyghost.alphavantage.forex.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForexResponse {


    private MetaData metaData;
    private List<ForexUnit> forexUnits;
    private String errorMessage;

    private ForexResponse(MetaData metaData, List<ForexUnit> forexUnits) {
        this.metaData = metaData;
        this.forexUnits = forexUnits;
        this.errorMessage = null;
    }

    private ForexResponse(String errorMessage) {
        this.metaData = MetaData.empty();
        this.forexUnits = new ArrayList<>();
        this.errorMessage = errorMessage;
    }

    public static ForexResponse of(Map<String, Object> stringObjectMap) {
        Parser parser = new Parser();
        return parser.parse(stringObjectMap);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public List<ForexUnit> getForexUnits() {
        return forexUnits;
    }

    @Override
    public String toString() {
        return "ForexResponse{" +
                "metaData=" + metaData +
                ", forexUnits=" + forexUnits.size() +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    public static class Parser {
        @SuppressWarnings("unchecked")
        ForexResponse parse(Map<String, Object> stringObjectMap) {

            List<String> keys = new ArrayList<>(stringObjectMap.keySet());

            Map<String, String> md;
            Map<String, Map<String, String>> stockData;

            try {
                md = (Map<String, String>) stringObjectMap.get(keys.get(0));
                stockData = (Map<String, Map<String, String>>) stringObjectMap.get(keys.get(1));

            } catch (ClassCastException e) {
                return new ForexResponse((String) stringObjectMap.get(keys.get(0)));
            }


            MetaData metaData = new MetaData(
                    md.get("1. Information"),
                    md.get("2. From Symbol"),
                    md.get("3. To Symbol"),
                    md.get("4. Last Refreshed"),
                    md.get("5. Interval"),
                    md.get("6. Output Size"),
                    md.get("7. Time Zone")
            );

            List<ForexUnit> forexUnits = new ArrayList<>();

            for (Map.Entry<String, Map<String, String>> e : stockData.entrySet()) {

                ForexUnit.Builder forexUnit = new ForexUnit.Builder();
                Map<String, String> m = e.getValue();
                forexUnit.date(e.getKey());
                forexUnit.open(Double.parseDouble(m.get("1. open")));
                forexUnit.high(Double.parseDouble(m.get("2. high")));
                forexUnit.low(Double.parseDouble(m.get("3. low")));
                forexUnit.close(Double.parseDouble(m.get("4. close")));
                forexUnits.add(forexUnit.build());
            }
            return new ForexResponse(metaData, forexUnits);
        }
    }
}

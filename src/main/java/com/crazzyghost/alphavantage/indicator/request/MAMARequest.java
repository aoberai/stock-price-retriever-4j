package com.crazzyghost.alphavantage.indicator.request;

import com.crazzyghost.alphavantage.parameters.Function;
import com.crazzyghost.alphavantage.parameters.SeriesType;

public class MAMARequest extends IndicatorRequest {

    private SeriesType series_type;
    private double fastLimit;
    private double slowLimit;

    private MAMARequest(Builder builder) {
        super(builder);
        this.fastLimit = builder.fastLimit;
        this.slowLimit = builder.slowLimit;
        this.series_type = builder.seriesType;
    }

    public static class Builder extends IndicatorRequest.Builder<Builder> {

        private double fastLimit = 0.1;
        private double slowLimit = 0.1;
        private SeriesType seriesType;

        public Builder() {
            this.function(Function.MAMA);
        }

        public Builder fastLimit(double fastLimit) {
            this.fastLimit = fastLimit;
            return this;
        }

        public Builder slowLimit(double slowLimit) {
            this.slowLimit = slowLimit;
            return this;
        }

        public Builder seriesType(SeriesType seriesType) {
            this.seriesType = seriesType;
            return this;
        }

        @Override
        public IndicatorRequest build() {
            return new MAMARequest(this);
        }


    }


}
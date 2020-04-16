package com.crazzyghost.alphavantage.exchangerate;

import com.crazzyghost.alphavantage.parameters.Function;

public class ExchangeRateRequest {


    private Function function;
    private String from_currency;
    private String to_currency;


    private ExchangeRateRequest(Builder builder){
        this.function = builder.function;
        this.from_currency = builder.fromCurrency;
        this.to_currency = builder.toCurrency;
    }

    public Function getFunction(){
        return this.function;
    }

    public String getFromCurrency(){
        return this.from_currency;
    }

    public String getToCurrency(){
        return this.to_currency;
    }

    public static Builder builder(){
        return  new Builder();
    }

    public static class Builder{
        Function function;
        String fromCurrency;
        String toCurrency;

        public Builder(){
            this.function = Function.CURRENCY_EXCHANGE_RATE;
        }

        public Builder fromCurrency(String fromCurrency){
            this.fromCurrency = fromCurrency;
            return this;
        }

        public Builder toCurrency(String toCurrency){
            this.toCurrency = toCurrency;
            return this;
        }

        public ExchangeRateRequest build(){
            return new ExchangeRateRequest(this);
        }

    }
}

package com.crazzyghost.alphavantage;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Allows you to set the library configuration parameters.
 *
 * @author crazzyghost
 * @since 1.0.0
 */
public class Config {


    public static String BASE_URL = "https://www.alphavantage.co/query?";
    private String key;
    private int timeOut;
    private OkHttpClient httpClient;

    private Config(Builder builder) {
        this.key = builder.key;
        this.timeOut = builder.timeOut;
        this.httpClient = builder.httpClient == null ? defaultClient(builder.timeOut) : builder.httpClient;
    }

    /**
     * Get a builder instance
     *
     * @return {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @return the connection timeout value
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * @return the API Key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return the HTTP client used to fetch data
     */
    public OkHttpClient getOkHttpClient() {
        return this.httpClient;
    }

    /**
     * @param timeOut connect timeout
     * @return a default HTTP client for fetching data
     */
    private OkHttpClient defaultClient(int timeOut) {
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Builder class for {@link Config}
     */
    public static class Builder {
        private String key;
        private int timeOut;
        private OkHttpClient httpClient;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        /**
         * Set the connect timeout of the HTTP client
         *
         * @param timeOut timeout for config
         * @return the builder instance
         */
        public Builder timeOut(int timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        /**
         * Set the HTTP client
         *
         * @param httpClient the HTTP client for config
         * @return
         */
        public Builder httpClient(OkHttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Build a new Configuration instance
         *
         * @return {@link Config} instance
         */
        public Config build() {
            return new Config(this);
        }
    }
}

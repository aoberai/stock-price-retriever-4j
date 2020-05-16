package com.aoberai.stocktradingbot.utils;

public class Timer {
    public static void delay(Long delayMs) {
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

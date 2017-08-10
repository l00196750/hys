package com.hys.common.utils;

import java.time.LocalDate;

import com.google.common.base.Strings;

public class LocalDates {

    public static String now(String separator) {
        separator = Strings.nullToEmpty(separator);
        LocalDate now = LocalDate.now();

        int yearValue = now.getYear();
        int monthValue = now.getMonthValue();
        int dayValue = now.getDayOfMonth();
        int absYear = Math.abs(yearValue);
        StringBuilder buf = new StringBuilder(10);
        if (absYear < 1000) {
            if (yearValue < 0) {
                buf.append(yearValue - 10000).deleteCharAt(1);
            }
            else {
                buf.append(yearValue + 10000).deleteCharAt(0);
            }
        }
        else {
            if (yearValue > 9999) {
                buf.append('+');
            }
            buf.append(yearValue);
        }

        buf.append(separator).append(monthValue < 10 ? "0" : "").append(monthValue);
        buf.append(separator).append(dayValue < 10 ? "0" : "").append(dayValue);

        return buf.toString();
    }
}

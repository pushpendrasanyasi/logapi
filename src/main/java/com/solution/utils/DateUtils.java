package com.solution.utils;

import java.time.LocalDate;
import java.time.Period;

public class DateUtils {

    public static int getDateDifference(LocalDate fromDate, LocalDate toDate) {
        Period period = Period.between(fromDate, toDate);
        return Math.abs(period.getDays());
    }

}

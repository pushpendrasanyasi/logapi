package com.solution.data.dao;

import java.time.LocalDate;
import java.util.List;

public interface LogRepository {
    List<LocalDate> getDatesForMissingData(LocalDate fromDate, LocalDate toDate);
}

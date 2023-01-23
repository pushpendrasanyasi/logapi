package com.solution.services;

import com.solution.common.enums.LogProcessingQueryType;
import com.solution.data.dto.response.RawLogResponse;

import java.time.LocalDate;

public interface LogsService {
    RawLogResponse process(LogProcessingQueryType type, LocalDate fromDate, LocalDate toDate);
}

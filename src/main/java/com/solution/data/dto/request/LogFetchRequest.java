package com.solution.data.dto.request;

import com.solution.common.enums.LogProcessingQueryType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LogFetchRequest {
    private LogProcessingQueryType type;
    private LocalDate fromDate;
    private LocalDate toDate;
}

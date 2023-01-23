package com.solution.controller;

import com.solution.common.constants.APIConstants;
import com.solution.common.enums.LogProcessingQueryType;
import com.solution.data.dto.response.RawLogResponse;
import com.solution.data.dto.response.RegexResponse;
import com.solution.services.LogsService;
import com.solution.services.RegexPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AppController {
    private final RegexPatternService regexPatternService;

    private final LogsService logsService;

    @Autowired
    public AppController(RegexPatternService regexPatternService, LogsService logsService) {
        this.regexPatternService = regexPatternService;
        this.logsService = logsService;
    }

    @GetMapping(value = APIConstants.REGEX_PATTERN_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public RegexResponse getRegexPatterns() {
        return regexPatternService.getStoredRegex();
    }

    @GetMapping(value = APIConstants.RAW_LOGS_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RawLogResponse> getRawLogs(
            @RequestParam(value = "from_date", required = true, defaultValue = "false")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "to_date", required = true, defaultValue = "false")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        RawLogResponse response = logsService.process(LogProcessingQueryType.RAW_LOG, fromDate, toDate);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = APIConstants.SENSITIVE_LOGS_API, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RawLogResponse> getFilteredLogs(
            @RequestParam(value = "from_date", required = true, defaultValue = "false")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(value = "to_date", required = true, defaultValue = "false")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate
    ) {
        RawLogResponse response = logsService.process(LogProcessingQueryType.REGEX_BASED_LOGS, fromDate, toDate);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}

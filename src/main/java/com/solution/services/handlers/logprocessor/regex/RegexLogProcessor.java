package com.solution.services.handlers.logprocessor.regex;

import com.solution.common.constants.ApplicationConstants;
import com.solution.common.constants.ApplicationProperties;
import com.solution.data.dto.request.LogFetchRequest;
import com.solution.helpers.LogPathHelper;
import com.solution.services.handlers.logprocessor.LogProcessor;
import com.solution.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RegexLogProcessor extends LogProcessor {

    private final LogPathHelper logPathHelper;
    private final LogFileHandler logFileHandler;

    private final ApplicationProperties applicationProperties;

    @Autowired
    public RegexLogProcessor(LogPathHelper logPathHelper,
                             LogFileHandler logFileHandler,
                             ApplicationProperties applicationProperties) {
        this.logPathHelper = logPathHelper;
        this.logFileHandler = logFileHandler;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public List<String> processAndReturnOutputFiles(LogFetchRequest logFetchRequest) {
        List<String> output = new ArrayList<>();
        LocalDate fromDate = logFetchRequest.getFromDate();
        LocalDate toDate = logFetchRequest.getToDate();

        int diff = DateUtils.getDateDifference(fromDate, toDate);

        LocalDate curr = fromDate;
        while (diff >= 0) {
            List<String> relativeFilePaths = logPathHelper.getRawLogFilesForDay(curr);
            List<String> outFilePaths = filterAndCreateLogs(relativeFilePaths);
            List<String> relativePaths = outFilePaths.stream().map(this::relativePath).collect(Collectors.toList());
            output.addAll(relativePaths);
            curr = curr.plusDays(1);
            --diff;
        }
        return output;

    }

    private List<String> filterAndCreateLogs(List<String> paths) {
        List<String> out = new ArrayList<>();
        for (String path : paths) {
            String fullInputPath = buildInputFilePath(path);
            String outFilePath = buildOutputFilePath(path);
            int sensitiveLines = 0;
            try {
                sensitiveLines = logFileHandler.regexFilterLogFileAndCreateNewFile(fullInputPath, outFilePath);
            } catch (IOException e) {
                log.error("IOException while filtering the log file at path: {}", path, e);
                continue;
            }
            if (sensitiveLines > 0) {
                out.add(outFilePath);
            }
        }
        return out;
    }

    private String buildInputFilePath(String relativeInputPath) {
        String[] tokens = relativeInputPath.split(ApplicationConstants.LogDirectoryNames.RAW_LOG_DIR_NAME);
        String relativeFilePath = tokens[1];
        return applicationProperties.getRawLogBasePath() + File.separatorChar + relativeFilePath;
    }

    private String buildOutputFilePath(String relativeInputPath) {
        String[] tokens = relativeInputPath.split(ApplicationConstants.LogDirectoryNames.RAW_LOG_DIR_NAME);
        String relativeFilePath = tokens[1];
        return applicationProperties.getProcessedLogBasePath() + relativeFilePath;
    }

    private String relativePath(String fullPath) {
        String[] tokens = fullPath.split(ApplicationConstants.LogDirectoryNames.PROCESSED_LOG_DIR_NAME);
        return File.separatorChar + ApplicationConstants.LogDirectoryNames.PROCESSED_LOG_DIR_NAME + tokens[1];
    }
}

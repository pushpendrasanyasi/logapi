package com.solution.helpers;

import com.solution.common.constants.ApplicationConstants;
import com.solution.common.constants.ApplicationProperties;
import com.solution.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogPathHelper {

    private final ApplicationProperties properties;
    private final String DATE_FORMAT = "yyyy-MM-dd";

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    @Autowired
    public LogPathHelper(ApplicationProperties properties) {
        this.properties = properties;
    }

    public String getDateDirectoryName(LocalDate localDate) {
        return formatter.format(localDate);
    }

    public List<String> getRawLogFilesForDay(LocalDate date) {
        String rawLogBasePath = properties.getRawLogBasePath();
        String dateDirectoryName = getDateDirectoryName(date);
        String dirPath = rawLogBasePath + File.separatorChar + dateDirectoryName;
        List<String> fileNames = FileUtils.getFileNamesInDirectory(dirPath);
        return fileNames.stream().map(name -> File.separatorChar + ApplicationConstants.LogDirectoryNames.RAW_LOG_DIR_NAME
                        + File.separatorChar + dateDirectoryName
                        + File.separatorChar + name)
                .collect(Collectors.toList());
    }

    public List<String> transformToOutputFilePath(List<String> filePaths) {
        String endpoint = properties.getEndpoint();
        return filePaths.stream().map(name -> endpoint + name).collect(Collectors.toList());
    }

}

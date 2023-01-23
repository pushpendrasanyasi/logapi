package com.solution.common.constants;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Data
@Configuration
public class ApplicationProperties {

    public static final String REGEX_FILE_PATH = "C:\\Users\\pushp\\IdeaProjects\\LogApi\\src\\main\\resources\\SavedPatterns.csv";
    public static final String DOWNLOADED_RAW_LOG_PATH = "C:\\Users\\pushp\\IdeaProjects\\LogApi\\runtime\\download\\raw";
    public static final String PID_FILE_DIR_PATH = "C:\\Users\\pushp\\IdeaProjects\\LogApi\\runtime\\pid";

    @Value("${local-disk.log.path}")
    private String logBasePath;

    private String rawLogBasePath;
    
    private String processedLogBasePath;

    @Value("${local-disk.output.log.base.path}")
    private String outputLogBasePath;

    @Value("${endpoint}")
    private String endpoint;

    @PostConstruct
    public void populateRawLogBasePath() {
        this.rawLogBasePath = logBasePath + File.separatorChar
                + ApplicationConstants.LogDirectoryNames.RAW_LOG_DIR_NAME;
        this.processedLogBasePath = logBasePath + File.separatorChar
                + ApplicationConstants.LogDirectoryNames.PROCESSED_LOG_DIR_NAME;
    }

}

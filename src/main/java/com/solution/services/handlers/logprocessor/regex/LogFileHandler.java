package com.solution.services.handlers.logprocessor.regex;

import com.solution.data.dao.RegexStorageEngine;
import com.solution.data.dto.response.RegexEntity;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LogFileHandler {

    private final RegexStorageEngine regexStorageEngine;
    private final RegexMatcher regexMatcher;

    @Autowired
    public LogFileHandler(RegexStorageEngine regexStorageEngine,
                          RegexMatcher regexMatcher) {
        this.regexStorageEngine = regexStorageEngine;
        this.regexMatcher = regexMatcher;
    }

    public int regexFilterLogFileAndCreateNewFile(String rawLogPath, String outFilePath) throws IOException {
        List<String> regexes = getSensitiveRegexPatterns();
        List<String> sensitiveLines = regexMatcher.readFileAndfilterSensitiveLines(regexes, rawLogPath);
        if (sensitiveLines.size() > 0) {
            writeToOutputFile(sensitiveLines, outFilePath);
        }
        return sensitiveLines.size();
    }

    private void writeToOutputFile(List<String> sensitiveLines, String outFilePath) throws IOException {
        File outFile = new File(outFilePath);
        File parent = outFile.getParentFile();
        if (parent.mkdirs() && outFile.createNewFile()) {
            FileUtils.writeLines(outFile, sensitiveLines);
        }
    }

    private List<String> getSensitiveRegexPatterns() {
        List<RegexEntity> regexEntities = regexStorageEngine.getStoredRegex();
        return regexEntities.stream().map(RegexEntity::getPattern).collect(Collectors.toList());
    }


}

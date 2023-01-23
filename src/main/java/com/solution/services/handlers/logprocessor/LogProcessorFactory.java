package com.solution.services.handlers.logprocessor;

import com.solution.common.enums.LogProcessingQueryType;
import com.solution.services.handlers.logprocessor.raw.RawLogProcessor;
import com.solution.services.handlers.logprocessor.regex.RegexLogProcessor;
import org.springframework.stereotype.Component;

@Component
public class LogProcessorFactory {

    private final RawLogProcessor rawLogProcessor;
    private final RegexLogProcessor regexLogProcessor;

    public LogProcessorFactory(RawLogProcessor rawLogProcessor,
                               RegexLogProcessor regexLogProcessor) {
        this.rawLogProcessor = rawLogProcessor;
        this.regexLogProcessor = regexLogProcessor;
    }


    public LogProcessor getLogProcessor(LogProcessingQueryType type) {
        LogProcessor logProcessor;
        switch (type) {
            case RAW_LOG:
                logProcessor = rawLogProcessor;
                break;
            case REGEX_BASED_LOGS:
                logProcessor = regexLogProcessor;
                break;
            default:
                logProcessor = null;
        }
        return logProcessor;
    }

}

package com.solution.services.handlers.logprocessor;

import com.solution.data.dto.request.LogFetchRequest;

import java.util.List;

public abstract class LogProcessor {

    public abstract List<String> processAndReturnOutputFiles(LogFetchRequest LogFetchRequest);
}

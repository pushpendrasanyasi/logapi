package com.solution.services.impl;

import com.solution.common.enums.LogProcessingQueryType;
import com.solution.common.enums.LogRequestStatus;
import com.solution.data.dto.request.LogFetchRequest;
import com.solution.data.dto.response.RawLogResponse;
import com.solution.data.entities.RequestStatus;
import com.solution.helpers.LogPathHelper;
import com.solution.services.LogsService;
import com.solution.services.RequestManager;
import com.solution.services.handlers.logprocessor.LogProcessor;
import com.solution.services.handlers.logprocessor.LogProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@EnableAsync
@Service
public class LogsServiceImpl implements LogsService {

    private final LogProcessorFactory logProcessorFactory;

    private final RequestManager requestManager;
    private final LogPathHelper logPathHelper;

    @Autowired
    public LogsServiceImpl(LogProcessorFactory logProcessorFactory,
                           RequestManager requestManager,
                           LogPathHelper logPathHelper) {
        this.logProcessorFactory = logProcessorFactory;
        this.requestManager = requestManager;
        this.logPathHelper = logPathHelper;
    }

    @Override
    public RawLogResponse process(LogProcessingQueryType type, LocalDate fromDate, LocalDate toDate) {
        List<String> logFilePaths = null;
        LogRequestStatus status = LogRequestStatus.IN_PROGRESS;
        LogFetchRequest request = LogFetchRequest.builder().type(type).fromDate(fromDate).toDate(toDate).build();
        //Create RequestId for request
        String requestId = requestManager.generateRequestId(request);
        //get a lock on request id for processing
        synchronized (requestId) {
            RequestStatus currStatus = requestManager.getRequestStatus(requestId);
            if (currStatus != null && currStatus.getStatus() == LogRequestStatus.COMPLETED) {
                status = LogRequestStatus.COMPLETED;
                logFilePaths = currStatus.getResultFilePaths();
                logFilePaths = logPathHelper.transformToOutputFilePath(logFilePaths);
            }
            if (currStatus == null) {
                requestManager.setRequestStatus(requestId, status);
                triggerProcessing(request, requestId);
            }
        }
        RawLogResponse.ResponseStatus responseStatus = RawLogResponse.ResponseStatus.builder()
                .status(status.toString())
                .requestId(requestId)
                .build();
        return RawLogResponse.builder().responseStatus(responseStatus).logFilePaths(logFilePaths).build();
    }

    @Async
    void triggerProcessing(LogFetchRequest request, String requestId) {
        LogProcessor logProcessor = logProcessorFactory.getLogProcessor(request.getType());
        List<String> fileNames = logProcessor.processAndReturnOutputFiles(request);
        requestManager.updateLogsForRequest(requestId, fileNames);
    }


}

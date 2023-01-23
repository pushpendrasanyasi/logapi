package com.solution.services.impl;

import com.solution.common.enums.LogRequestStatus;
import com.solution.data.dao.RequestRepository;
import com.solution.data.dto.request.LogFetchRequest;
import com.solution.data.entities.RequestStatus;
import com.solution.services.RequestManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RequestManagerImpl implements RequestManager {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestManagerImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public String generateRequestId(LogFetchRequest request) {
        return createRequestId(request);
    }

    @Override
    public RequestStatus getRequestStatus(String requestId) {
        return requestRepository.getStatus(requestId);
    }

    @Override
    public void setRequestStatus(String requestId, LogRequestStatus status) {
        requestRepository.setStatus(requestId, status);
    }

    @Override
    public void updateLogsForRequest(String requestId, List<String> logPaths) {
        requestRepository.putStatusAndLogDir(requestId, LogRequestStatus.COMPLETED, logPaths);
    }

    private String createRequestId(LogFetchRequest request) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fromDateString = formatter.format(request.getFromDate());
        String toDateString = formatter.format(request.getToDate());
        return request.getType() + "#" + fromDateString + "#" + toDateString;
    }

}

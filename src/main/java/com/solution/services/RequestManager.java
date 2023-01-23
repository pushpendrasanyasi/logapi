package com.solution.services;

import com.solution.common.enums.LogRequestStatus;
import com.solution.data.dto.request.LogFetchRequest;
import com.solution.data.entities.RequestStatus;

import java.util.List;

public interface RequestManager {

    String generateRequestId(LogFetchRequest request);

    RequestStatus getRequestStatus(String requestId);

    void setRequestStatus(String requestId, LogRequestStatus status);

    void updateLogsForRequest(String requestId, List<String> logPaths);
}

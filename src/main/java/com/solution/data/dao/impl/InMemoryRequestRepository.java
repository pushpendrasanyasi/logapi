package com.solution.data.dao.impl;

import com.solution.common.enums.LogRequestStatus;
import com.solution.data.dao.RequestRepository;
import com.solution.data.entities.RequestStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryRequestRepository implements RequestRepository {

    private final Map<String, RequestStatus> store = new ConcurrentHashMap<>();

    @Override
    public void setStatus(String requestId, LogRequestStatus status) {
        RequestStatus statusObj = RequestStatus.builder().status(status).build();
        store.put(requestId, statusObj);
    }

    @Override
    public void putStatusAndLogDir(String requestId, LogRequestStatus status, List<String> logDir) {
        RequestStatus statusObj = RequestStatus.builder().status(status).resultFilePaths(logDir).build();
        store.put(requestId, statusObj);
    }

    @Override
    public RequestStatus getStatus(String requestId) {
        RequestStatus status = store.get(requestId);
        return status;
    }
}

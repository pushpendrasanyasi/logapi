package com.solution.data.dao;


import com.solution.common.enums.LogRequestStatus;
import com.solution.data.entities.RequestStatus;

import java.util.List;

public interface RequestRepository {

    void setStatus(String requestId, LogRequestStatus status);

    void putStatusAndLogDir(String requestId, LogRequestStatus status, List<String> logDir);

    RequestStatus getStatus(String requestId);

}

package com.solution.data.entities;

import com.solution.common.enums.LogRequestStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RequestStatus {

    private LogRequestStatus status;

    private List<String> resultFilePaths;

}

package com.solution.data.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RawLogResponse {

    private ResponseStatus responseStatus;

    private List<String> logFilePaths;

    @Builder
    @Data
    public static class ResponseStatus {
        private String requestId;
        private String status;
    }
}

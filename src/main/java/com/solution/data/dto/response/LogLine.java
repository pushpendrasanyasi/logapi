package com.solution.data.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LogLine {

    @JsonProperty("time")
    String time;
    @JsonProperty("message")
    String message;
    @JsonProperty("log_stream_name")
    String logStream;
}

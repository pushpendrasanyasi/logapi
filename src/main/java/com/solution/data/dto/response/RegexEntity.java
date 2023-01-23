package com.solution.data.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegexEntity {
    String name;
    String pattern;
}

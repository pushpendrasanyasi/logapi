package com.solution.data.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegexResponse {

    List<RegexEntity> patterns;

}

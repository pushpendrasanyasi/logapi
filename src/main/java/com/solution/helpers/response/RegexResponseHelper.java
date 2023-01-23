package com.solution.helpers.response;

import com.solution.data.dto.response.RegexEntity;
import com.solution.data.dto.response.RegexResponse;

import java.util.List;

public class RegexResponseHelper {

    public static RegexResponse buildRegexResponse(List<RegexEntity> entities) {
        return RegexResponse.builder()
                .patterns(entities)
                .build();
    }

}

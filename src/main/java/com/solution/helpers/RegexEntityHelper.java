package com.solution.helpers;

import com.solution.data.dto.response.RegexEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RegexEntityHelper {

    public static List<RegexEntity> transformToRegexEntity(List<String> list) {
        List<RegexEntity> out = Collections.emptyList();
        if (list == null) {
            return out;
        }
        out = list.stream().filter(StringUtils::isNotBlank).map(RegexEntityHelper::stringToRegex).collect(Collectors.toList());
        return out;
    }

    private static RegexEntity stringToRegex(String line) {
        String[] tokens = line.split(",");
        String name = StringUtils.trim(tokens[0]);
        String pattern = StringUtils.trim(tokens[1]);
        return RegexEntity.builder().name(name).pattern(pattern).build();
    }

}

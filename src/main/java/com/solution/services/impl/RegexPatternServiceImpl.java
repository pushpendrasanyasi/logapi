package com.solution.services.impl;

import com.solution.data.dao.RegexStorageEngine;
import com.solution.data.dto.response.RegexEntity;
import com.solution.data.dto.response.RegexResponse;
import com.solution.helpers.response.RegexResponseHelper;
import com.solution.services.RegexPatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegexPatternServiceImpl implements RegexPatternService {
    private final RegexStorageEngine regexStorageEngine;

    @Autowired
    public RegexPatternServiceImpl(RegexStorageEngine regexStorageEngine) {
        this.regexStorageEngine = regexStorageEngine;
    }

    @Override
    public RegexResponse getStoredRegex() {
        List<RegexEntity> entities = regexStorageEngine.getStoredRegex();
        return RegexResponseHelper.buildRegexResponse(entities);
    }
}

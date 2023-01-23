package com.solution.data.dao.impl;

import com.solution.common.constants.ApplicationProperties;
import com.solution.data.dao.RegexStorageEngine;
import com.solution.data.dto.response.RegexEntity;
import com.solution.helpers.RegexEntityHelper;
import com.solution.utils.FileUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FileRegexStorageRepository implements RegexStorageEngine {

    @Override
    public List<RegexEntity> getStoredRegex() {
        List<String> lines = FileUtils.readFile(ApplicationProperties.REGEX_FILE_PATH);
        return RegexEntityHelper.transformToRegexEntity(lines);
    }

}

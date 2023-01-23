package com.solution.data.dao;

import com.solution.data.dto.response.RegexEntity;

import java.util.List;

public interface RegexStorageEngine {

    List<RegexEntity> getStoredRegex();

}

package com.solution.data.dao.impl;

import com.solution.common.constants.ApplicationProperties;
import com.solution.data.dao.LogRepository;
import com.solution.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class LocalDiskLogRepository implements LogRepository {

    public List<LocalDate> getDatesForMissingData(LocalDate fromDate, LocalDate toDate) {
        List<LocalDate> missingDates = new ArrayList<>();
        String basePath = ApplicationProperties.DOWNLOADED_RAW_LOG_PATH;
        File dayLogDir;

        int diff = DateUtils.getDateDifference(fromDate, toDate);

        LocalDate tempDate = fromDate;
        while (diff > 0) {
            String dirPath = dayDirectoryPath(basePath, tempDate);
            dayLogDir = new File(dirPath);
            if ((dayLogDir.exists() && dayLogDir.isDirectory())) {
                log.info("processing done or already started for date {}", tempDate.toString());
            } else {
                missingDates.add(LocalDate.from(tempDate));
            }
            tempDate = tempDate.plusDays(1);
            --diff;
        }
        return missingDates;
    }

    private String dayDirectoryPath(String basePath, LocalDate date) {
        String dateString = DateTimeFormatter.ISO_DATE.format(date);
        return basePath + File.separatorChar + dateString;
    }
}

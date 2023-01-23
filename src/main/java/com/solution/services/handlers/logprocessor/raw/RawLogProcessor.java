package com.solution.services.handlers.logprocessor.raw;

import com.solution.data.dto.request.LogFetchRequest;
import com.solution.helpers.LogPathHelper;
import com.solution.services.handlers.logprocessor.LogProcessor;
import com.solution.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RawLogProcessor extends LogProcessor {

    private final LogPathHelper logPathHelper;

    public RawLogProcessor(LogPathHelper logPathHelper) {
        this.logPathHelper = logPathHelper;
    }

    @Override
    public List<String> processAndReturnOutputFiles(LogFetchRequest logFetchRequest) {
        LocalDate fromDate = logFetchRequest.getFromDate();
        LocalDate toDate = logFetchRequest.getToDate();
        List<String> output = new ArrayList<>();
        int diff = DateUtils.getDateDifference(fromDate, toDate);

        LocalDate curr = fromDate;
        while (diff > 0) {
            List<String> files = logPathHelper.getRawLogFilesForDay(curr);
            output.addAll(files);
            curr = curr.plusDays(1);
            --diff;
        }
        return output;
    }

}

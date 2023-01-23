package com.solution.services.handlers;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LogsProcessHandler {

    public void startProcess() {
    }

    public void checkRunningProcess() {
    }

    private String getPidFileName(Date fromDate, Date toDate) {
        Date temp = new Date();
        while (temp.before(toDate)) {

        }
        return null;
    }

}

package com.hamiliserver.sampleapp.async;

import com.hamiliserver.sampleapp.model.Holiday;

import java.util.List;

public interface HolidayCallback {
    void holidays(List<Holiday> holidayList);
}

package com.gmail.klokovsergey.common.entity.tasks;

import com.gmail.klokovsergey.common.entity.interfaceTask.YearlyRepeatableInterface;

import java.time.LocalDate;

public class YearlyTask extends Task implements YearlyRepeatableInterface {
    @Override
    public boolean isRepeat() {
        return false;
    }

    @Override
    public LocalDate getNextNotification() {
        return null;
    }
}

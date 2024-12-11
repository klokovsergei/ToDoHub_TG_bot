package com.gmail.klokovsergey.common.entity.tasks;

import com.gmail.klokovsergey.common.entity.interfaceTask.MonthlyRepeatableInterface;

import java.time.LocalDate;

public class MonthlyTask extends Task implements MonthlyRepeatableInterface {
    @Override
    public boolean isRepeat() {
        return false;
    }

    @Override
    public LocalDate getNextNotification() {
        return null;
    }
}

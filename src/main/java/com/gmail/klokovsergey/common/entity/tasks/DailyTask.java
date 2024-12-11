package com.gmail.klokovsergey.common.entity.tasks;

import com.gmail.klokovsergey.common.entity.interfaceTask.DailyRepeatableInterface;

import java.time.LocalDate;

public class DailyTask extends Task implements DailyRepeatableInterface {
    @Override
    public boolean isRepeat() {
        return false;
    }

    @Override
    public LocalDate getNextNotification() {
        return null;
    }
}

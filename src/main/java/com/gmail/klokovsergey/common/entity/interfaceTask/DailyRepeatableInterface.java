package com.gmail.klokovsergey.common.entity.interfaceTask;

import java.time.LocalDate;

public interface DailyRepeatableInterface extends Repeatable{
    LocalDate getNextNotification();
}

package com.gmail.klokovsergey.common.entity.interfaceTask;

import java.time.LocalDate;

public interface YearlyRepeatableInterface extends Repeatable{
    LocalDate getNextNotification();
}

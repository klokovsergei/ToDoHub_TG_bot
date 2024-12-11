package com.gmail.klokovsergey.common.entity.interfaceTask;

import java.time.LocalDate;

public interface MonthlyRepeatableInterface extends Repeatable{
    LocalDate getNextNotification();
}

package com.gmail.klokovsergey.common.entity.tasks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Task implements Externalizable {

    //region Поля
    private long userTgId;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private LocalDateTime dataTimeNotification;
    private String title;
    private String description;
    private boolean status;

    //endregion

    //region Методы



    //endregion

    //region Override методы записи и чтения
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(userTgId);
        out.writeObject(dateStart);
        out.writeObject(dateFinish);
        out.writeObject(dataTimeNotification);
        out.writeObject(title);
        out.writeObject(description);
        out.writeBoolean(status);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        userTgId = in.readLong();
        dateStart = (LocalDate) in.readObject();
        dateFinish = (LocalDate) in.readObject();
        dataTimeNotification = (LocalDateTime) in.readObject();
        title = (String) in.readObject();
        description = (String) in.readObject();
        status = in.readBoolean();
    }

    //endregion

    //region Конструкторы

    //endregion
}

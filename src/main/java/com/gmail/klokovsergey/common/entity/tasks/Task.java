package com.gmail.klokovsergey.common.entity.tasks;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Task implements Externalizable {

    //region Поля
    private LocalDate dateCreate;
    private LocalDate dateFinish;
    private LocalDate dateNotification;
    private LocalTime timeNotification;
    private String title;
    private boolean status;

    //endregion

    //region Override методы записи и чтения
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(dateCreate);
        out.writeObject(dateFinish);
        out.writeObject(dateNotification);
        out.writeObject(timeNotification);
        out.writeObject(title);
        out.writeBoolean(status);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        dateCreate = (LocalDate) in.readObject();
        dateFinish = (LocalDate) in.readObject();
        dateNotification = (LocalDate) in.readObject();
        timeNotification = (LocalTime) in.readObject();
        title = (String) in.readObject();
        status = in.readBoolean();
    }

    @Override
    public String toString() {
        return "Task{" +
                "dateCreate=" + dateCreate +
                ", dateFinish=" + dateFinish +
                ", dataNotification=" + dateNotification +
                ", timeNotification=" + timeNotification +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return status == task.status && Objects.equals(dateCreate, task.dateCreate)
                && Objects.equals(dateFinish, task.dateFinish) && Objects.equals(dateNotification, task.dateNotification)
                && Objects.equals(timeNotification, task.timeNotification) && Objects.equals(title, task.title);
    }
    //endregion
}

package com.gmail.klokovsergey.common.entity.database;

import com.gmail.klokovsergey.common.entity.StatusMode;
import lombok.*;

import java.io.*;
import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Data
public class User implements Externalizable {
    private static final long serialVersionUID = 1L;

    private long userTgId;
    private StatusMode currentMode;
    private Date lastVisit;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getUserTgId());
        out.writeObject(this.getCurrentMode());
        out.writeLong(this.getLastVisit().getTime());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        userTgId = in.readLong();
        currentMode = StatusMode.fromMode((String)in.readObject());
        lastVisit = new Date(in.readLong());
    }

    @Override
    public String toString() {
        return userTgId + ":" + currentMode + "," + lastVisit;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userTgId == user.userTgId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userTgId);
    }
}

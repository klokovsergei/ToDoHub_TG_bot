package com.gmail.klokovsergey.common.entity.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gmail.klokovsergey.common.entity.StatusMode;
import com.gmail.klokovsergey.common.entity.tasks.Task;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.*;

@NoArgsConstructor
public class CashUsersHub implements Externalizable {

    private Map<User, List<Task>> cashUsers = new HashMap<>();

    public StatusMode getUserStatusMode(long userId) {
        Optional<User> result = cashUsers.keySet().stream().filter(user -> user.getUserTgId() == userId).findAny();
        if (result.isPresent()) {
            return result.get().getCurrentMode();
        }
        else
            createNewUser(userId);

        return StatusMode.START;
    }
    public boolean setUserStatusMode(long userId, StatusMode statusMode) {
        Optional<User> result = cashUsers.keySet().stream().filter(user -> user.getUserTgId() == userId).findAny();
        if (result.isPresent()) {
            result.get().setCurrentMode(statusMode);
            return true;
        }
        else
            createNewUser(userId);

        return false;
    }

    private void createNewUser(long userId) {
        User user = new User();
        user.setUserTgId(userId);
        user.setCurrentMode(StatusMode.START);
        cashUsers.put(user, new ArrayList<>());
    }



    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }

//User, ArrayList<? extends Task>
}

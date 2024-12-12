package com.gmail.klokovsergey.common.entity.database;

import com.gmail.klokovsergey.common.entity.tasks.Task;
import com.gmail.klokovsergey.common.service.DataArchiveService;

import java.util.*;

public class DataArchive {

    private static final String fileSingedUpUsers = "src/main/resources/notForInternet/database/singed_up_users/fileSingedUpUsers.json";
    private static final String dirUsersTasks = "src/main/resources/notForInternet/database/users_tasks";

    private static final DataArchive INSTANCE = new DataArchive();
    private static final Map<Long, List<Task>> mapUsersTasks = new HashMap<>();
    private static final Set<User> users = new HashSet<>();


    private DataArchive() { loadAllUsers(); }
    //region Методы
    public static DataArchive getInstance() {
        return INSTANCE;
    }


    public void addUser(User user) {
        if (users.contains(user)) {
            //действия, если зарегистрированный пользователь
        } else {
            users.add(user);
            mapUsersTasks.put(user.getUserTgId(), new ArrayList<>());
        }
    }

    private void loadAllUsers() {
        Set<User> loadUsers = null;
        loadUsers = DataArchiveService.loadUsersFromFile(fileSingedUpUsers);
        if (loadUsers != null)
            users.addAll(loadUsers);
    }
    //endregion


}

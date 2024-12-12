package com.gmail.klokovsergey.common.entity.database;

import com.gmail.klokovsergey.common.entity.tasks.Task;
import com.gmail.klokovsergey.common.service.DataArchiveService;

import java.io.File;
import java.util.*;

public class DataArchive {

    private static final String FILE_SINGED_UP_USERS_JSON = "src/main/resources/notForInternet/database/singed_up_users/fileSingedUpUsers.json";
    private static final String DIR_USERS_TASKS = "src/main/resources/notForInternet/database/users_tasks";

    private static final DataArchive INSTANCE = new DataArchive();
    private static final Map<Long, List<Task>> mapUsersTasks = new HashMap<>();
    private static final Set<User> users = new HashSet<>();


    private DataArchive() { loadAllUsers(); }


    //region Методы

    public static DataArchive getInstance() {
        return INSTANCE;
    }

    public void editUser(User user) {
        if (users.contains(user)) {
            //действия, если зарегистрированный пользователь
        } else {
            users.add(user);
            mapUsersTasks.put(user.getUserTgId(), new ArrayList<>());
        }
    }

    private void loadAllUsers() {
        Set<User> loadUsers = null;
        loadUsers = DataArchiveService.loadUsersFromFile(FILE_SINGED_UP_USERS_JSON);
        if (loadUsers != null)
            users.addAll(loadUsers);
    }

    public void stopArchive() {
        DataArchiveService.saveUsersToFile(FILE_SINGED_UP_USERS_JSON, users);

        for (long userTgId : mapUsersTasks.keySet()) {
            File file = new File(DIR_USERS_TASKS, (userTgId + ".json"));
            List<Task> tasks = mapUsersTasks.get(userTgId);
            DataArchiveService.saveTasksToFile(file, tasks);
        }
    }
    //endregion


}

import com.gmail.klokovsergey.common.entity.StatusMode;
import com.gmail.klokovsergey.common.entity.database.User;
import com.gmail.klokovsergey.common.entity.tasks.Task;
import com.gmail.klokovsergey.common.service.DataArchiveService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class DataArchiveServiceTest {

    public static void main(String[] args) {

        System.out.println("================");
        System.out.println("Создаем Map<Long, List<Task>> и Set<User> и наполняем их для проверки DataArchiveService.");
        User user1 = new User();
        user1.setUserTgId(1L);
        user1.setCurrentMode(StatusMode.START);
        user1.setLastVisit(new Date());
        User user2 = new User();
        user2.setUserTgId(2L);
        user2.setCurrentMode(StatusMode.ADMIN);
        user2.setLastVisit(new Date());
        User user3 = new User();
        user3.setUserTgId(3L);
        user3.setCurrentMode(StatusMode.START);
        user3.setLastVisit(new Date());

        Task task1 = new Task();
        task1.setTitle("Задача 1");
        task1.setDateCreate(LocalDate.now());
        task1.setStatus(false);
        Task task2 = new Task();
        task2.setTitle("Задача 2");
        task2.setDateCreate(LocalDate.now().minusDays(3));
        task2.setStatus(false);
        Task task3 = new Task();
        task3.setTitle("Задача 3");
        task3.setDateCreate(LocalDate.now().minusYears(1));
        task3.setStatus(false);
        Task task4 = new Task();
        task4.setTitle("Задача 4");
        task4.setDateCreate(LocalDate.now().minusWeeks(4));
        task4.setStatus(false);

        Set<User> usersSet = new HashSet<>();
        usersSet.add(user1);
        usersSet.add(user2);
        usersSet.add(user3);
        System.out.println("Set<User>: " + usersSet);

        Map<Long, List<Task>> mapUsersTasks1 = new HashMap<>();
        mapUsersTasks1.put(user1.getUserTgId(), List.of(task1, task4));
        mapUsersTasks1.put(user3.getUserTgId(), List.of(task1, task2, task3, task4));
        System.out.println("Map<Long, List<Task>>: " + mapUsersTasks1);

        System.out.println("================");

        System.out.println("Сериализация Set<User>");

        String fileSetUsersJson = "src/test/java/log/users_data_archive.json";
        DataArchiveService.saveUsersToFile(fileSetUsersJson, usersSet);
        System.out.println("================");
        File file = new File(fileSetUsersJson);
        System.out.println("================");
        System.out.println("Файл с данными создан: check = " + file.exists());
        System.out.println("================");
        System.out.println("================");
        System.out.println("Содержимое файла:");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready())
                System.out.println(reader.readLine());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("================");
        System.out.println("Десериализация Set<User>");
        Set<User> newSetUsers = DataArchiveService.loadUsersFromFile(fileSetUsersJson);
        System.out.println("Set<User> newSet: " + newSetUsers);
        System.out.println("================");
        System.out.println("================");
        System.out.println("Set одинаковые: check = " + usersSet.containsAll(newSetUsers));
        System.out.println("================");
        System.out.println("================");



//        Task newTask = objectMapper.readValue(file1, objectMapper.getTypeFactory().constructType(Task.class));
//        System.out.println("Новый пользователь из файла: " + newTask);
//        System.out.println("================");
//        System.out.println("Сверяем обоих Task: check = " + (task1.equals(newTask)));
//        System.out.println("================");

    }
}

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gmail.klokovsergey.common.entity.tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskTest {
    public static void main(String[] args) throws IOException {
        System.out.println("================");
        System.out.println("Создаем Task без параметров и проверяем геттеры/сеттеры:");
        Task task1 = new Task();
        System.out.println("Task создан = " + (task1 != null));
        System.out.println("================");

        System.out.println("dateCreate (LocalDate)");
        LocalDate dateCreate = LocalDate.now();
        task1.setDateCreate(dateCreate);
        System.out.println("dateCreate = " + task1.getDateCreate() + ", check = " + (dateCreate == task1.getDateCreate()));
        System.out.println("================");

        System.out.println("dateFinish (LocalDate)");
        LocalDate dateFinish = LocalDate.now().plusDays(2L);
        task1.setDateFinish(dateFinish);
        System.out.println("dateFinish = " + task1.getDateFinish() + ", check = " + (dateFinish == task1.getDateFinish()));
        System.out.println("================");

        System.out.println("dateNotification (LocalDate)");
        LocalDate dateNotification = LocalDate.now().plusDays(1L);
        task1.setDateNotification(dateNotification);
        System.out.println("dateNotification = " + task1.getDateNotification() + ", check = " + (dateNotification == task1.getDateNotification()));
        System.out.println("================");

        System.out.println("timeNotification (LocalTime)");
        LocalTime timeNotification = LocalTime.now();
        task1.setTimeNotification(timeNotification);
        System.out.println("timeNotification = " + task1.getTimeNotification() + ", check = " + (timeNotification == task1.getTimeNotification()));
        System.out.println("================");

        System.out.println("title (String)");
        String title = "Do test";
        task1.setTitle(title);
        System.out.println("title = \"" + task1.getTitle() + "\", check = " + (title.equals(task1.getTitle())));
        System.out.println("================");

        System.out.println("status (boolean)");
        boolean status = false;
        task1.setStatus(status);
        System.out.println("status = \"" + task1.isStatus() + "\", check = " + (status == task1.isStatus()));
        System.out.println("================");

        System.out.println("Сериализация");

        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        String fileTasks_Json = "src/test/java/log/tasks_cash.json";
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            objectMapper.writeValue(new File(fileTasks_Json), task1);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("================");
        File file = new File(fileTasks_Json);
        System.out.println("Файл с данными создан: check = " + file.exists());
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
        System.out.println("Десериализация");
        File file1 = new File(fileTasks_Json);
        Task newTask = objectMapper.readValue(file1, objectMapper.getTypeFactory().constructType(Task.class));
        System.out.println("Новый пользователь из файла: " + newTask);
        System.out.println("================");
        System.out.println("Сверяем обоих Task: check = " + (task1.equals(newTask)));
        System.out.println("================");
    }
}

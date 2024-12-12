import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gmail.klokovsergey.common.entity.StatusMode;
import com.gmail.klokovsergey.common.entity.database.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserTest {
    public static void main(String[] args) throws IOException {
        System.out.println("================");
        System.out.println("Создаем User без параметров и проверяем геттеры/сеттеры:");
        User user1 = new User();
        System.out.println("User создан = " + (user1 != null));
        System.out.println("================");
        System.out.println("user ID (long)");
        long userId = 1L;
        user1.setUserTgId(userId);
        System.out.println("ID = " + user1.getUserTgId() + ", check = " + (userId == user1.getUserTgId()));
        System.out.println("================");
        System.out.println("user Status Mode");
        StatusMode userMode = StatusMode.START;
        user1.setCurrentMode(userMode);
        System.out.println("Status mode = " + user1.getCurrentMode() + ", check = " + (userMode == user1.getCurrentMode()));
        System.out.println("================");
        System.out.println("user LastVisit");
        Date userDate = new Date();
        user1.setLastVisit(userDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(userDate));
        System.out.println("Date = " + user1.getLastVisit() + ", check = " + (userDate == user1.getLastVisit()));
        System.out.println("================");

        System.out.println("Сериализация");

        ObjectMapper objectMapper = new ObjectMapper();
        String fileUser_Json = "src/test/java/log/users_cash.json";
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            objectMapper.writeValue(new File(fileUser_Json), user1);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.println("================");
        File file = new File(fileUser_Json);
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
        File file1 = new File(fileUser_Json);
        User newUser = objectMapper.readValue(file1, objectMapper.getTypeFactory().constructType(User.class));
        System.out.println("Новый пользователь из файла: " + newUser);
        System.out.println("================");
        System.out.println("Сверяем обоих User: check = " + (user1.equals(newUser)));

        System.out.println("================");
        System.out.println("Проверяем работу EQUELS. User равны, если равны ID");
        user1.setLastVisit(new Date());
        user1.setCurrentMode(StatusMode.ADMIN);
        System.out.println("Изменил LastVisit и CurrentMode: status = " + (user1.equals(newUser)));
        user1.setLastVisit(userDate);
        user1.setCurrentMode(userMode);
        user1.setUserTgId(5L);
        System.out.println("LastVisit и CurrentMode одинаковые, ID разные. Объекты НЕ РАВНЫ: status = " + (!user1.equals(newUser)));
    }
}

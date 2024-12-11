import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gmail.klokovsergey.common.entity.StatusMode;
import com.gmail.klokovsergey.common.entity.database.User;

import java.io.*;
import java.util.*;

public class CashUsersHubTest implements Externalizable {

    private static final long serialVersionUID = 1L;
    private Map<User, List<String>> cash;

    public CashUsersHubTest() { }

    public static void main(String[] args) {
        System.out.println("================");
        System.out.println("Создаем User без параметров и проверяем геттеры/сеттеры:");


        System.out.println("Наполняем Map.");
        User user1 = new User();
        User user2 = new User();
        user1.setUserTgId(1L);
        user2.setUserTgId(2L);
        user1.setCurrentMode(StatusMode.START);
        user2.setCurrentMode(StatusMode.ADMIN);
        user1.setLastVisit(new Date());
        user2.setLastVisit(new Date());

        CashUsersHubTest cashUsersHubTest = new CashUsersHubTest();
        cashUsersHubTest.cash = new HashMap<>();
        cashUsersHubTest.cash.put(user1, List.of("Ghbdt", "fsaa", "fgdg"));
        cashUsersHubTest.cash.put(user2, List.of("Ghedqebdt", "fsaasfa"));

        String fileUser_Json = "src/test/java/log/usersHub_cash.json";

        for (var item : cashUsersHubTest.cash.entrySet())
            System.out.println(item);
//
//        System.out.println("Сериализация");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        String fileUser_Json = "src/test/java/log/users_cash.json";
//        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
//        try {
//            objectMapper.writeValue(new File(fileUser_Json), user1);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        System.out.println("================");
//        File file = new File(fileUser_Json);
//        System.out.println("Файл с данными создан: check = " + file.exists());
//        System.out.println("Содержимое файла:");
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            while (reader.ready())
//                System.out.println(reader.readLine());
//            reader.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("================");
//        System.out.println("Десериализация");
//        File file1 = new File(fileUser_Json);
//        User newUser = objectMapper.readValue(file1, objectMapper.getTypeFactory().constructType(User.class));
//        System.out.println("Новый пользователь из файла: " + newUser);
//        System.out.println("================");
//        System.out.println("Сверяем обоих User: check = " + (user1.equals(newUser)));


    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }
}

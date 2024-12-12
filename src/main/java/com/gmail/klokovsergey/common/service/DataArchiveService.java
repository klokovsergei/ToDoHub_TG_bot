package com.gmail.klokovsergey.common.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gmail.klokovsergey.common.entity.database.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataArchiveService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveUsersToFile(String fileName, Set<User> users) {
        try {
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            objectMapper.writeValue(new File(fileName), users);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Не удалось записать файл " + fileName);
        }
    }
    public static Set<User> loadUsersFromFile(String fileName) {
        Set<User> users = new HashSet<>();

        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            try {
                users = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(Set.class, User.class));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Не удалось прочитать файл " + fileName);
            }
        }
        return users;
    }
}

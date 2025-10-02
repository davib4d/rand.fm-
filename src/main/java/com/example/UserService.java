package com.example; // <-- CORRIGIDO

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private static final String USERS_FILE = "users.json";
    private final Gson gson = new Gson();
    private List<User> users;

    public UserService() {
        this.users = loadUsersFromFile();
    }

    private List<User> loadUsersFromFile() {
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> loadedUsers = gson.fromJson(reader, userListType);
            return loadedUsers != null ? loadedUsers : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void saveUsersToFile() {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados dos usuários: " + e.getMessage());
        }
    }

    public boolean createUser(String username, String password) {
        if (users.stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username))) {
            return false;
        }
        users.add(new User(username, password));
        saveUsersToFile();
        return true;
    }

    public Optional<User> login(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password))
                .findFirst();
    }
    
    public void updateUser(User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(updatedUser.getUsername())) {
                users.set(i, updatedUser);
                saveUsersToFile();
                return;
            }
        }
    }
}
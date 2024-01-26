package org.example.dbdatacreation.csventityreaders;

import org.example.entities.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserCSVReader implements GenericCSVReader<User>{

    public List<User> readFromCSV(String filePath) {
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\t");
                User user = createUserFromCSV(data[0].split(","));
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    private User createUserFromCSV(String[] data) {
        User.UserBuilder userBuilder = User.builder();

        userBuilder.id(Long.parseLong(data[0].trim()))
                .salary(Double.parseDouble(data[1].trim()))
                .uuid(UUID.fromString(data[2].trim()))
                .legalEntity(Boolean.parseBoolean(data[3].trim()))
                .company(data[4].trim())
                .name(data[5].trim());

        return userBuilder.build();
    }
}
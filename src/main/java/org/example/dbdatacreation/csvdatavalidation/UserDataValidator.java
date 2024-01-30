package org.example.dbdatacreation.csvdatavalidation;

import org.example.entities.User;

import java.util.UUID;

public class UserDataValidator implements GenericDataValidator<User> {

    private static final String REGEX_FOR_UUID = "[0-9a-zA-Z\\-]+";

    public boolean isValid(User user) {
        return isValidId(user.getId()) &&
                isValidSalary(user.getSalary()) &&
                isValidUUID(user.getUuid()) &&
                isValidLegalEntity(user.getLegalEntity()) &&
                isValidCompany(user.getCompany()) &&
                isValidName(user.getName());
    }

    private boolean isValidId(Long id) {
        return id != null && id > 0;
    }

    private boolean isValidSalary(Double salary) {
        return salary != null && salary >= 0;
    }

    private boolean isValidUUID(UUID uuid) {
        if (uuid != null) {
            String uuidString = uuid.toString();
            return uuidString.matches(REGEX_FOR_UUID);
        }
        return false;
    }

    private boolean isValidLegalEntity(Boolean legalEntity) {
        return legalEntity != null;
    }

    private boolean isValidCompany(String company) {
        return company != null && !company.trim().isEmpty();
    }

    private boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}

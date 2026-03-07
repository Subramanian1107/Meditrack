package main.java.com.airtribe.meditrack.util;

import main.java.com.airtribe.meditrack.exception.InvalidDataException;

public final class Validator {

    private Validator() {
    }

    public static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be null or empty");
        }
    }

    public static void validateAge(int age) {
        if (age <= 0 || age > 120) {
            throw new InvalidDataException("Age must be between 1 and 120");
        }
    }

    public static void validateFee(double fee) {
        if (fee < 0) {
            throw new InvalidDataException("Fee cannot be negative");
        }
    }

    public static void validateExperience(int experience) {
        if (experience < 0) {
            throw new InvalidDataException("Experience cannot be negative");
        }
    }

    public static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new InvalidDataException(message);
        }
        return value;
    }
}


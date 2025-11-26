package com.substring.auth.helpers;

import java.util.UUID;

public class UserHelper {

    public static UUID parseUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + id);
        }
    }
}

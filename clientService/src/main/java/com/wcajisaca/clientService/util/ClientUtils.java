package com.wcajisaca.clientService.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class
 * Created by wcajisaca
 */
public class ClientUtils {
    /**
     * Encriptacion de la contraseña
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }
}

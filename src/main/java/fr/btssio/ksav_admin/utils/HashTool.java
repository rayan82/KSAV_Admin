package fr.btssio.ksav_admin.utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashTool {
    
    /**
     * Permet d'hacher un mot de passe
     * 
     * @param password
     * @return mot de passe hacher
     */
    public static String hash(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
    }
    
    /**
     * Vérifier si un mot de passe non hacher est équivalent à un mot de passe hacher
     * 
     * @param password
     * @param hash
     * @return Boolean
     */
    public static boolean check(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
    
}

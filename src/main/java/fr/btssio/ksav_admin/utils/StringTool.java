package fr.btssio.ksav_admin.utils;

public class StringTool {
    
    /**
     * Permet de mettre un majuscule à la première lettre du mot
     * 
     * @param str
     * @return Chaine de caractère
     */
    public static String capitalize(String str) {
        if (str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
    
}

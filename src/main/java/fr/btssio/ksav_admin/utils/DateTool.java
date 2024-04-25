package fr.btssio.ksav_admin.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateTool {
    
    /**
     * Permet de formater un timestamp
     * 
     * @param timestamp
     * @param format
     * @return Timestamp formater
     */
    public static String formatDate(Timestamp timestamp, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(timestamp);
    }
    
    /**
     * Permet de formater une date
     * 
     * @param date
     * @param format
     * @return Date formater
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    /**
     * Permet de convertir une date JJ/MM/AAAA en objet date
     * 
     * @param strDate
     * @return Objet date
     */
    public static Date convertStringToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = new Date(formatter.parse(strDate).getTime());
        } catch (ParseException e) {
            Logger.getLogger(DateTool.class.getName()).log(Level.WARNING, e.getMessage());
        }
        return date;
    }
    
    /**
     * Vérifier si une date respecte un format
     * 
     * @param date
     * @param format
     * @return Boolean format valide
     */
    public static boolean isValidDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
        
}

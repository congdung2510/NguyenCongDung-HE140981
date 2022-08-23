/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

/**
 *
 * @author admin
 */
public class Validator {

    public static Integer validateEid(String eid) {
        if (eid == null || eid.isEmpty()) {
            throw new IllegalArgumentException("Data invalid");
        }
        Integer id;
        try {
            id = Integer.valueOf(eid);

        } catch (Exception e) {
            throw new IllegalArgumentException("Eid must be integer");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Eid must bigger than 0");
        }
        return id;
    }

    public static String validateString(String str) {
        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            throw new IllegalArgumentException("Data invalid");
        }

        return str.trim();
    }

}

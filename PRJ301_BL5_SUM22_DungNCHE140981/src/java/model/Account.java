/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Ngo Tung Son
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Account {
    private Integer id;
    private String username;
    private String password;
    private ArrayList<Group> groups;
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author admin
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OnLeave {
   private Integer id;
   private Integer eid;
   private Date offFrom;
   private Date offTo;
   private String reason;
        
}

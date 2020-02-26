package se.iths.anton.myservice;


import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id @Generated int id;
    String userName;
    String realName;
    String city;
    int income;
    boolean inRelation;
}

package se.iths.anton.myservice;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id @GeneratedValue Integer id;
    String userName;
    String realName;
    String city;
    Integer income;
    boolean inRelation;

    public User(Integer id, String userName,String realName, String city, Integer income, boolean inRelation) {
        this.id=id;
        this.userName=userName;
        this.realName=realName;
        this.city=city;
        this.income=income;
        this.inRelation=inRelation;
    }
}

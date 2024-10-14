package org.tech.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="ClientRef")
public class ClientRef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    private String name;

    private String date;

    public ClientRef() {
    }

    public long getRid() {
        return rid;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    private String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
}

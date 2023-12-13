package com.api.fleetmanagement.models;


import jakarta.persistence.*;

@Entity
@Table(name = "taxis")
public class TaxisModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String plate;

//    public TaxisModel(Integer id, String plate) {
//        this.id = id;
//        this.plate = plate;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;

    }

}


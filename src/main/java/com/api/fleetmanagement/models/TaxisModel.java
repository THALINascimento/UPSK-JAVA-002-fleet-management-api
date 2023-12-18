package com.api.fleetmanagement.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "taxis")
public class TaxisModel {
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

//    public List<TrajectoriesModel> getTrajectories() {
//        return trajectories;
//    }
//
//    public void setTrajectories(List<TrajectoriesModel> trajectories) {
//        this.trajectories = trajectories;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String plate;

//    @OneToMany(mappedBy = "taxi", cascade = CascadeType.ALL)
//    private List<TrajectoriesModel> trajectories;

}



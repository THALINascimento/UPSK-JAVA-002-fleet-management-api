package com.api.fleetmanagement.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "taxis")
public class TaxisModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String plate;

    @OneToMany(mappedBy = "taxi", fetch = FetchType.LAZY)
    private List<TrajectoriesModel> trajectories;


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

    public List<TrajectoriesModel> getTrajectories() {
        return trajectories;
    }

    public void setTrajectories(List<TrajectoriesModel> trajectories) {
        this.trajectories = trajectories;
    }
}

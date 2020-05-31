package com.example.tomik.bikenote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Model danych motocykla
 */

public class AutoData implements  Serializable {

    /**
     * Model motocyka np. Diavel
     */
    private String model;

    /**
     * Marka motocykla np. Ducatti
     */
    private  String make;

    /**
     * Kolor motocykla
     */

    private String color;

    /**
     * Kolekcja tankowań
     */
    private List<TankUpRecord> tankUpRecord;

    public AutoData(String model, String make, String color) {
        this.model = model;
        this.make = make;
        this.color = color;
        tankUpRecord = new ArrayList<>();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<TankUpRecord> getTankUpRecord() {
        return tankUpRecord;
    }

    public void setTankUpRecord(List<TankUpRecord> tankUpRecord) {
        this.tankUpRecord = tankUpRecord;
    }


    @Override
    public String toString() {
        return make + " " + model + " " + color;
    }
}

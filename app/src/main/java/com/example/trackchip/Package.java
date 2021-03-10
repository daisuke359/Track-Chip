package com.example.trackchip;

import android.view.View;

import java.util.Date;

public class Package {
    int ID;
    String est_date;
    String origin;
    String dc_location;
    String destination;
    String status;

    public Package() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEst_date() {
        return est_date;
    }

    public void setEst_date(String est_date) {
        this.est_date = est_date;
    }

    public String getDc_location() {
        return dc_location;
    }

    public void setDc_location(String dc_location) {
        this.dc_location = dc_location;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

package com.localnews.localnews.models.busModels;

import jakarta.persistence.*;

@Entity
public class BusModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String route;

    @Column(name = "day_of_week")
    private String dayOfWeek;
    private String departureTime;
    private String local;

    public BusModel() {
    }

    public BusModel(Long id, String route, String dayOfWeek, String departureTime, String local) {
        this.id = id;
        this.route = route;
        this.dayOfWeek = dayOfWeek;
        this.departureTime = departureTime;
        this.local = local;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}

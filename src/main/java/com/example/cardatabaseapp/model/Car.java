package com.example.cardatabaseapp.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "creator_id")
    private Integer creatorId;

    private String brand;

    private String model;

    private String engine;

    @Column(name = "power_in_kw")
    private Integer powerInKw;

    @Column(name = "engine_capacity_in_l")
    private Float engineCapacityInL;

    @Column(name = "body_type")
    private String bodyType;

    private Integer year;

    @Column(name = "gear_type")
    private String gearType;


    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "video_url")
    private String videoUrl;

    private String description;

    public Car() {
    }

    public Car(Integer id, Integer creatorId, String brand, String model, String engine, Integer powerInKw, Float engineCapacityInL,
               String bodyType, Integer year, String gearType, String pictureUrl, String videoUrl, String description) {
        this.id = id;
        this.creatorId = creatorId;
        this.brand = brand;
        this.model = model;
        this.engine = engine;
        this.powerInKw = powerInKw;
        this.engineCapacityInL = engineCapacityInL;
        this.bodyType = bodyType;
        this.year = year;
        this.gearType = gearType;
        this.pictureUrl = pictureUrl;
        this.videoUrl = videoUrl;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Integer getPowerInKw() {
        return powerInKw;
    }

    public void setPowerInKw(Integer powerInKw) {
        this.powerInKw = powerInKw;
    }

    public Float getEngineCapacityInL() {
        return engineCapacityInL;
    }

    public void setEngineCapacityInL(Float engineCapacityInL) {
        this.engineCapacityInL = engineCapacityInL;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}

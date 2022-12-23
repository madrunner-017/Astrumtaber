package com.example.proj_zaliczeniowy.models;

public class ProductModel {
    private int id;
    private String name;
    private String category;
    private String objClass;
    private float magnitude;
    private int temperature;
    private int mass;
    private String description;
    private int imagePath;
    private float price;
    private int quantity;

    public ProductModel(int id, String name, String category, String objClass, float magnitude, int temperature, int mass, String description, int imagePath, float price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.objClass = objClass;
        this.magnitude = magnitude;
        this.temperature = temperature;
        this.mass = mass;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
    }

    public ProductModel(String name, String category, String objClass, float magnitude, int temperature, int mass, String description, int imagePath, float price) {
        this.name = name;
        this.category = category;
        this.objClass = objClass;
        this.magnitude = magnitude;
        this.temperature = temperature;
        this.mass = mass;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
    }

    public ProductModel(String name, int imagePath, float price) {
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
    }

    public ProductModel() {
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", objClass='" + objClass + '\'' +
                ", magnitude=" + magnitude +
                ", temperature=" + temperature +
                ", mass=" + mass +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", price=" + price +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getObjClass() {
        return objClass;
    }

    public void setObjClass(String objClass) {
        this.objClass = objClass;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagePath() {
        return imagePath;
    }

    public void setImagePath(int imagePath) {
        this.imagePath = imagePath;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

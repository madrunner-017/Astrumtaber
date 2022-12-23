package com.example.proj_zaliczeniowy.models;

public class UserModel {
    private int id;
    private String name;
    private String email;
    private long phone;
    private String password;

    public UserModel(int id, String email, long phone, String password) {
        this.id = id;
        this.name = email.split("@")[0];
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public UserModel(int id, String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UserModel() {
    }

//    toString method
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                '}';
    }

//    getters and setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.proj_zaliczeniowy.models;

import java.util.Date;

public class OrderModel {
    private int ID;
    private String recipient;
    private String orderDate;
    private int totalPrice;
    private String content;

    public OrderModel(int ID, String recipient, String orderDate, int totalPrice, String content) {
        this.ID = ID;
        this.recipient = recipient;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.content = content;
    }

    public OrderModel(String recipient, String orderDate, int totalPrice, String content) {
        this.recipient = recipient;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.content = content;
    }

    public OrderModel(String recipient) {
        this.recipient = recipient;
    }

    public OrderModel() {
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "ID=" + ID +
                ", recipient='" + recipient + '\'' +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", content='" + content + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

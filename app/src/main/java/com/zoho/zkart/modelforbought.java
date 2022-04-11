package com.zoho.zkart;

public class modelforbought {
    String Date,Time,Total,brand,category,model,quantity,invoice;

    public modelforbought() {
    }

    public modelforbought(String date, String time, String total, String brand, String category, String model, String quantity, String invoice) {
        Date = date;
        Time = time;
        Total = total;
        this.brand = brand;
        this.category = category;
        this.model = model;
        this.quantity = quantity;
        this.invoice = invoice;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}

package com.zoho.zkart;

public class modelforcart {
    String brand,model,price,quantity,category;

    public modelforcart(String brand, String model, String price, String quantity, String category) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public modelforcart() {
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

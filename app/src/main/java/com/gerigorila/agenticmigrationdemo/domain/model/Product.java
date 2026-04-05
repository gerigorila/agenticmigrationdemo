package com.gerigorila.agenticmigrationdemo.domain.model;

public class Product {
    private final int id;
    private final String name;
    private final double price;
    private final String description;
    private final String imageUrl;

    public Product(int id, String name, double price, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}

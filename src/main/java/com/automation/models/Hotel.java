package com.automation.models;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class Hotel {
    private String id;
    private String name;
    private String address;
    private String city;
    private String country;
    private BigDecimal pricePerNight;
    private String currency;
    private double rating;
    private boolean isAvailable;
    private String roomType;
    private String bedType;
    private int maxOccupancy;


    // Singleton instance for global access across page objects
    private static Hotel instance = new Hotel();

    public static Hotel getInstance() {
        return instance;
    }

    // Default constructor
    private Hotel() {}

    // Constructor with essential fields
    private Hotel(String id, String name, String currency, BigDecimal pricePerNight, String city) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.pricePerNight = pricePerNight;
        this.city = city;
    }

    // Builder pattern
    public static class Builder {
        private String id;
        private String name;
        private String address;
        private String city;
        private String country;
        private BigDecimal pricePerNight;
        private String currency = "VND"; // Default currency
        private double rating;
        private boolean isAvailable;
        private String roomType;
        private String bedType;
        private int maxOccupancy;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setPricePerNight(BigDecimal pricePerNight) {
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public Builder setAvailable(boolean available) {
            isAvailable = available;
            return this;
        }

        public Builder setRoomType(String roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder setBedType(String bedType) {
            this.bedType = bedType;
            return this;
        }

        public Builder setMaxOccupancy(int maxOccupancy) {
            this.maxOccupancy = maxOccupancy;
            return this;
        }

        public Hotel build() {
            Hotel hotel = new Hotel();
            hotel.id = this.id;
            hotel.name = this.name;
            hotel.address = this.address;
            hotel.city = this.city;
            hotel.country = this.country;
            hotel.pricePerNight = this.pricePerNight;
            hotel.currency = this.currency;
            hotel.rating = this.rating;
            hotel.isAvailable = this.isAvailable;
            hotel.roomType = this.roomType;
            hotel.bedType = this.bedType;
            hotel.maxOccupancy = this.maxOccupancy;

            return hotel;
        }
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }
}
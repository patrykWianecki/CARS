package com.app.model;

import com.app.model.enums.Color;

import java.math.BigDecimal;
import java.util.Set;

public class Car {
    private String model;
    private BigDecimal price;
    private Color color;
    private Long mileage;
    private Set<String> components;

    public Car() {
    }

    private Car(CarBuilder builder) {
        this.model = builder.model;
        this.price = builder.price;
        this.color = builder.color;
        this.mileage = builder.mileage;
        this.components = builder.components;
    }

    public String getModel() {
        return model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Color getColor() {
        return color;
    }

    public Long getMileage() {
        return mileage;
    }

    public Set<String> getComponents() {
        return components;
    }

    public void setComponents(Set<String> components) {
        this.components = components;
    }

    public static CarBuilder builder() {
        return new Car.CarBuilder();
    }

    @Override
    public String toString() {
        return model + " " +
                price + " " +
                color + " " +
                mileage + " " +
                components;
    }

    public static class CarBuilder {
        private String model;
        private BigDecimal price;
        private Color color;
        private Long mileage;
        private Set<String> components;

        public Car build() {
            return new Car(this);
        }

        public CarBuilder model(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public CarBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public CarBuilder mileage(Long mileage) {
            this.mileage = mileage;
            return this;
        }

        public CarBuilder components(Set<String> components) {
            this.components = components;
            return this;
        }
    }


}

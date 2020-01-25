package com.app.validator.data;

import java.math.BigDecimal;
import java.util.Set;

import com.app.model.Car;
import com.app.model.enums.Color;

public class MockDataForTests {

  public static Car createCar() {
    return Car.builder()
        .color(Color.BLACK)
        .components(Set.of("ABS"))
        .mileage(5L)
        .model("MAZDA")
        .price(BigDecimal.TEN)
        .build();
  }
}

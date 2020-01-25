package com.app.validator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;

import com.app.model.Car;
import com.app.model.enums.Color;

import static java.math.BigDecimal.*;

public class CarValidator {

  public static boolean isCarValid(Car car) {
    Optional.ofNullable(car).orElseThrow(() -> new NullPointerException("Car is null"));

    return isModelValid(car.getModel()) &&
        isPriceValid(car.getPrice()) &&
        isColorValid(car.getColor()) &&
        isMileageValid(car.getMileage()) &&
        areComponentsValid(car.getComponents());
  }

  private static boolean isModelValid(String model) {
    Optional.ofNullable(model).orElseThrow(() -> new NullPointerException("Model is null"));
    return model.matches("[A-Z]+\\s{0,1}[A-Z]*");
  }

  private static boolean isPriceValid(BigDecimal price) {
    Optional.ofNullable(price).orElseThrow(() -> new NullPointerException("Price is null"));
    return ZERO.intValue() < price.compareTo(ZERO);
  }

  private static boolean isColorValid(Color color) {
    Optional.ofNullable(color).orElseThrow(() -> new NullPointerException("Color is null"));
    return Arrays.asList(Color.values()).contains(color);
  }

  private static boolean isMileageValid(Long mileage) {
    Optional.ofNullable(mileage).orElseThrow(() -> new NullPointerException("Mileage is null"));
    return ZERO.longValue() < mileage;
  }

  private static boolean areComponentsValid(Set<String> components) {
    if (CollectionUtils.isEmpty(components)) {
      throw new NullPointerException("Components is null or empty");
    }
    return components.stream().allMatch(CarValidator::isComponentValid);
  }

  private static boolean isComponentValid(String component) {
    return component.matches("[A-Z]+\\s{0,1}[A-Z]*");
  }
}

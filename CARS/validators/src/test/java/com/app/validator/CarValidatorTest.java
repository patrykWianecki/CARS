package com.app.validator;

import java.math.BigDecimal;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.model.Car;
import com.app.validator.data.MockDataForTests;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarValidatorTest {

  private Car car = MockDataForTests.createCar();

  @Test
  void should_successfully_valid_car() {
    // given + when
    boolean actual = CarValidator.isCarValid(car);

    // then
    assertTrue(actual);
  }

  @Test
  void should_throw_exception_when_car_is_null() {
    // when + then
    assertThrows(NullPointerException.class, () -> CarValidator.isCarValid(null));
  }

  @Test
  void should_throw_exception_when_color_is_null() {
    // given
    car.setColor(null);

    // when + then
    assertThrows(NullPointerException.class, () -> CarValidator.isCarValid(car));
  }

  @Test
  void should_throw_exception_when_model_is_null() {
    // given
    car.setModel(null);

    // when + then
    assertThrows(NullPointerException.class, () -> CarValidator.isCarValid(car));
  }

  @Test
  void should_throw_exception_when_price_is_null() {
    // given
    car.setPrice(null);

    // when + then
    assertThrows(NullPointerException.class, () -> CarValidator.isCarValid(car));
  }

  @Test
  void should_throw_exception_when_mileage_is_null() {
    // given
    car.setMileage(null);

    // when + then
    assertThrows(NullPointerException.class, () -> CarValidator.isCarValid(car));
  }

  @Test
  void should_throw_exception_when_components_is_null() {
    // given
    car.setComponents(null);

    // when + then
    assertThrows(NullPointerException.class, () -> CarValidator.isCarValid(car));
  }

  @Test
  void should_return_false_when_mileage_is_zero() {
    // given
    car.setMileage(0L);

    // when
    boolean actual = CarValidator.isCarValid(car);

    // then
    assertFalse(actual);
  }

  @Test
  void should_return_false_when_mileage_is_smaller_than_zero() {
    // given
    car.setMileage(-1L);

    // when
    boolean actual = CarValidator.isCarValid(car);

    // then
    assertFalse(actual);
  }

  @Test
  void should_return_false_when_price_is_zero() {
    // given
    car.setPrice(BigDecimal.ZERO);

    // when
    boolean actual = CarValidator.isCarValid(car);

    // then
    assertFalse(actual);
  }

  @Test
  void should_return_false_when_price_is_smaller_than_zero() {
    // given
    car.setPrice(BigDecimal.valueOf(-1));

    // when
    boolean actual = CarValidator.isCarValid(car);

    // then
    assertFalse(actual);
  }

  @Test
  void should_return_false_when_model_has_incorrect_syntax() {
    // given
    car.setModel("Mazda");

    // when
    boolean actual = CarValidator.isCarValid(car);

    // then
    assertFalse(actual);
  }

  @Test
  void should_return_false_when_components() {
    // given
    car.setComponents(Set.of("Abs"));

    // when
    boolean actual = CarValidator.isCarValid(car);

    // then
    assertFalse(actual);
  }
}
package com.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.model.Car;

import static com.app.model.enums.Color.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

  public final static String ABS = "ABS";
  public final static String AIR_CONDITIONING = "AIR CONDITIONING";
  public final static String ALLOY_WHEELS = "ALLOY WHEELS";
  public final static String BLUETOOTH = "BLUETOOTH";
  public final static String ESP = "ESP";
  public final static String SUNROOF = "SUNROOF";
  public final static String MAZDA = "MAZDA";
  public final static String BMW = "BMW";

  private CarService carService = new CarService("CarsTest.json");

  @Test
  public void should_find_cars_with_correct_price_range() {
    // given + when
    List<Car> cars = carService.getCarsFromGivenPriceRange(BigDecimal.valueOf(160), BigDecimal.valueOf(300));

    // then
    assertNotNull(cars);
    assertEquals(cars.size(), 2);
  }

  @Test
  void should_throw_exception_when_min_price_is_greater_than_max_price() {
    // when + then
    assertThrows(IllegalArgumentException.class, () -> carService.getCarsFromGivenPriceRange(BigDecimal.TEN, BigDecimal.ONE));
  }

  @Test
  public void should_find_all_cars_with_particular_component() {
    // given + when
    Map<String, Set<Car>> cars = carService.getCarsWithParticularComponent();

    // then
    assertNotNull(cars);
    assertEquals(cars.keySet().size(), 6);
    assertTrue(cars.containsKey(ABS));
    assertTrue(cars.containsKey(AIR_CONDITIONING));
    assertTrue(cars.containsKey(ALLOY_WHEELS));
    assertTrue(cars.containsKey(BLUETOOTH));
    assertTrue(cars.containsKey(ESP));
    assertTrue(cars.containsKey(SUNROOF));
    assertEquals(cars.get(ABS).size(), 1);
    assertEquals(cars.get(AIR_CONDITIONING).size(), 1);
    assertEquals(cars.get(ALLOY_WHEELS).size(), 1);
    assertEquals(cars.get(BLUETOOTH).size(), 2);
    assertEquals(cars.get(ESP).size(), 1);
    assertEquals(cars.get(SUNROOF).size(), 1);
  }

  @Test
  public void should_find_all_cars_with_sorted_components() {
    // given + when
    List<Car> cars = carService.getCarsWithSortedComponents();

    // then
    assertNotNull(cars);
    assertEquals(3, cars.size());

    List<String> firstCarComponents = new ArrayList<>(cars.get(0).getComponents());
    assertEquals(3, firstCarComponents.size());
    assertEquals(ABS, firstCarComponents.get(0));
    assertEquals(ALLOY_WHEELS, firstCarComponents.get(1));
    assertEquals(SUNROOF, firstCarComponents.get(2));

    List<String> secondCarComponents = new ArrayList<>(cars.get(1).getComponents());
    assertEquals(2, secondCarComponents.size());
    assertEquals(AIR_CONDITIONING, secondCarComponents.get(0));
    assertEquals(BLUETOOTH, secondCarComponents.get(1));

    List<String> thirdCarComponents = new ArrayList<>(cars.get(2).getComponents());
    assertEquals(2, thirdCarComponents.size());
    assertEquals(BLUETOOTH, thirdCarComponents.get(0));
    assertEquals(ESP, thirdCarComponents.get(1));
  }

  @Test
  public void should_find_the_most_expensive_car() {
    // given + when
    List<Car> cars = carService.findTheMostExpensiveCars();

    // then
    assertNotNull(cars);
    assertEquals(cars.size(), 2);
    assertEquals(BigDecimal.valueOf(160), cars.get(0).getPrice());
    assertEquals(BigDecimal.valueOf(160), cars.get(1).getPrice());
  }

  @Test
  public void should_find_the_most_expensive_car_model() {
    // given + when
    Map<String, Car> cars = carService.getTheMostExpensiveCarModels();

    // then
    assertNotNull(cars);
    assertEquals(2, cars.size());
    assertEquals(BigDecimal.valueOf(160), cars.get(MAZDA).getPrice());
    assertEquals(BigDecimal.valueOf(160), cars.get(BMW).getPrice());
  }

  @Test
  public void should_find_cars_counted_by_color() {
    // given + when
    Map<String, Long> cars = carService.countCarsByColor();

    // then
    assertNotNull(cars);
    assertEquals(cars.size(), 2);
    assertEquals(1, cars.get(GREEN.name()));
    assertEquals(2, cars.get(BLACK.name()));
  }

  @Test
  public void should_find_all_cars_with_greater_mileage_than_given() {
    // given + when
    List<Car> cars = carService.sortCarsWithGreaterMileage(2000L);

    // then
    assertNotNull(cars);
    assertEquals(cars.size(), 2);
  }
}
